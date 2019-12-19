package io.trabe.teaching.rest.model.service;

import java.util.List;

import io.trabe.teaching.rest.model.pojo.Account;

public interface ConsumeApiService {

    List<Account> getAccountsByUserLogin(Long userId);

    Account createAccount(String userLogin, Account account);
}
