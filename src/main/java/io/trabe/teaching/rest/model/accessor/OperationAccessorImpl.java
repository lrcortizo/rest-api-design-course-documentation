package io.trabe.teaching.rest.model.accessor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Service;

import io.trabe.teaching.rest.model.pojo.PaymentMethod;
import io.trabe.teaching.rest.model.pojo.backend.BackendOperation;

@Service
public class OperationAccessorImpl implements OperationAccessor {

    private final ConcurrentHashMap<Long, List<BackendOperation>> storage;
    private Long lastId = 1L;

    public OperationAccessorImpl() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public List<BackendOperation> getOperationsForAccount(Long accountId) {
        if (storage.get(accountId) != null) {
            return storage.get(accountId);
        }
        return Collections.emptyList();
    }

    @Override
    public List<BackendOperation> getOperationsByAccountAndPaymentMethod(Long accountId, Long paymentMethodId) {
        return Seq.seq(getOperationsForAccount(accountId))
                .filter(backendOperation -> backendOperation.getPaymentMethod().getId().equals(paymentMethodId))
                .toList();
    }

    @Override
    public BackendOperation createOperation(Double amount, String description, PaymentMethod paymentMethod,
            Long accountId) {

        BackendOperation operation = BackendOperation.builder()
                .id(getNextId())
                .accountId(accountId)
                .amount(amount)
                .description(description)
                .date(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .build();

        if (storage.containsKey(accountId)) {
            storage.put(accountId, Seq.concat(Seq.seq(storage.get(accountId)), Seq.of(operation)).toUnmodifiableList());
        } else {

            storage.put(accountId, Collections.singletonList(operation));
        }
        return operation;
    }

    @Override
    public Optional<BackendOperation> get(Long id) {
        return Seq.seq(storage.values())
                .flatMap(Seq::seq)
                .findFirst(backendOperation -> backendOperation.getId().equals(id));
    }

    private synchronized Long getNextId() {
        return lastId++;
    }


}
