package cn.bupt.airsys.service;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.exception.ServerException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by ALSO on 2015/5/16.
 */
public class ServerDaemon implements Runnable {
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

    public void run() {
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

    /* test
    public static void main(String args[]) {
        new ServerDaemon(new ServerListener() {
            @Override
            public void onReceive(String inetAddr, byte[] requestData) {
                int type = requestData[0];
                switch (type) {
                    case 4:
                        byte data[] = new byte[4];
                        for (int i = 0; i < 4; i++) {
                            data[i] = requestData[i + 1];
                        }
                        float temp = Utility.byte2float(data);
                        System.out.println("TEMP: " +  temp);
                        break;

                    case 5:
                            byte data2[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data2[i] = requestData[i + 1];
                            }
                            float temp2 = Utility.byte2float(data2);
                            int power = requestData[5];
                            System.out.println("Slave: " + inetAddr + " req temp: " + temp2 + " req power: " + power);
                        break;

                    default:
                        break;
                }
            }
            @Override
            public void onException(ServerException e) {
            }
        }).run();
    }
    */

}
