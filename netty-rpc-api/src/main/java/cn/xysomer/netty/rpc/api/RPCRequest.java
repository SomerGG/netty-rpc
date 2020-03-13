package cn.xysomer.netty.rpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description RPC 请求封装
 * @Author Somer
 * @Date 2020-03-13 09:01
 */
@Data
public class RPCRequest implements Serializable {

    private String className;//请求服务类

    private String methodName;//请求方法名

    private Object[] parameters;//请求参数

    private String version;//服务版本号
}
