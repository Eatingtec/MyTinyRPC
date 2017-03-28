package client;

import common.RPCDecoder;
import common.RPCEncoder;
import common.RPCRequestMessage;
import common.RPCResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Greeting on 2017/3/27.
 */
public class RPCClient{
    private RPCRealClient rpcRealClient;
    private ConcurrentHashMap<String,RPCRecord> resultMap;
    public RPCClient() {
        resultMap = new ConcurrentHashMap<String, RPCRecord>();
    }
    public void connect(String host,int port) {
        this.rpcRealClient = new RPCRealClient(host,port,resultMap);
        Thread thread = new Thread(rpcRealClient);
        thread.start();
        rpcRealClient.waitReady();
    }


    public RPCResponseMessage remoteInvoke(String interfaceName, Method method,Object[] args) throws Exception{
        String uuid = rpcRealClient.remoteInvoke(interfaceName, method, args);
        RPCRecord record = resultMap.get(uuid);
        record.getReady().wait();
        RPCResponseMessage responseMessage = record.getRpcResponseMessage();
        resultMap.remove(uuid);
        return responseMessage;
    }
}

class RPCRealClient implements Runnable{
    private String host;
    private int port;
    private RPCEncoder rpcEncoder;
    private RPCDecoder rpcDecoder;
    private RPCClientHandler rpcClientHandler;
    private ConcurrentHashMap<String,RPCRecord> resultMap;
    private Object ready;

    public RPCRealClient(String host,int port,ConcurrentHashMap<String,RPCRecord> resultMap){
        this.host = host;
        this.port = port;
        rpcEncoder = new RPCEncoder(RPCRequestMessage.class);
        rpcDecoder = new RPCDecoder(RPCResponseMessage.class);
        rpcClientHandler = new RPCClientHandler(resultMap);
    }

    public void run() {
        try {
            this.connect(this.host, this.port);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void connect(String host, int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(rpcEncoder);
                            socketChannel.pipeline().addLast(rpcDecoder);
                            socketChannel.pipeline().addLast(rpcClientHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(host,port).sync();
            ready.notify();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public void waitReady(){
        try{
            ready.wait();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public String remoteInvoke(String interfaceName, Method method,Object[] args){
        return this.rpcClientHandler.remoteInvoke(interfaceName, method, args);
    }
}
