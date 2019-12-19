package io.trabe.teaching.rest.model.accessor;


import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.exception.AccountAlreadyExistsException;
import io.trabe.teaching.rest.model.exception.AccountNotFoundException;
import io.trabe.teaching.rest.model.pojo.AccountKind;
import io.trabe.teaching.rest.model.pojo.backend.BackendAccount;

@Component
public class AccountAccessorImpl implements AccountAccessor {

    private final ConcurrentHashMap<String, List<BackendAccount>> storage;
    private Long lastId = 1L;

    public AccountAccessorImpl() {
        storage = new ConcurrentHashMap<>();
        createAccount("marcos", "Cuenta corriente", "ES43INGB2596749386", 122.50, AccountKind.CHECKING);
        createAccount("marcos", "Paraiso fiscal", "CH3489144371634777488", 1220000.50, AccountKind.SAVINGS);

    }

    @Override
    public List<BackendAccount> getUserAccounts(String login) {
        return storage.get(login);
    }

    @Override
    public BackendAccount getAccount(Long id) {
        Optional<BackendAccount> accountOptional = Seq.seq(storage.values())
                .flatMap(Seq::seq)
                .filter(account -> account.getId().equals(id))
                .findAny();
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        }
        throw new AccountNotFoundException();
    }

    @Override
    public BackendAccount createAccount(String login, String description, String code, double balance,
            AccountKind accountKind) {
        return createAccount(login, description, code, balance, accountKind, Calendar.getInstance());
    }

    @Override
    public BackendAccount createAccount(String login, String description, String code, double balance,
            AccountKind accountKind,
            Calendar creationDate) {
        if (Seq.seq(storage.values())
                .flatMap(Seq::seq)
                .filter(account -> account.getCode().equals(code))
                .isNotEmpty()) {

            throw new AccountAlreadyExistsException();
        }
        BackendAccount backendAccount = BackendAccount.builder()
                .id(getNextId())
                .login(login)
                .code(code)
                .balance(balance)
                .description(description)
                .creationDate(creationDate)
                .accountKind(accountKind)
                .build();
        if (storage.containsKey(login)) {
            storage.put(login, Seq.concat(Seq.seq(storage.get(login)), Seq.of(backendAccount)).toUnmodifiableList());
        } else {

            storage.put(login, Collections.singletonList(backendAccount));
        }
        return backendAccount;
    }

    @Override
    public void removeAccount(Long id) {
        Optional<String> ownerLogin = getAccountLogin(id);

        if (ownerLogin.isPresent()) {
            storage.put(ownerLogin.get(), Seq.seq(storage.get(ownerLogin.get()))
                    .filter(backendAccount -> !backendAccount.getId().equals(id))
                    .toUnmodifiableList());
        }

    }

    @Override
    public BackendAccount updateAccount(Long id, String description) {

        Optional<String> ownerLogin = getAccountLogin(id);

        if (ownerLogin.isPresent()) {
            List<BackendAccount> accounts = storage.get(ownerLogin);
            Optional<BackendAccount> backendAccountOptional = getAccountFromList(accounts, id);
            if (!backendAccountOptional.isPresent()) {
                throw new AccountNotFoundException();
            }
            BackendAccount account = backendAccountOptional.get();
            BackendAccount newAccount = BackendAccount.builder()
                    .id(account.getId())
                    .code(account.getCode())
                    .balance(account.getBalance())
                    .description(description)
                    .build();

            storage.put(ownerLogin.get(), Seq.concat(Seq.seq(accounts)
                            .filter(backendAccount -> backendAccount.getId() != id),
                    Seq.of(newAccount)).toUnmodifiableList());
            return newAccount;
        }
        throw new AccountNotFoundException();

    }

    private synchronized Long getNextId() {
        return lastId++;
    }

    private Optional<String> getAccountLogin(Long id) {
        return Seq.seq(storage.entrySet())
                .filter(entry -> Seq.seq(entry.getValue()).filter(account -> account.getId() == id).isNotEmpty())
                .map(Map.Entry::getKey)
                .findAny();
    }


    private Optional<BackendAccount> getAccountFromList(List<BackendAccount> accounts, Long accountId) {
        return Seq.seq(accounts)
                .filter(account -> account.getId() == accountId)
                .findAny();
    }
}
