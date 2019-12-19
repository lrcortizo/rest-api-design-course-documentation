package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.pojo.backend.BackendUser;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.User;

@Component
class UserConverter {

    Function<BackendUser, User> convert(List<Account> accounts) {
        return backendUser -> User.builder()
                .id(backendUser.getId())
                .login(backendUser.getLogin())
                .name(backendUser.getName())
                .accounts(accounts)
                .build();
    }
}
