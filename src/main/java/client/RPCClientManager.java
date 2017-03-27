package client;
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
    private Map<String,RPCClientProxy> name2Proxy;
    private RPCClient rpcClient;
    private RPCClientManager(){
        name2Proxy = new HashMap<String, RPCClientProxy>();
        rpcClient = new RPCClient();
        try {
            rpcClient.connect(host, port);
        }catch(Exception e){
            System.out.println("client can not connect to the server successfully");
        }
    }

    public void add(RPCInterfaceConfigBuilder builder){

    }

    public Object get(String name){
        return null;
    }




}
