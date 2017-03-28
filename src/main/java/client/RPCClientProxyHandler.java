package client;

import common.RPCException;
import common.RPCRequestMessage;
import common.RPCResponseMessage;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClientProxyHandler implements InvocationHandler{

    private RPCClient rpcClient;
    private String interfaceName;

    public RPCClientProxyHandler(RPCClient rpcClient,String interfaceName){
        this.rpcClient = rpcClient;
        this.interfaceName = interfaceName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCResponseMessage responseMessage = rpcClient.remoteInvoke(interfaceName,method,args);
        if(responseMessage.isWrong()){
            if(responseMessage.isExceptional()){
                throw new RPCException(responseMessage.getExceptionInfo());
            }else{
                throw new RPCException("unknown exception when handle the result");
            }
        }
        Object result = responseMessage.getResult();
        return result;
    }

}
