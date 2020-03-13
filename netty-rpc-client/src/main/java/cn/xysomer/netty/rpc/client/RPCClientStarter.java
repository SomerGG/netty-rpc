package cn.xysomer.netty.rpc.client;

import cn.xysomer.netty.rpc.api.IHelloService;
import cn.xysomer.netty.rpc.api.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description 客户端测试
 * @Author Somer
 * @Date 2020-03-13 09:52
 */
public class RPCClientStarter {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RPCClientConfig.class);
        RPCClientProxy rpcClientProxy = applicationContext.getBean(RPCClientProxy.class);
        IHelloService helloService = rpcClientProxy.createClientProxy(IHelloService.class, "localhost", 8080, "v2");
        String sayHello = helloService.sayHello("Somer");
        System.out.println(sayHello);
        String saveUser = helloService.saveUser(new User("Somer", 18));
        System.out.println(saveUser);
    }
}
