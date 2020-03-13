package cn.xysomer.netty.rpc.client;

import cn.xysomer.netty.rpc.api.RPCRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description 代理生成拦截器
 * @Author Somer
 * @Date 2020-03-13 09:36
 */
public class RPCInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    private String version;

    public RPCInvocationHandler(String host, int port, String version) {
        this.host = host;
        this.port = port;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(this.version);

        //远程通信
        RPCNetTransport rpcNetTransport = new RPCNetTransport(host, port);
        Object result = rpcNetTransport.send(request);
        return result;
    }
}
