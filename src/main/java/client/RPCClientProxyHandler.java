package client;

import common.RPCRequestMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClientProxyHandler implements InvocationHandler{

    private RPCClient rpcClient;
    private String interfaceName;
    private String version;

    public RPCClientProxyHandler(RPCClient rpcClient,String interfaceName,String version){
        this.rpcClient = rpcClient;
        this.interfaceName = interfaceName;
        this.version = version;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequestMessage message = new RPCRequestMessage();
        message.setId(UUID.randomUUID().toString());
        message.setInterfaceName(this.interfaceName);
        message.setMethodName(method.getName());
        message.setVersion(this.version);
        List<Object> parameters = Arrays.asList(args);
        message.setParameters(parameters);

        rpcClient.sendMessage(message);




        return null;
    }

}
