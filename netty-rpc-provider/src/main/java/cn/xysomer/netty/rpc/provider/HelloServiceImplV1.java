package cn.xysomer.netty.rpc.provider;

import cn.xysomer.netty.rpc.api.IHelloService;
import cn.xysomer.netty.rpc.api.User;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:04
 */
@RPCService(value = IHelloService.class, version = "v1")
public class HelloServiceImplV1 implements IHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("[V1.0] sayHello()：" + name);
        return "[V1.0] sayHello()：" + name;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("[V1.0] saveUser()：" + user.toString());
        return "[V1.0] saveUser()：" + user.toString();
    }
}
