package com.kamegatze.code_generation.config.bean_config;

import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

@Configuration
public class CreateBean {

    @Bean
    public RestOperations getRestOperations() {

        /*
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress("mivlgu-proxy.lan.mivlgu.ru", 3128));

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);*/

        return new RestTemplate();
    }
}
