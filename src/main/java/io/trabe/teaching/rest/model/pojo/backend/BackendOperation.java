package io.trabe.teaching.rest.model.pojo.backend;

import java.time.LocalDateTime;

import io.trabe.teaching.rest.model.pojo.OperationKind;
import io.trabe.teaching.rest.model.pojo.PaymentMethod;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class BackendOperation {
    private Long id;
    private Long accountId;
    private OperationKind kind;
    private PaymentMethod paymentMethod;
    private LocalDateTime date;
    private String description;
    private Double amount;
}
