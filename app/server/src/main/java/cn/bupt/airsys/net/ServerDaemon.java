package cn.bupt.airsys.net;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.exception.ServerException;

import java.io.IOException;
import java.net.*;

/**
 * Created by ALSO on 2015/5/16.
 */
public class ServerDaemon {
    private int localPort;
    private ServerListener mServerListener;
    private DatagramSocket serverSocket;

    public ServerDaemon(int port, ServerListener listener) {
        this.mServerListener = listener;
        this.localPort = port;
    }

    public ServerDaemon(ServerListener listener) {
        this(Configure.DEFAULT_PORT, listener);
    }

    private void udpInit() {
        try {
            serverSocket = new DatagramSocket(this.localPort);
        } catch (SocketException e) {
            this.mServerListener.onException(new ServerException(e.toString()));
            //e.printStackTrace();
        }
    }

    public void start() {
        udpInit();
        byte[]  receiveData = null;
        for(;;) {
            receiveData = new byte[8];
            DatagramPacket receivePack = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePack);
                InetAddress addr = receivePack.getAddress();
                mServerListener.onReceive(addr.getHostAddress(), receivePack.getData());
            } catch (IOException e) {
                this.mServerListener.onException(new ServerException(e.toString()));
                //e.printStackTrace();
            }

        }
    }

}
