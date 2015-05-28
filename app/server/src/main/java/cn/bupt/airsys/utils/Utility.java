package cn.bupt.airsys.utils;

import cn.bupt.airsys.exception.MsgException;

import java.nio.ByteBuffer;

/**
 * Created by ALSO on 2015/5/21.
 */
public class Utility {
    public static String MD5(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public static byte[]  packetCompose(int type, Object param1, Object param2) throws MsgException {
        byte[] msg = new byte[8];
        switch(type) {
            case 5:
                msg[0] = (byte) (5 & 0xff);
                msg[1] = (byte) ((Integer) param1 & 0xff);
                byte[] tmp = ByteBuffer.allocate(4).putFloat((Float) param2).array();
                for(int i = 2; i < 6; i++) {
                    msg[i] = tmp[i-2];
                }
                break;
            case 6:
                msg[0] = (byte) (5 & 0xff);
                byte[] _tmp = ByteBuffer.allocate(4).putFloat((Float) param2).array();
                for(int i = 1; i < 5; i++) {
                    msg[i] = _tmp[i-1];
                }
                msg[5] = (byte) ((Integer) param2 & 0xff);
                break;
            default:
                throw new MsgException(type);
        }
        return msg;
    }

    public static String packetParse(byte[] packet) {
        // TODO
        return null;
    }
}