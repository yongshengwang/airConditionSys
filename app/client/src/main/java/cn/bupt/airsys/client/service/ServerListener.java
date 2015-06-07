package cn.bupt.airsys.client.service;

/**
 * Created by ALSO on 2015/6/7.
 */
public interface ServerListener {

    public abstract void onReceive(String inetAddr, byte[]  data);
    public abstract void onException(Exception e);

}
