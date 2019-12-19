package io.trabe.teaching.rest.model.accessor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Service;

import io.trabe.teaching.rest.model.pojo.PaymentMethodKind;
import io.trabe.teaching.rest.model.pojo.backend.BackendPaymentMethod;

@Service
public class PaymentMethodAccessorImpl implements PaymentMethodAccessor {

    private final ConcurrentHashMap<Long, List<BackendPaymentMethod>> storage;
    private Long lastId = 1L;

    public PaymentMethodAccessorImpl() {
        this.storage = new ConcurrentHashMap<>();
    }


    @Override
    public List<BackendPaymentMethod> getPaymentMethodsForAccount(Long accountId) {
        if (storage.get(accountId) != null) {
            return storage.get(accountId);
        }
        return Collections.emptyList();
    }

    @Override
    public BackendPaymentMethod createPaymentMethod(String alias, PaymentMethodKind kind, Long accountId) {
        BackendPaymentMethod paymentMethod = BackendPaymentMethod.builder()
                .id(getNextId())
                .alias(alias)
                .kind(kind)
                .build();

        if (storage.containsKey(accountId)) {
            storage.put(accountId,
                    Seq.concat(Seq.seq(storage.get(accountId)), Seq.of(paymentMethod)).toUnmodifiableList());
        } else {

            storage.put(accountId, Collections.singletonList(paymentMethod));
        }
        return paymentMethod;
    }

    private synchronized Long getNextId() {
        return lastId++;
    }
}

