package io.trabe.teaching.rest.controller.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccount;
import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.api.external.v2.ApiExtendedAccount;

@Mapper(componentModel = "spring")
public interface ExternalApiMapper {

    ApiAccount toAccountApi(Account account);

    List<ApiAccount> toAccountApiList(List<Account> accounts);

    @Mapping(target = "extendedInformation", constant = "Some extended information")
    ApiExtendedAccount toExtendedAccountApi(Account account);
}
