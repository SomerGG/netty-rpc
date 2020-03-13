package cn.xysomer.netty.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author Somer
 * @Date 2020-03-13 09:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String name;

    private int age;
}
