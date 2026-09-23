package Hilos;

import java.io.IOException;
import java.net.*;

public class HiloCliente extends Thread{

    private DatagramSocket socket;
    private boolean fin = false;
    private InetAddress ipServer;

    public HiloCliente()
    {
        try {
            socket = new DatagramSocket();
            ipServer = InetAddress.getByName("192.168.1.50");
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
        System.out.println(msj);
    }

    public void enviarMensaje(String msj)
    {
        byte[] data = msj.getBytes();
        try {
            int puerto = 9999;
            DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
            socket.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
