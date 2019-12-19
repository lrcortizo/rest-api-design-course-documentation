package io.trabe.teaching.rest.model.pojo;

import java.util.Calendar;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Account {
    private Long id;
    private String code;
    private String description;
    private Double balance;
    private Calendar creationDate;
    private AccountKind kind;

}
