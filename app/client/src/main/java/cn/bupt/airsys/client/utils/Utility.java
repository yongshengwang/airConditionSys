package cn.bupt.airsys.client.utils;

import javax.swing.*;

/**
 * Created by ALSO on 2015/6/7.
 */
public class Utility {
    public static float byte2float(byte[] bytes) {
        int asInt = (bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF) << 8)
                | ((bytes[2] & 0xFF) << 16)
                | ((bytes[3] & 0xFF) << 24);
        return Float.intBitsToFloat(asInt);
    }
}
