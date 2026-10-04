package Hilos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.UnknownHostException;

public class HiloServidor extends Thread{

    private DatagramSocket socket;
    private boolean fin = false;
    private int cantConexiones = 0;
    InetAddress ip1, ip2;
    int puerto1, puerto2;


    public HiloServidor()
    {
        try {
            socket = new DatagramSocket(9992);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run()
    {
        while(!fin)
        {
            byte[] datos = new byte[1024];
            DatagramPacket dp = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void procesarMensaje(DatagramPacket dp)
    {
        String msj = new String(dp.getData()).trim();
        System.out.println("Mensaje recibido: " + msj);

        if(cantConexiones < 2)
        {
            if(msj.equals("Conectar")) {
                cantConexiones++;
                if (cantConexiones == 1) {
                    ip1 = dp.getAddress();
                    puerto1 = dp.getPort();
                    enviarMensaje("Conexion recibida", ip1, puerto1);
                } else if (cantConexiones == 2) {
                    ip2 = dp.getAddress();
                    puerto2 = dp.getPort();
                    enviarMensaje("Conexion recibida", ip2, puerto2);
                    enviarMensaje("Empezarchat", ip1, puerto1);
                    enviarMensaje("Empezarchat", ip2, puerto2);
                } else {
                    enviarMensaje("Sala llena", dp.getAddress(), dp.getPort());
                }
            }
        } else
        {
            if(dp.getAddress().equals(ip1) && dp.getPort() == puerto1)
            {
                msj = "Cliente 1 dice: " + msj;
                enviarMensaje(msj, ip2, puerto2);
            } else if(dp.getAddress().equals(ip2) && dp.getPort() == puerto2)
            {
                msj = "Cliente 1 dice: " + msj;
                enviarMensaje(msj, ip1, puerto1);
            } else
            {
                enviarMensaje("Acceso denegado", dp.getAddress(), dp.getPort());
            }
        }
    }

    public void enviarMensaje(String msj, InetAddress ipDestino, int puerto)
    {
        byte[] data = msj.getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(data, data.length, ipDestino, puerto);
            socket.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
