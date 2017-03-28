package common;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Greeting on 2017/3/28.
 */
public class RPCDecoder extends ByteToMessageDecoder {
    private RuntimeSchema schema;

    public RPCDecoder(Class messageClass){
        this.schema = RuntimeSchema.createFrom(messageClass);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if( in.readableBytes() < 4 ){
            return;
        }
        in.markReaderIndex();
        int messageLength = in.readInt();
        if(messageLength < 0){
            ctx.close();
        }
        if(in.readableBytes() < messageLength){
            in.resetReaderIndex();
        }
        byte[] bytes = new byte[messageLength];
        in.readBytes(bytes);
        Object message = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, message, schema);
        out.add(message);
    }
}
