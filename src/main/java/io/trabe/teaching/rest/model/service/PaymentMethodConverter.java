package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.function.Function;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.pojo.backend.BackendPaymentMethod;
import io.trabe.teaching.rest.model.pojo.PaymentMethod;

@Component
class PaymentMethodConverter {

    public Function<BackendPaymentMethod, PaymentMethod> convert() {
        return backendPaymentMethod -> PaymentMethod.builder()
                .id(backendPaymentMethod.getId())
                .alias(backendPaymentMethod.getAlias())
                .kind(backendPaymentMethod.getKind())
                .build();
    }

    Function<List<BackendPaymentMethod>, List<PaymentMethod>> convertList() {
        return backendPaymentMethods -> Seq.seq(backendPaymentMethods)
                .map(convert())
                .toList();
    }

}
