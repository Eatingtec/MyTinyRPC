package client;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCInterfaceConfig {
    private String name;
    private Class theInterface;
    public RPCInterfaceConfig setName(String name){
        this.name = name;
        return this;
    }
    public RPCInterfaceConfig setInterface(Class it){
        this.theInterface = it;
        return this;
    }
    public String getName(){
        return this.name;
    }
    public Class getInterface(){
        return this.theInterface;
    }
}
