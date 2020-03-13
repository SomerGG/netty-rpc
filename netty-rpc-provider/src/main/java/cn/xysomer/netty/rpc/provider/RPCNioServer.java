package cn.xysomer.netty.rpc.provider;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:09
 */
public class RPCNioServer implements ApplicationContextAware, InitializingBean {

    private int port;

    public RPCNioServer(int port) {
        this.port = port;
    }

    EventLoopGroup boosGroup = null;

    EventLoopGroup workerGroup = null;

    private Map<String, Object> registryServiceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化 NIO Server
        ServerBootstrap server = new ServerBootstrap();
        try {
            boosGroup = new NioEventLoopGroup();//用于服务端接收客户端连接
            workerGroup = new NioEventLoopGroup();//用于进行 SocketChannel 的读写
            server.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//设置创建的 Channel 为 NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 1024)//针对主线程的配置，最大分配线程数量设置为1024
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//对于子线程的配置，保持长连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //设置协议解码器
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            //设置协议编码器
                            pipeline.addLast(new LengthFieldPrepender(4));
                            //设置对象编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //设置对象解码器
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //设置自定义处理逻辑
                            pipeline.addLast("handler", new RPCServerHandler(registryServiceMap));
                        }
                    });
            ChannelFuture future = server.bind(port).sync();//绑定监听端口，同步等待绑定操作成功，返回一个用于异步操作的通知回调
            System.out.println("NIO Server Start Listen Port：" + port);
            future.channel().closeFuture().sync();//等待服务端监听端口关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//优雅关闭线程池
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RPCService.class);
        if (!serviceMap.isEmpty()) {
            for (Object serviceBean : serviceMap.values()) {
                RPCService rpcService = serviceBean.getClass().getAnnotation(RPCService.class);
                String serviceName = rpcService.value().getName();
                String version = rpcService.version();
                if (!StringUtils.isEmpty(version)) {
                    serviceName += "-" + version;
                }
                registryServiceMap.put(serviceName, serviceBean);
            }
        }
    }
}
