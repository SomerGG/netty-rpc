package cn.xysomer.netty.rpc.api;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:00
 */
public interface IHelloService {

    String sayHello(String name);

    String saveUser(User user);
}
