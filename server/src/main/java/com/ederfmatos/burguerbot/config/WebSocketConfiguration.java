package com.ederfmatos.burguerbot.config;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final BurgerBotSocketHandler burgerBotSocketHandler;

    public WebSocketConfiguration(BurgerBotSocketHandler burgerBotSocketHandler) {
        this.burgerBotSocketHandler = burgerBotSocketHandler;
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(1024000);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(burgerBotSocketHandler, "/socket").setAllowedOrigins("*");
    }
}