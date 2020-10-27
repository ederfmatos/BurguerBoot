package com.ederfmatos.burguerbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class MessageRequest {
    private String id;
    private String name;
    private String phoneNumber;
    private String message;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("phoneNumber", phoneNumber)
                .append("message", message)
                .toString();
    }
}