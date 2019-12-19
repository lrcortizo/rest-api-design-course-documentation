package io.trabe.teaching.rest.model.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import io.trabe.teaching.rest.model.accessor.AccountAccessor;
import io.trabe.teaching.rest.model.accessor.PaymentMethodAccessor;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.AccountKind;
import io.trabe.teaching.rest.model.pojo.Operation;
import io.trabe.teaching.rest.model.pojo.PaymentMethod;
import io.trabe.teaching.rest.model.pojo.PaymentMethodKind;
import io.trabe.teaching.rest.model.accessor.OperationAccessor;
import io.trabe.teaching.rest.model.service.mapper.AccountMapper;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountAccessor accountAccessor;
    private final OperationAccessor operationAccessor;
    private final PaymentMethodAccessor paymentMethodAccessor;
    private final AccountConverter accountConverter;
    private final OperationConverter operationConverter;
    private final PaymentMethodConverter paymentMethodConverter;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountAccessor accountAccessor,
            AccountConverter accountConverter,
            OperationAccessor operationAccessor,
            PaymentMethodAccessor paymentMethodAccessor,
            OperationConverter operationConverter,
            PaymentMethodConverter paymentMethodConverter,
            AccountMapper accountMapper) {
        this.accountAccessor = accountAccessor;
        this.accountConverter = accountConverter;
        this.operationAccessor = operationAccessor;
        this.paymentMethodAccessor = paymentMethodAccessor;
        this.operationConverter = operationConverter;
        this.paymentMethodConverter = paymentMethodConverter;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<Account> getUserAccounts(String login) {
        return accountMapper.convert(accountAccessor.getUserAccounts(login));

    }

    @Override
    public Account getAccount(Long accountId) {

        return accountConverter.convert().apply(accountAccessor.getAccount(accountId));
    }

    @Override
    public Account createAccount(String login, String description, String code, Double balance,
            AccountKind accountKind) {
        return accountConverter.convert()
                .apply(accountAccessor.createAccount(login, description, code, balance, accountKind));
    }

    @Override
    public Account createAccount(String login, String description, String code, Double balance, AccountKind accountKind,
            Calendar creationDate) {
        return accountConverter.convert()
                .apply(accountAccessor.createAccount(login, description, code, balance, accountKind, creationDate));
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountAccessor.removeAccount(accountId);

    }

    @Override
    public Account updateAccount(Long accountId, String description) {
        return accountConverter.convert().apply(accountAccessor.updateAccount(accountId, description));
    }

    @Override
    public List<Operation> getAccountOperations(Long accountId) {
        return operationConverter.convertList().apply(operationAccessor.getOperationsForAccount(accountId));
    }

    @Override
    public Operation createOperation(Long accountId, String description, Double amount, PaymentMethod paymentMethod) {
        return operationConverter.convert()
                .apply(operationAccessor.createOperation(amount, description, paymentMethod, accountId));
    }

    @Override
    public PaymentMethod createPaymentMethod(Long accountId, String alias, PaymentMethodKind kind) {
        return paymentMethodConverter.convert()
                .apply(paymentMethodAccessor.createPaymentMethod(alias, kind, accountId));
    }

    @Override
    public List<PaymentMethod> getAccountPaymentMethods(Long accountId) {
        return paymentMethodConverter.convertList().apply(paymentMethodAccessor.getPaymentMethodsForAccount(accountId));
    }
}
