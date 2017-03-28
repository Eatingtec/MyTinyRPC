package client;

import common.RPCRequestMessage;
import common.RPCResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ConcurrentHashMap<String,RPCRecord> resultMap;

    public RPCClientHandler(ConcurrentHashMap<String,RPCRecord> resultMap){
        this.resultMap = resultMap;
    }

    public String remoteInvoke(String interfaceName, Method method, Object[] args){

        if(ctx == null){
            return null;
        }

        RPCRequestMessage message = new RPCRequestMessage();
        message.setId(UUID.randomUUID().toString());
        message.setInterfaceName(interfaceName);
        message.setMethodName(method.getName());
        List<Object> parameters = Arrays.asList(args);
        message.setParameters(parameters);
        RPCRecord record = new RPCRecord();
        record.setId(message.getId());
        record.setReady(new Object());
        record.setTime(new Date().getTime());
        resultMap.put(message.getId(),record);


        ctx.writeAndFlush(message);
        return message.getId();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCResponseMessage responseMessage = (RPCResponseMessage)msg;
        String requestId = responseMessage.getRequestId();
        RPCRecord record = resultMap.get(requestId);
        record.setRpcResponseMessage(responseMessage);
        record.setId(requestId);
        record.setTime(new Date().getTime());
        record.getReady().notify();
    }


}
