package cn.bupt.airsys.service;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.utils.Utility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by ALSO on 2015/6/13.
 */
public class DataSender {
    private int port;
    private int remotePort = Configure.SLAVE_PORT;
    private ServerListener listener;
    private DatagramSocket serverSocket;

    public DataSender(int port) throws SocketException {
        this.port = port;
        init();
    }

    public DataSender() throws SocketException {
        this(Configure.DEFAULT_SEND_PORT);
    }

    private void init() throws SocketException {
        serverSocket = new DatagramSocket(this.port);
    }

    public void sendStatus(InetAddress addr, int port, int workMode, float pay) throws IOException {
        System.out.println("send status: " + addr + " port: " + port + "workmode: " + workMode + "pay: " + pay);
        byte[] sendData = new byte[6];
        sendData[0] = (byte) (6 & 0xff);
        sendData[1] = (byte) (workMode & 0xff);
        //byte[] _tmp = ByteBuffer.allocate(4).putFloat((Float) pay).array();
        byte[] _tmp = Utility.float2bytes(pay);
        for (int i = 2; i < 6; i++) {
            sendData[i] = _tmp[i - 2];
        }
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, remotePort);
        serverSocket.send(sendPack);
    }

    public void sendWorkInfo(InetAddress addr, int port, float tarTemp, int power) throws IOException {
        System.out.println("send work info: " + addr + " port: " + port + " temp: " + tarTemp + " power: " + power);
        byte[] sendData = new byte[6];
        sendData[0] = (byte) (7 & 0xff);
        //byte[] _tmp = ByteBuffer.allocate(4).putFloat((Float) tarTemp).array();
        byte[] _tmp = Utility.float2bytes(tarTemp);
        for (int i = 1; i <= 4; i++) {
            sendData[i] = _tmp[i - 1];
        }
        sendData[5] = (byte) (power & 0xff);
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, addr, remotePort);
        serverSocket.send(sendPack);
    }
}
