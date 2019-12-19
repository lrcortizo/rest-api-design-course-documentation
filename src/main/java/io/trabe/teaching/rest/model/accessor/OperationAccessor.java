package io.trabe.teaching.rest.model.accessor;

import java.util.List;
import java.util.Optional;

import io.trabe.teaching.rest.model.pojo.PaymentMethod;
import io.trabe.teaching.rest.model.pojo.backend.BackendOperation;

public interface OperationAccessor {

    List<BackendOperation> getOperationsForAccount(Long accountId);

    List<BackendOperation> getOperationsByAccountAndPaymentMethod(Long accountId, Long paymentMethodId);

    BackendOperation createOperation(Double amount, String description, PaymentMethod paymentMethod, Long accountId);

    Optional<BackendOperation> get(Long id);
}
