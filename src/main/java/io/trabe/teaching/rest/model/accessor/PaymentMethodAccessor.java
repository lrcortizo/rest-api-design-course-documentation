package io.trabe.teaching.rest.model.accessor;

import java.util.List;

import io.trabe.teaching.rest.model.pojo.PaymentMethodKind;
import io.trabe.teaching.rest.model.pojo.backend.BackendPaymentMethod;

public interface PaymentMethodAccessor {

    List<BackendPaymentMethod> getPaymentMethodsForAccount(Long accountId);

    BackendPaymentMethod createPaymentMethod(String alias, PaymentMethodKind kind, Long accountId);
}
