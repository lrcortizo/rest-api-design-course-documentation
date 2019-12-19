package io.trabe.teaching.rest.model.service;

import java.util.Calendar;
import java.util.List;

import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.AccountKind;
import io.trabe.teaching.rest.model.pojo.Operation;
import io.trabe.teaching.rest.model.pojo.PaymentMethod;
import io.trabe.teaching.rest.model.pojo.PaymentMethodKind;


public interface AccountService {

    List<Account> getUserAccounts(String login);

    Account getAccount(Long accountId);

    Account createAccount(String login, String description, String code, Double balance, AccountKind accountKind);

    Account createAccount(String login, String description, String code, Double balance, AccountKind accountKind,
            Calendar creationDate);

    void deleteAccount(Long accountId);

    Account updateAccount(Long accountId, String description);

    List<Operation> getAccountOperations(Long accountId);

    Operation createOperation(Long accountId, String description, Double amount, PaymentMethod paymentMethod);

    PaymentMethod createPaymentMethod(Long accountId, String alias, PaymentMethodKind kind);

    List<PaymentMethod> getAccountPaymentMethods(Long accountId);

}
