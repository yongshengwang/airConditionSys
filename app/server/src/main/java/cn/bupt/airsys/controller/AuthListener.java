package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.User;

/**
 * Created by ALSO on 2015/6/2.
 */
public interface AuthListener {
    public void onComplete(User user);
}
