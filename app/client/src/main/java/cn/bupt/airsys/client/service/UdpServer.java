package cn.bupt.airsys.client.service;

import cn.bupt.airsys.client.Configure;

import javax.naming.InitialContext;
import java.io.IOException;
import java.net.*;

/**
 * Created by ALSO on 2015/6/7.
 */
public class UdpServer implements Runnable {
    private int port;
    private ServerListener listener;
    private DatagramSocket serverSocket;

    public UdpServer(int port, ServerListener listener) {
        this.listener = listener;
        this.port = port;
    }

    public UdpServer(ServerListener listener) {
        this(Configure.DEFAULT_PORT, listener);
    }


    @Override
    public void run() {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            listener.onException(e);
        }

        byte[] receiveData = null;
        for(;;){
            receiveData = new byte[6];
            DatagramPacket receivePack = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePack);
                InetAddress addr = receivePack.getAddress();
                listener.onReceive(addr.getHostAddress(), receivePack.getData());
                int port = receivePack.getPort();
                byte[] sendData = new byte[1]; sendData[0] = 1;
                DatagramPacket sendPack = new DatagramPacket(sendData, 1, addr, port);
                serverSocket.send(sendPack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
