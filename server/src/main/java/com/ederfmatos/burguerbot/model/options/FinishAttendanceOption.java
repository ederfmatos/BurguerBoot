package com.ederfmatos.burguerbot.model.options;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FinishAttendanceOption extends ActionOption {

    public FinishAttendanceOption(String value, String name) {
        super(value, name);
    }

}
