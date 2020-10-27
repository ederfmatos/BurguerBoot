package com.ederfmatos.burguerbot.handler;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.MessageResponse;
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

    private static final Gson gson = new Gson();
    private final BotService botService;
    private static WebSocketSession session;

    public BurgerBotSocketHandler(BotService botService) {
        this.botService = botService;
    }

    public static boolean send(Attendance attendance, String message) {
        if (session == null) {
            return false;
        }

        try {
            MessageResponse messageResponse = MessageResponse.builder()
                    .id(attendance.getCustomer().getId())
                    .message(message)
                    .name(attendance.getCustomer().getName())
                    .phoneNumber(attendance.getCustomer().getPhoneNumber())
                    .build();

            sendMessage(session, messageResponse);
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
        log.info("Mensagem recebida {}", messageRequest);

        String response = botService.respondMessage(messageRequest);

        MessageResponse messageResponse = MessageResponse.builder()
                .id(messageRequest.getId())
                .message(response)
                .name(messageRequest.getName())
                .phoneNumber(messageRequest.getPhoneNumber())
                .build();

        sendMessage(session, messageResponse);
    }

    private static void sendMessage(WebSocketSession session, MessageResponse messageResponse) throws IOException {
        session.sendMessage(new TextMessage(gson.toJson(messageResponse)));
    }

}