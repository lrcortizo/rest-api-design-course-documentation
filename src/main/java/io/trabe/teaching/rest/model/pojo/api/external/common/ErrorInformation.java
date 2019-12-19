package io.trabe.teaching.rest.model.pojo.api.external.common;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ErrorInformation {
    private String exception;
}
