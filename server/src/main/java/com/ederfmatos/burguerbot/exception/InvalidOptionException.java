package com.ederfmatos.burguerbot.exception;

import com.ederfmatos.burguerbot.model.enumeration.EmojiEnum;
import com.ederfmatos.burguerbot.utils.BurgerBotMessages;

public class InvalidOptionException extends BurgerBotException {

    public InvalidOptionException() {
        super(EmojiEnum.CRY + " " + BurgerBotMessages.from("errors.invalid_option").string());
    }

    public InvalidOptionException(String message) {
        super(message);
    }

}
