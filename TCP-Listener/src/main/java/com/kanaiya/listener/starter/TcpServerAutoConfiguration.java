package com.kanaiya.listener.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kanaiya.listener.tcp.Server;
import com.kanaiya.listener.tcp.TcpControllerBeanPostProcessor;
import com.kanaiya.listener.tcp.TcpServer;
import com.kanaiya.listener.tcp.TcpServerAutoStarterApplicationListener;

@Configuration
@EnableConfigurationProperties(TcpServerProperties.class)
@ConditionalOnProperty(prefix = "kanaiya.tcp-server", name = {"port", "autostart"})
public class TcpServerAutoConfiguration {

    @Bean
    TcpServerAutoStarterApplicationListener tcpServerAutoStarterApplicationListener() {
        return new TcpServerAutoStarterApplicationListener();
    }

    @Bean
    TcpControllerBeanPostProcessor tcpControllerBeanPostProcessor() {
        return new TcpControllerBeanPostProcessor();
    }

    @Bean
    Server server(){
        return new TcpServer();
    }
}
