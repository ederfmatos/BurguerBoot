package com.ederfmatos.burgerbot.handler;

import com.ederfmatos.burgerbot.model.MessageRequest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@Component
public class BurgerBotSocketHandler extends AbstractWebSocketHandler {

    Logger LOG = LoggerFactory.getLogger(BurgerBotSocketHandler.class);

    private final Gson gson;

    public BurgerBotSocketHandler(Gson gson) {
        this.gson = gson;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        MessageRequest messageRequest = gson.fromJson(message.getPayload(), MessageRequest.class);
        messageRequest.setId(session.getId());
        LOG.info("Mensagem recebida {}", messageRequest);

        String response = "oba";
        session.sendMessage(new TextMessage(response));
    }

}