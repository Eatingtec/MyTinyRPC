package server;

import common.RPCDecoder;
import common.RPCEncoder;
import common.RPCRequestMessage;
import common.RPCResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCServer{
    private int port;
    private RPCEncoder rpcEncoder;
    private RPCDecoder rpcDecoder;
    private RPCServerHandler rpcServerHandler;

    public RPCServer(int port, RPCServerManager manager){
        this.port = port;
        rpcEncoder = new RPCEncoder(RPCResponseMessage.class);
        rpcDecoder = new RPCDecoder(RPCRequestMessage.class);
        rpcServerHandler = new RPCServerHandler(manager);
    }

    public void start() {
        try{
            this.bind();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void bind() throws Exception{
        EventLoopGroup masterGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(masterGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(rpcEncoder);
                            socketChannel.pipeline().addLast(rpcDecoder);
                            socketChannel.pipeline().addLast(rpcServerHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
