package cn.xysomer.netty.rpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:51
 */
@Configuration
public class RPCClientConfig {

    @Bean(name = "rpcClientProxy")
    public RPCClientProxy rpcClientProxy() {
        return new RPCClientProxy();
    }
}
