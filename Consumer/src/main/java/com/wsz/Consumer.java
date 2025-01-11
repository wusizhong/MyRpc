package com.wsz;

import com.wsz.Proxy.ProxyFactory;

public class Consumer {
    public static void main(String[] args) {

        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("你好wsz");
        System.out.println(result);

    }
}
