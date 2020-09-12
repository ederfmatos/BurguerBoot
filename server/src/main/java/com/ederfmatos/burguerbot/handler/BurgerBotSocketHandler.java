package com.ederfmatos.burguerbot.handler;

import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.service.BotService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
public class BurgerBotSocketHandler extends AbstractWebSocketHandler {

    private final Gson gson;
    private final BotService botService;
    private static WebSocketSession session;

    public BurgerBotSocketHandler(Gson gson, BotService botService) {
        this.gson = gson;
        this.botService = botService;
    }

    public static boolean send(String message) {
        if (session == null) {
            return false;
        }

        try {
            session.sendMessage(new TextMessage(message));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        BurgerBotSocketHandler.session = session;
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        MessageRequest messageRequest = gson.fromJson(message.getPayload(), MessageRequest.class);
        messageRequest.setId(session.getId());
        log.info("Mensagem recebida {}", messageRequest);

        String response = botService.respondMessage(messageRequest);
        session.sendMessage(new TextMessage(response));
    }



}