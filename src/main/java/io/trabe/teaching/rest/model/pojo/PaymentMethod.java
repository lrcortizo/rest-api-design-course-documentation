package io.trabe.teaching.rest.model.pojo;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentMethod {
    private Long id;
    private String alias;
    private PaymentMethodKind kind;
}
