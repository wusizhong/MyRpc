package com.wsz;

import com.wsz.common.URL;
import com.wsz.protocol.HttpServer;
import com.wsz.register.LocalRegister;
import com.wsz.register.RemoteMapRegister;

public class Provider {
    public static void main(String[] args) {
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

        //注册中心注册
        //获取当前机器的host和port

        URL url = new URL("localhost", 8080);
        RemoteMapRegister.register(HelloService.class.getName(), url);

        //Netty、Tomcat
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHost(), url.getPort());
    }
}
