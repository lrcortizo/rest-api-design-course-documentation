package io.trabe.teaching.rest.model.accessor.publicapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccount;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccountCreationRequest;


@Mapper(componentModel = "spring")
public interface PublicApiMapper {

    Account toAccount(ApiAccount apiAccount);

    ApiAccountCreationRequest toApiAccountCreationRequest(Account account);

    List<Account> toAccounts(List<ApiAccount> apiAccounts);

}
