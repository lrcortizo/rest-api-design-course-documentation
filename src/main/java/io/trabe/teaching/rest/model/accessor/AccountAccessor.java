package io.trabe.teaching.rest.model.accessor;

import java.util.Calendar;
import java.util.List;

import io.trabe.teaching.rest.model.pojo.AccountKind;
import io.trabe.teaching.rest.model.pojo.backend.BackendAccount;


public interface AccountAccessor {

    List<BackendAccount> getUserAccounts(String login);

    BackendAccount getAccount(Long id);


    BackendAccount createAccount(String login, String description, String code, double balance,
            AccountKind accountKind);

    BackendAccount createAccount(String login, String description, String code, double balance, AccountKind accountKind,
            Calendar creationDate);

    void removeAccount(Long id);

    BackendAccount updateAccount(Long id, String description);
}
