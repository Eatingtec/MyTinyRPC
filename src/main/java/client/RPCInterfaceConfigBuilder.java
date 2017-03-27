package client;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCInterfaceConfigBuilder {
    private String name;
    private Class theInterface;
    public RPCInterfaceConfigBuilder setName(String name){
        this.name = name;
        return this;
    }
    public RPCInterfaceConfigBuilder setInterface(Class it){
        this.theInterface = it;
        return this;
    }
}
