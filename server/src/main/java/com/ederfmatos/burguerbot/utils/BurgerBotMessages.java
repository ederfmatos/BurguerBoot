package com.ederfmatos.burguerbot.utils;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

@Component
public class BurgerBotMessages {

    private String messageKey;
    private final Locale locale = new Locale("pt-br");
    private final Map<String, Object> replaces;

    private BurgerBotMessages() {
        this.replaces = new HashMap<>();
    }

    public BurgerBotMessages setMessageKey(String messageKey) {
        this.messageKey = messageKey;
        return this;
    }

    public static BurgerBotMessages from(String messageKey) {
        return new BurgerBotMessages().setMessageKey(messageKey);
    }

    public BurgerBotMessages format(String key, Object value) {
        this.replaces.put(key, value);
        return this;
    }

    public String string() {
        String message = ResourceBundleMessageSourceSingleton.getInstance().getMessage(messageKey, null, locale);

        for (String key : replaces.keySet()) {
            message = message.replace("{" + key + "}", replaces.get(key).toString());
        }

        return message;
    }

    private static class ResourceBundleMessageSourceSingleton {

        private static ResourceBundleMessageSource instance;

        public static ResourceBundleMessageSource getInstance() {
            if (instance == null) {
                instance = new ResourceBundleMessageSource();
                instance.setBasenames("messages");
                instance.setDefaultEncoding("UTF-8");
                instance.setUseCodeAsDefaultMessage(true);
            }

            return instance;
        }
    }
}
