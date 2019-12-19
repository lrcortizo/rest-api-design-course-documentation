package io.trabe.teaching.rest.model.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.client.ApiException;
import io.swagger.client.api.AccountApi;
import io.trabe.teaching.rest.model.accessor.publicapi.PublicapiAccessor;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.service.mapper.AccountMapper;

@Component
public class ConsumeApiServiceImpl implements ConsumeApiService {

    private final PublicapiAccessor publicapiAccessor;
    private final AccountApi accountApi;
    private final AccountMapper accountMapper;
    private static final Logger log = LoggerFactory.getLogger(ConsumeApiServiceImpl.class);

    public ConsumeApiServiceImpl(PublicapiAccessor publicapiAccessor, AccountApi accountApi,
            AccountMapper accountMapper) {
        this.publicapiAccessor = publicapiAccessor;
        this.accountApi = accountApi;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<Account> getAccountsByUserLogin(Long userId) {
        //return publicapiAccessor.getUserAccounts(userLogin);
        try {
            accountApi.getApiClient().setApiKey(
                    "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6InB1YmxpYzpjNDI0YjY3Yi1mZTI4LTQ1ZDctYjAxNS1mNzlkYTUwYjViMjEiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOltdLCJjbGllbnRfaWQiOiJhZG1pbiIsImV4cCI6MTU3Njc1NTEyOCwiZXh0Ijp7fSwiaWF0IjoxNTc2NzUxNTI4LCJpc3MiOiJodHRwOi8vMTI3LjAuMC4xOjQ0NDQvIiwianRpIjoiOGJiODNiMjktYjc2NC00YTkyLThhM2QtYTZhYTZkYjRkMzliIiwibmJmIjoxNTc2NzUxNTI4LCJzY3AiOltdLCJzdWIiOiJhZG1pbiJ9.d4-UcdGO6MH7F-ObRzC7nyCluQqErCFibFWWAqZkendSsk6cYoUuQY2Yw_vZqXmkf4hTyT5V8k-l1pBPWv-94B5mbs6xSeP4xGqliLRvBNZ3lkk5vd4yxe4ArOzgdTiXChEOg8wmyamWBZCeRXbSMLTTxCK1bww_lKsMA2rQsJuxksh_Wr6TOsUP3tuFfBlk6_6T-Lc7bLMXhiSiHbMdRYPJV6TnXKWtwcbTEN_eLaiiZGArpObLjkkM23dxwoKSOMUnJXPz8i739ApmCfDyIBJYApUkup1QarXLyLLZ9J8xz_9CcubStZxxndVlTccQywioHqMdYPoXyH84oDBakuE7megQzcXwk3rJ_kNqrXvsEji_BbcXjJooj3kNqSN2G_rp6M68id6N_835kbT7FKpON9znRp-xWXLkoAR1O34WjllC8-8fb65JS6wuq4VerdkeBO79wKBgk-n3Wz-ia0zymGG2MlFEnvpdiJ33-8jKHOmoiDKPAjaw0oz_Z9mQVTh-wfrbrx5EctQ0RnCwhN7KRIU0rMBnhNabiyZcaB8U3CNl3O_yefiKCLmsAfbEpHi5dkYHQI00OrauocOcl2m0GzjHEuazyWPud5K7OfnQjOvIpmx6t-KmqrFtgIZZUw06dWXP1Nh73pOmCPS_WfdflL_Yb0RlYVVc8Hf2NpQ");
            return accountMapper.convertFromApi(accountApi.getUserAccountsUsingGET1(userId));
        } catch (ApiException e) {
            log.error("Trouble consuming API");
        }
        // Example, in real life most likely an exception
        return Collections.emptyList();
    }

    @Override
    public Account createAccount(String userLogin, Account account) {
        return publicapiAccessor.createAccount(userLogin, account);
    }
}
