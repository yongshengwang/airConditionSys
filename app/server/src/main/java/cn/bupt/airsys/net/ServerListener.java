package cn.bupt.airsys.net;

import cn.bupt.airsys.exception.ServerException;

/**
 * Created by ALSO on 2015/5/16.
 */
public interface ServerListener {
    public abstract void onReceive(String inetAddr, byte[]  requestData);

    public abstract void onException(ServerException e);
}
