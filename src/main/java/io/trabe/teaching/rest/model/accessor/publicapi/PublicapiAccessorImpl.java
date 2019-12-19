package io.trabe.teaching.rest.model.accessor.publicapi;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.trabe.teaching.rest.model.accessor.publicapi.mapper.PublicApiMapper;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccount;

@Component
public class PublicapiAccessorImpl implements PublicapiAccessor {

    private static final String ACCOUNTS_URL = "http://localhost:8080/api/public/1/users/{userId}/accounts";
    private final RestTemplate restTemplate;
    private final PublicApiMapper publicApiMapper;

    private static final Logger log = LoggerFactory.getLogger(PublicapiAccessorImpl.class);

    public PublicapiAccessorImpl(RestTemplate restTemplate,
            PublicApiMapper publicApiMapper) {
        this.restTemplate = restTemplate;
        this.publicApiMapper = publicApiMapper;
    }

    @Override
    public List<Account> getUserAccounts(String userLogin) {
        List<ApiAccount> apiAccounts = Arrays.asList(restTemplate
                .getForObject(UriComponentsBuilder.fromHttpUrl(ACCOUNTS_URL).buildAndExpand(userLogin).toUri(),
                        ApiAccount[].class));
        return publicApiMapper.toAccounts(apiAccounts);
    }

    @Override
    public Account createAccount(String userLogin, Account account) {

        ApiAccount apiAccount = restTemplate.postForObject(UriComponentsBuilder.fromHttpUrl(ACCOUNTS_URL)
                        .buildAndExpand(userLogin).toUri(),
                publicApiMapper.toApiAccountCreationRequest(account),
                ApiAccount.class);
        return publicApiMapper.toAccount(apiAccount);

    }

    @Override
    public Account getAccount(Long id) {
        return null;
    }
}
