package com.wsz.Proxy;

import com.wsz.common.Invocation;
import com.wsz.common.URL;
import com.wsz.loadbalance.LoadBalance;
import com.wsz.protocol.HttpClient;
import com.wsz.register.RemoteMapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {
    public static <T> T getProxy(Class clazz) {
        Object proxyInstance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Invocation invocation = new Invocation(clazz.getName(),
                        method.getName(), method.getParameterTypes(), args);
                HttpClient httpClient = new HttpClient();

                //服务发现
                List<URL> urlList = RemoteMapRegister.get(clazz.getName());

                //服务调用
                List<URL> invokeUrls = new ArrayList<>();

                int max = 4;
                while (true) {
                    max--;

                    urlList.remove(invokeUrls);
                    //负载均衡
                    URL url = LoadBalance.random(urlList);
                    invokeUrls.add(url);

                    String result;
                    try {
                        result = httpClient.send(url.getHost(), url.getPort(), invocation);
                    } catch (Exception e) {
                        //服务重试 3次
                        if(max > 0) continue;

                        //服务容错
                        //error-callback com.wsz.ErrorCallbackService
                        return "error";
                    }
                    return result;
                }
            }
        });
        return (T) proxyInstance;
    }
}
