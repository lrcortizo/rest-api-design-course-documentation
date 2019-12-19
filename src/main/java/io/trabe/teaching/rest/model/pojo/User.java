package io.trabe.teaching.rest.model.pojo;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    private Long id;
    private String name;
    private String login;
    private List<Account> accounts;
}
