package com.ederfmatos.burguerbot.model.enumeration;

public enum RequestEnum {

    SNACK("\uD83C\uDF54"),
    DRINK("\uD83C\uDF79");

    private final String emoji;

    RequestEnum(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
