package cn.bupt.airsys.exception;

/**
 * Created by ALSO on 2015/5/19.
 */
public class MsgException extends Exception {

    public MsgException(int type) {
        super("No such type("+ type + ") Message");
    }
}
