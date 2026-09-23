package Hilos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HiloServidor extends Thread{

    private DatagramSocket socket;
    private boolean fin = false;


    public HiloServidor()
    {
        try {
            socket = new DatagramSocket(5000);
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
        System.out.println(msj);
    }

    public void enviarMensaje(String msj)
    {
        byte[] data = msj.getBytes();
        InetAddress ipDestino;
        try {
            ipDestino = InetAddress.getByName("192.168.1.50");
            int puerto = 9999;
            DatagramPacket dp = new DatagramPacket(data, data.length, ipDestino, puerto);
            socket.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
