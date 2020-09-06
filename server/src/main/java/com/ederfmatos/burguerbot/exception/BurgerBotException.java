package com.ederfmatos.burguerbot.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BurgerBotException extends RuntimeException {

    public BurgerBotException(String message) {
        super(message);
    }

    public void showError() {
        log.error(this.getMessage());
    }

}
