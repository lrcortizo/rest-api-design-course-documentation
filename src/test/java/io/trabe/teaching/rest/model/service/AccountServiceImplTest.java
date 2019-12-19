package io.trabe.teaching.rest.model.service;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.trabe.teaching.rest.model.accessor.AccountAccessor;
import io.trabe.teaching.rest.model.accessor.PaymentMethodAccessor;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.AccountKind;
import io.trabe.teaching.rest.model.pojo.backend.BackendAccount;
import io.trabe.teaching.rest.model.accessor.OperationAccessor;
import io.trabe.teaching.rest.model.service.mapper.AccountMapper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AccountServiceImplTest {

    private static final String TEST_LOGIN = "test.login";
    private static final String DESCRIPTION = "Some desctiption";
    private static final String CODE = "code";
    private static final double BALANCE = 3000.00;
    private static final Long BACKEND_ID = 135L;
    @Mock
    private AccountAccessor accountAccessor;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private AccountMapper accountMapper;

    @Mock
    private OperationAccessor operationAccessor;
    @Mock
    private PaymentMethodAccessor paymentMethodAccessor;

    @Mock
    private OperationConverter operationConverter;
    @Mock
    private PaymentMethodConverter paymentMethodConverter;

    AccountServiceImpl accountServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(AccountServiceImpl.class);
        accountServiceImpl = new AccountServiceImpl(accountAccessor, accountConverter, operationAccessor,
                paymentMethodAccessor, operationConverter, paymentMethodConverter, accountMapper);
    }

    @Test
    public void testAccountCreation() {
        BackendAccount mockedAccount = BackendAccount.builder()
                .id(BACKEND_ID)
                .accountKind(AccountKind.CHECKING)
                .creationDate(Calendar.getInstance())
                .login(TEST_LOGIN)
                .description(DESCRIPTION)
                .balance(BALANCE)
                .code(CODE)
                .build();
        Mockito.when(accountAccessor.createAccount(TEST_LOGIN, DESCRIPTION, CODE, BALANCE, AccountKind.CHECKING))
                .thenReturn(mockedAccount);

        Account account = accountServiceImpl.createAccount(TEST_LOGIN, DESCRIPTION, CODE, BALANCE,
                AccountKind.CHECKING);


        assertThat(account).isEqualToComparingFieldByField(accountConverter.convert().apply(mockedAccount));

    }

}