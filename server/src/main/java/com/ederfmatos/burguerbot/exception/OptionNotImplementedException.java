package com.ederfmatos.burguerbot.exception;

import com.ederfmatos.burguerbot.utils.BurgerBotMessages;

public class OptionNotImplementedException extends BurgerBotException {

    public OptionNotImplementedException() {
        super(BurgerBotMessages.from("errors.option_not_implemented").string());
    }

}
