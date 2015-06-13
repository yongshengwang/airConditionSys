package cn.bupt.airsys.client.utils;

import javax.swing.*;
import java.nio.ByteBuffer;

/**
 * Created by ALSO on 2015/6/7.
 */
public class Utility {
    public static float byte2float(byte[] bytes) {
/*        int asInt = (bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF) << 8)
                | ((bytes[2] & 0xFF) << 16)
                | ((bytes[3] & 0xFF) << 24);
        return Float.intBitsToFloat(asInt);
        */
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static void main(String args[]) {
        float f = 8.0f;
        byte[] _tmp = ByteBuffer.allocate(4).putFloat(f).array();
        for(int i = 0; i < 4; i++ ) {
            System.out.print(" " + _tmp[i]);
        }
        System.out.println("new: " + ByteBuffer.wrap(_tmp).getFloat());
    }
}