package com.ederfmatos.burguerbot.handler;

import com.ederfmatos.burguerbot.service.BotService;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class BurgerBotSocketHandler extends AbstractWebSocketHandler {

    private final Gson gson;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss");
    private final BotService botService;

    public BurgerBotSocketHandler(Gson gson, BotService botService) {
        this.gson = gson;
        this.botService = botService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        MessageRequest messageRequest = gson.fromJson(message.getPayload(), MessageRequest.class);
        messageRequest.id = session.getId();
        log.info("Mensagem recebida {} às {}", messageRequest, LocalDateTime.now().format(dateTimeFormatter));

        String response = botService.respondMessage(messageRequest);
        session.sendMessage(new TextMessage(response));
    }

    @Getter
    public static final class MessageRequest {
        private String id;
        private String name;
        private String phoneNumber;
        private String message;

        @Override
        public String toString() {
            return "MessageRequest{" + "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

}