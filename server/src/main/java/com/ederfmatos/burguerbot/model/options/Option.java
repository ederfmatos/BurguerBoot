package com.ederfmatos.burguerbot.model.options;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

@Document(collection = "options")
public class Option {

    @Id
    protected String id;
    protected String value;
    protected String name;
    protected List<? extends Option> options;

    public Option() {
    }

    public Option(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public Option(String value, String name, List<? extends Option> options) {
        this(value, name);
        this.options = options;
    }

    public Option(String value, String name, Option... options) {
        this(value, name, Arrays.asList(options));
    }

    public String getId() {
        return id;
    }

    public Option setId(String id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public List<? extends Option> getOptions() {
        return options;
    }

    public Option setOptions(List<? extends Option> options) {
        this.options = options;
        return this;
    }

    public boolean hasOptions() {
        return this.getOptions() != null && this.getOptions().size() > 0;
    }
    
}
