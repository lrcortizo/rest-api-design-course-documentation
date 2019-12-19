package io.trabe.teaching.rest.model.pojo.backend;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BackendUser {
    private Long id;
    private String name;
    private String login;
}
