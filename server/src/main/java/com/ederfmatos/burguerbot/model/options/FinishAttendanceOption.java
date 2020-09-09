package com.ederfmatos.burguerbot.model.options;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FinishAttendanceOption extends ActionOption {

    public FinishAttendanceOption() {
        super("3", "Finalizar atendimento");
    }

}
