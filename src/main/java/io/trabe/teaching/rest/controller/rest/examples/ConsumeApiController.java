package io.trabe.teaching.rest.controller.rest.examples;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.service.ConsumeApiService;

@RestController
@RequestMapping("/api/examples/1/consume-accounts/users/{userId}/accounts")
public class ConsumeApiController {

    private final ConsumeApiService consumeApiService;


    public ConsumeApiController(ConsumeApiService consumeApiService) {
        this.consumeApiService = consumeApiService;
    }

    @GetMapping
    public List<Account> userAccounts(@PathVariable Long userId) {
        return consumeApiService.getAccountsByUserLogin(userId);
    }
}
