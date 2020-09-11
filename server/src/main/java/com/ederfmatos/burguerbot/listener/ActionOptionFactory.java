package com.ederfmatos.burguerbot.listener;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActionOptionFactory {

    private final List<ActionExecutable> executables;

    public ActionOptionFactory(List<ActionExecutable> executables) {
        this.executables = executables;
    }

    public ActionExecutable build(ActionOption option) {
        return this.executables.stream()
                .filter(executable -> executable.isInstanceOf(option))
                .findFirst()
                .orElseThrow(InvalidOptionException::new);
    }

}
