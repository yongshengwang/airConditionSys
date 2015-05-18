package cn.bupt.airsys.exception;

import java.net.SocketException;

/**
 * Created by ALSO on 2015/5/16.
 */
public class ServerException extends SocketException {
    public ServerException() {};
    public ServerException(String message) {
        super(message);
    }
}
