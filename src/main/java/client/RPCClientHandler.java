package client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;

    public void sendMessage(Object message){
        if(ctx == null){
            return;
        }
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);

    }


}
