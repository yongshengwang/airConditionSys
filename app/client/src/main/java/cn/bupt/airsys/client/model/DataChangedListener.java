package cn.bupt.airsys.client.model;

/**
 * Created by ALSO on 2015/6/12.
 */
public interface DataChangedListener {
    public void temperatureChanged(float temp);
    public void paymentChanged(float pay);
    public void powerChanged(int power);
    public void workModeChanged(int workMode);
    public void onException(Exception e);
}
