package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.function.Function;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.pojo.backend.BackendOperation;
import io.trabe.teaching.rest.model.pojo.Operation;

@Component
class OperationConverter {

    public Function<BackendOperation, Operation> convert() {
        return backendOperation -> Operation.builder()
                .id(backendOperation.getId())
                .date(backendOperation.getDate())
                .amount(backendOperation.getAmount())
                .kind(backendOperation.getKind())
                .description(backendOperation.getDescription())
                .paymentMethod(backendOperation.getPaymentMethod())
                .build();
    }

    Function<List<BackendOperation>, List<Operation>> convertList() {
        return backendOperations -> Seq.seq(backendOperations)
                .map(convert())
                .toList();
    }

}
