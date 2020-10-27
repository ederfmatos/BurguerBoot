package com.ederfmatos.burguerbot.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class MessageResponse {

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