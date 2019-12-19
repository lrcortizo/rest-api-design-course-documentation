package io.trabe.teaching.rest.model.pojo.backend;

import io.trabe.teaching.rest.model.pojo.PaymentMethodKind;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BackendPaymentMethod {
    private Long id;
    private String alias;
    private PaymentMethodKind kind;
}
