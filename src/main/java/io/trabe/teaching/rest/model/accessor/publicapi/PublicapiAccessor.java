package io.trabe.teaching.rest.model.accessor.publicapi;

import java.util.List;

import io.trabe.teaching.rest.model.pojo.Account;

public interface PublicapiAccessor {

    List<Account> getUserAccounts(String userLogin);

    Account createAccount(String userLogin, Account account);

    Account getAccount(Long id);
}
