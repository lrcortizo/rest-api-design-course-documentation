package io.trabe.teaching.rest.model.pojo.backend;

import java.util.Calendar;

import io.trabe.teaching.rest.model.pojo.AccountKind;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BackendAccount {
    private Long id;
    private String login;
    private String code;
    private String description;
    private Double balance;
    private Calendar creationDate;
    private AccountKind accountKind;

}
