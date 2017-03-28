package common;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Greeting on 2017/3/28.
 */
public class RPCEncoder extends MessageToByteEncoder {
    private final int buffSize = 4096;
    private Class messageClass;
    private RuntimeSchema schema;

    public RPCEncoder(Class messageClass){
        this.messageClass = messageClass;
        this.schema = RuntimeSchema.createFrom(messageClass);
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
        if(!messageClass.isInstance(in)){
            return;
        }
        byte[] bytes = ProtostuffIOUtil.toByteArray(in,schema,LinkedBuffer.allocate(buffSize));
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
