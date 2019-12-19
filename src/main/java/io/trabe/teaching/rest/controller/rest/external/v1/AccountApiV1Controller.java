package io.trabe.teaching.rest.controller.rest.external.v1;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.trabe.teaching.rest.controller.mapper.ExternalApiMapper;
import io.trabe.teaching.rest.controller.rest.external.annotation.ExternalApiV1;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccount;
import io.trabe.teaching.rest.model.service.AccountService;
import io.trabe.teaching.rest.model.service.UserService;

@RestController
@ExternalApiV1
@Api(tags={"account"})
public class AccountApiV1Controller {

    private final AccountService accountService;
    private final UserService userService;
    private final ExternalApiMapper externalApiMapper;

    public AccountApiV1Controller(AccountService accountService,
            UserService userService, ExternalApiMapper externalApiMapper) {
        this.accountService = accountService;
        this.userService = userService;
        this.externalApiMapper = externalApiMapper;
    }


    @GetMapping("/users/{userId}/accounts/{accountId}")
    @Deprecated
    public ApiAccount getAccount(@PathVariable Long accountId) {
        return externalApiMapper.toAccountApi(accountService.getAccount(accountId));
    }
    @Deprecated
    @DeleteMapping("/users/{userId}/accounts/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
    }
}
