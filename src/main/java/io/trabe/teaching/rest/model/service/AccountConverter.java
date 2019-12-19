package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.function.Function;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.backend.BackendAccount;

@Component
class AccountConverter {

    Function<BackendAccount, Account> convert() {
        return backendAccount -> Account.builder()
                .id(backendAccount.getId())
                .code(backendAccount.getCode())
                .description(backendAccount.getDescription())
                .balance(backendAccount.getBalance())
                .creationDate(backendAccount.getCreationDate())
                .kind(backendAccount.getAccountKind())
                .build();
    }

    Function<List<BackendAccount>, List<Account>> convertList() {
        return backendAccounts -> Seq.seq(backendAccounts)
                .map(convert())
                .toList();
    }
}
