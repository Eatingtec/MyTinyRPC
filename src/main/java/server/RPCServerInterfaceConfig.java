package server;

/**
 * Created by Greeting on 2017/3/28.
 */
public class RPCServerInterfaceConfig {
    private Class theInterface;
    private Object theImplement;
    public RPCServerInterfaceConfig setInterface(Class it){
        this.theInterface = it;
        return this;
    }
    public RPCServerInterfaceConfig setImplement(Object impl){
        this.theImplement = impl;
        return this;
    }
    public Class getInterface(){
        return this.theInterface;
    }
    public Object getImplement(){
        return this.theImplement;
    }
}
