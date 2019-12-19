package io.trabe.teaching.rest.controller.rest.external.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.trabe.teaching.rest.controller.mapper.ExternalApiMapper;
import io.trabe.teaching.rest.controller.rest.external.annotation.ExternalApiV2;
import io.trabe.teaching.rest.model.pojo.api.external.v2.ApiExtendedAccount;
import io.trabe.teaching.rest.model.service.AccountService;
import io.trabe.teaching.rest.model.service.UserService;

@RestController
@ExternalApiV2
@Api(tags={"account"})
public class AccountApiV2Controller {

    private final AccountService accountService;
    private final UserService userService;
    private final ExternalApiMapper externalApiMapper;

    public AccountApiV2Controller(AccountService accountService,
            UserService userService, ExternalApiMapper externalApiMapper) {
        this.accountService = accountService;
        this.userService = userService;
        this.externalApiMapper = externalApiMapper;
    }


    @GetMapping("/users/{userId}/accounts/{accountId}")
    public ApiExtendedAccount getAccount(@PathVariable Long accountId) {
        return externalApiMapper.toExtendedAccountApi(accountService.getAccount(accountId));
    }


}
