package cn.xysomer.netty.rpc.provider;

import cn.xysomer.netty.rpc.api.RPCRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:23
 */
public class RPCServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> registryServiceMap;

    public RPCServerHandler(Map<String, Object> registryServiceMap) {
        this.registryServiceMap = registryServiceMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequest request = (RPCRequest) msg;
        String serviceName = request.getClassName();
        String version = request.getVersion();
        if (!StringUtils.isEmpty(version)) {
            serviceName += "-" + version;
        }
        Object service = registryServiceMap.get(serviceName);
        Class serviceClass = Class.forName(request.getClassName());
        Object[] args = request.getParameters();
        Method method;
        if (null != args) {
            Class<?>[] types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            method = serviceClass.getMethod(request.getMethodName(), types);
        } else {
            method = serviceClass.getMethod(request.getMethodName());
        }
        Object result = method.invoke(service, args);
        ctx.writeAndFlush(result);//异步发送应答消息给客户端
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
