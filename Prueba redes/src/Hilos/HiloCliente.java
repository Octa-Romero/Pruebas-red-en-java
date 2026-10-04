package Hilos;

import Red.Cliente;

import java.io.IOException;
import java.net.*;

public class HiloCliente extends Thread{

    private DatagramSocket socket;
    private boolean fin = false;
    private InetAddress ipServer;
    private int puerto = 9992;
    private Cliente cliente;

    public HiloCliente(Cliente cliente)
    {
        try {
            this.cliente = cliente;
            socket = new DatagramSocket();
            ipServer = InetAddress.getByName("192.168.0.15");
            enviarMensaje("Conectar");
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run()
    {
        while(!fin)
        {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data, data.length);
            try {
                socket.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void procesarMensaje(DatagramPacket dp) {
        String msj = new String(dp.getData()).trim();
        //System.out.println(msj);
        if(msj.equals("Empezarchat"))
        {
            cliente.empezarChat(true);
        }
    }

    public void enviarMensaje(String msj)
    {
        System.out.println("Enviado: " + msj);
        byte[] data = msj.getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
            socket.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
