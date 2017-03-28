package server;

import client.RPCClient;
import client.RPCClientProxyHandler;
import client.RPCInterfaceConfig;
import client.RPCServer;
import common.RPCException;
import common.RPCRequestMessage;
import common.RPCResponseMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCServerManager {

    static class InnerRPCServerManager{
        private static RPCServerManager rpcServerManager = new RPCServerManager();
        public static RPCServerManager getInstance(){
            return rpcServerManager;
        }
    }

    public static RPCServerManager getInstance(){
        return RPCServerManager.InnerRPCServerManager.getInstance();
    }

    final private int port = 8080;
    private Map<String,ServiceSlot> serviceSlotMap;
    private RPCServer rpcServer;


    private RPCServerManager(){
        serviceSlotMap = new HashMap<String, ServiceSlot>();
        rpcServer = new RPCServer(port,this);
        rpcServer.start();
    }

    public RPCResponseMessage invoke(RPCRequestMessage requestMessage){
        String interfaceName = requestMessage.getInterfaceName();
        String methodName = requestMessage.getMethodName();
        List<Object> parameters = requestMessage.getParameters();
        RPCResponseMessage responseMessage = new RPCResponseMessage();
        responseMessage.setRequestId(requestMessage.getId());
        if(!serviceSlotMap.containsKey(interfaceName)){
            responseMessage.setWrong(true);
            responseMessage.setExceptional(true);
            responseMessage.setExceptionInfo("interface "+interfaceName+" is not exist in service");
            return responseMessage;
        }

        ServiceSlot serviceSlot = serviceSlotMap.get(interfaceName);
        Map<String,Method> methods = serviceSlot.getMethods();
        String key = getMethodKey(methodName,parameters);

        if(!methods.containsKey(key)){
            responseMessage.setWrong(true);
            responseMessage.setExceptional(true);
            responseMessage.setExceptionInfo("interface "+interfaceName+" do not have method " + methodName + " in service");
            return responseMessage;
        }
        Method method = methods.get(methodName);
        try {
            Object result = method.invoke(serviceSlot.getObject(), parameters.toArray());
            responseMessage.setResult(result);
        }catch(Exception e){
            responseMessage.setWrong(true);
            responseMessage.setExceptional(true);
            responseMessage.setExceptionInfo(e.getMessage());
            return responseMessage;
        }
        return responseMessage;
    }

    public RPCServerManager add(RPCServerInterfaceConfig config) throws Exception{
        Class theInterface = config.getInterface();
        Object theImplement = config.getImplement();
        if(theInterface == null){
            throw new RPCException("the interface can not be null");
        }
        if(theImplement == null){
            throw new RPCException("the implement can not be null");
        }
        if(!theInterface.isInstance(theImplement)){
            throw new RPCException("the implement is not implements the interface");
        }
        ServiceSlot serviceSlot = new ServiceSlot();
        serviceSlot.setObject(theImplement);
        Map<String,Method> methodMap = new HashMap<String,Method>();
        Method[] methods = theInterface.getMethods();
        for(Method method : methods){
            methodMap.put(getMethodKey(method),method);
        }
        serviceSlot.setMethods(methodMap);
        serviceSlotMap.put(config.getInterface().getName(),serviceSlot);
        return this;
    }

    private String getMethodKey(Method method){
        StringBuilder key = new StringBuilder(method.getName());
        key.append("/");
        for(Class c : method.getParameterTypes()){
            key.append(c.getName());
            key.append("/");
        }
        return key.toString();
    }

    private String getMethodKey(String methodName,List<Object> args){
        StringBuilder key = new StringBuilder(methodName);
        key.append("/");
        for(Object arg : args){
            key.append(arg.getClass().getName());
            key.append("/");
        }
        return key.toString();
    }






}
