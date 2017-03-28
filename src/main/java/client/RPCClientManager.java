package client;
import common.RPCException;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClientManager {

    static class InnerRPCClientManager{
        private static RPCClientManager rpcClientManager = new RPCClientManager();
        public static RPCClientManager getInstance(){
            return rpcClientManager;
        }
    }

    public static RPCClientManager getInstance(){
        return InnerRPCClientManager.getInstance();
    }

    final private String host = "127.0.0.1";
    final private int port = 8080;
    private Map<String,Object> name2Proxy;
    private RPCClient rpcClient;


    private RPCClientManager(){
        name2Proxy = new HashMap<String, Object>();
        rpcClient = new RPCClient();
        rpcClient.connect(host, port);
    }

    public RPCClientManager add(RPCInterfaceConfig config) throws Exception{
        if("".equals(config.getName())){
            throw new RPCException("the name of interface can not be empty");
        }
        if("".equals(config.getVersion())){
            throw new RPCException("the version can not be null");
        }
        if(config.getInterface() == null){
            throw new RPCException("the interface can not be null");
        }
        Object object = Proxy.newProxyInstance(
                config.getInterface().getClassLoader(),
                new Class[]{config.getInterface()},
                new RPCClientProxyHandler(
                        this.rpcClient,
                        config.getInterface().getName()
                )
        );
        name2Proxy.put(config.getName(),object);
        return this;
    }

    public Object get(String name){
        return name2Proxy.get(name);
    }




}
