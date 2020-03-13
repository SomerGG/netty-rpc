package cn.xysomer.netty.rpc.client;

import java.lang.reflect.Proxy;

/**
 * @Description 客户端代理生成
 * @Author Somer
 * @Date 2020-03-13 09:33
 */
public class RPCClientProxy {

    public <T> T createClientProxy(final Class<?> interfaceClass, final String host, final int port, final String version) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new RPCInvocationHandler(host, port, version));
    }
}
