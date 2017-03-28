package server;

import common.RPCRequestMessage;
import common.RPCResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Greeting on 2017/3/28.
 */
public class RPCServerHandler extends ChannelInboundHandlerAdapter {

    private RPCServerManager rpcServerManager;

    public RPCServerHandler(RPCServerManager manager){
        rpcServerManager = manager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequestMessage requestMessage = (RPCRequestMessage)msg;
        RPCResponseMessage responseMessage = rpcServerManager.invoke(requestMessage);
        ctx.writeAndFlush(responseMessage);
    }
}
