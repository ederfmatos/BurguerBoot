package com.ederfmatos.burguerbot.model.options;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Option {

    private final String value;
    private final String name;
    private List<Option> options;

    public Option(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public Option(String value, String name, Option... options) {
        this(value, name, Arrays.asList(options));
    }

    public Option(String value, String name, List<Option> options) {
        this(value, name);
        this.options = options;
    }

    public boolean hasOptions() {
        return this.getOptions() != null && this.getOptions().size() > 0;
    }

    public <T extends Option> T castTo(Class<T> classToCast) {
        return (T) this;
    }

}
