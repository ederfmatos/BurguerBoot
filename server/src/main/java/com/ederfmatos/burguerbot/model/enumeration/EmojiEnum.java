package com.ederfmatos.burguerbot.model.enumeration;

public enum EmojiEnum {

    DRINK("\uD83E\uDD43"),
    PORTION("\uD83E\uDDC6"),
    BURGER("\uD83C\uDF54"),
    WATCH("⏰"),
    ESTABLISHMENT("\uD83C\uDFEF"),
    PHONE("\uD83D\uDCF1"),
    HEARTH("❤"),
    CRY("\uD83D\uDE30"),
    NOTES("\uD83D\uDCDD"),
    ;

    private final String emoji;

    EmojiEnum(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return this.emoji;
    }
}
