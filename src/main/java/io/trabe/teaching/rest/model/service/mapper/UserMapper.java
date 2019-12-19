package io.trabe.teaching.rest.model.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import io.trabe.teaching.rest.model.pojo.Account;
import io.trabe.teaching.rest.model.pojo.User;
import io.trabe.teaching.rest.model.pojo.backend.BackendUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convert(BackendUser backendUser, List<Account> accounts);
}
