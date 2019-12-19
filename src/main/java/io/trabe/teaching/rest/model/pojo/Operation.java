package io.trabe.teaching.rest.model.pojo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Operation {
    private Long id;
    private OperationKind kind;
    private PaymentMethod paymentMethod;
    private LocalDateTime date;
    private String description;
    private Double amount;

}
