package client;

import common.RPCResponseMessage;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCRecord {

    private String id;
    private Long time;
    private RPCResponseMessage rpcResponseMessage;
    private Object ready;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public RPCResponseMessage getRpcResponseMessage() {
        return rpcResponseMessage;
    }

    public void setRpcResponseMessage(RPCResponseMessage rpcResponseMessage) {
        this.rpcResponseMessage = rpcResponseMessage;
    }

    public Object getReady() {
        return ready;
    }

    public void setReady(Object ready) {
        this.ready = ready;
    }
}
