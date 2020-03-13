package cn.xysomer.netty.rpc.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:29
 */
@Configuration
@ComponentScan(basePackages = "cn.xysomer.netty.rpc.provider")
public class NIOServerConfig {

    @Bean(name = "rpcNioServer")
    public RPCNioServer rpcNioServer() {
        return new RPCNioServer(8080);
    }
}
