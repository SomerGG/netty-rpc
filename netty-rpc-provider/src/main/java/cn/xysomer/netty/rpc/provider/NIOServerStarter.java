package cn.xysomer.netty.rpc.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description NIO Server启动
 * @Author Somer
 * @Date 2020-03-13 09:30
 */
public class NIOServerStarter {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(NIOServerConfig.class);
        ((AnnotationConfigApplicationContext) applicationContext).start();
    }
}
