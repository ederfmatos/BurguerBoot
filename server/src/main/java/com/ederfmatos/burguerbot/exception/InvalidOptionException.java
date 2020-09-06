package com.ederfmatos.burguerbot.exception;

public class InvalidOptionException extends BurgerBotException {

    public InvalidOptionException() {
        super("Opção inválida");
    }

    public InvalidOptionException(String message) {
        super(message);
    }

}
