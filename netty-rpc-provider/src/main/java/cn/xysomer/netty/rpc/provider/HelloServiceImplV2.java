package cn.xysomer.netty.rpc.provider;

import cn.xysomer.netty.rpc.api.IHelloService;
import cn.xysomer.netty.rpc.api.User;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:04
 */
@RPCService(value = IHelloService.class, version = "v2")
public class HelloServiceImplV2 implements IHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("[V2.0] sayHello()：" + name);
        return "[V2.0] sayHello()：" + name;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("[V2.0] saveUser()：" + user.toString());
        return "[V2.0] saveUser()：" + user.toString();
    }
}
