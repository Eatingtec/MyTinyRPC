package client;
import common.RPCException;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private ConcurrentHashMap<String,RPCRecord> resultMap;


    private RPCClientManager(){
        name2Proxy = new HashMap<String, Object>();
        rpcClient = new RPCClient();
        rpcClient.connect(host, port);
        resultMap = new ConcurrentHashMap<String, RPCRecord>();
    }

    public RPCClientManager add(RPCInterfaceConfigBuilder builder) throws Exception{
        if("".equals(builder.getName())){
            throw new RPCException("the name of interface can not be empty");
        }
        if("".equals(builder.getVersion())){
            throw new RPCException("the version can not be null");
        }
        if(builder.getInterface() == null){
            throw new RPCException("the interface can not be null");
        }
        Object object = Proxy.newProxyInstance(
                builder.getInterface().getClassLoader(),
                new Class[]{builder.getInterface()},
                new RPCClientProxyHandler(
                        this.rpcClient,
                        builder.getInterface().getName(),
                        builder.getVersion(),
                        this.resultMap
                )
        );
        name2Proxy.put(builder.getName(),object);
        return this;
    }

    public Object get(String name){
        return name2Proxy.get(name);
    }




}
