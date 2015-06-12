package cn.bupt.airsys.client.service;

import cn.bupt.airsys.client.Configure;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * Created by ALSO on 2015/6/7.
 */
public class DataSender {
    private int port;
    private ServerListener listener;
    private DatagramSocket serverSocket;

    public DataSender(int port) throws SocketException {
        this.port = port;
        init();
    }

    public DataSender() throws SocketException {
        this(Configure.DEFAULT_PORT);
    }

    private void init() throws SocketException {
        serverSocket = new DatagramSocket(this.port);
    }

    public boolean connetc(InetAddress addr, int port, int id) throws IOException {
        System.out.println("connetc remote: " + addr + " port: " + port + " slave id: " + id);
        byte[] sendData = new byte[2];
        sendData[0] = (byte)(2 & 0xff);
        sendData[1] = (byte)(id & 0xff);
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, port);
        serverSocket.send(sendPack);
        // TODO check response
        return true;
    }

    public void disconnetc(InetAddress addr, int port, int id) throws IOException {
        byte[] sendData = new byte[1];
        sendData[0] = (byte)(3 & 0xff);
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, port);
        serverSocket.send(sendPack);
    }

    public void sendStatus(InetAddress addr, int port, float temp) throws IOException {
        System.out.println("connetc remote: " + addr + " port: " + port + " temp: " + temp);
        byte[] sendData = new byte[5];
        sendData[0] = (byte)(4 & 0xff);
        byte[] _tmp = ByteBuffer.allocate(4).putFloat((Float) temp).array();
        for(int i = 1; i < 5; i++) {
            sendData[i] = _tmp[i-1];
        }
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, port);
        serverSocket.send(sendPack);
    }

     public void request(InetAddress addr, int port, float temp, int power) throws IOException {
         System.out.println("connetc remote: " + addr + " port: " + port + " temp: " + temp + " power: " + power);
        byte[] sendData = new byte[6];
        sendData[0] = (byte)(5 & 0xff);
        byte[] _tmp = ByteBuffer.allocate(4).putFloat((Float) temp).array();
        for(int i = 1; i < 5; i++) {
            sendData[i] = _tmp[i-1];
        }
         sendData[5] = (byte)(power & 0xff);
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, port);
        serverSocket.send(sendPack);
    }


}
