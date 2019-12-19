package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Service;

import io.trabe.teaching.rest.model.accessor.AccountAccessor;
import io.trabe.teaching.rest.model.accessor.UserAccessor;
import io.trabe.teaching.rest.model.pojo.User;
import io.trabe.teaching.rest.model.pojo.backend.BackendUser;
import io.trabe.teaching.rest.model.service.mapper.AccountMapper;
import io.trabe.teaching.rest.model.service.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService {

    private final UserAccessor userAccessor;
    private final AccountAccessor accountAccessor;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    public UserServiceImpl(UserAccessor userAccessor,
            AccountAccessor accountAccessor,
            UserMapper userMapper, AccountMapper accountMapper) {
        this.userAccessor = userAccessor;
        this.accountAccessor = accountAccessor;
        this.userMapper = userMapper;
        this.accountMapper = accountMapper;
    }


    @Override
    public List<User> getAllUsers() {

        return Seq.seq(userAccessor.getAllUsers())
                .map(convert())
                .toList();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {

        return userAccessor.getUserByLogin(login)
                .map(convert());
    }

    @Override
    public User createUser(String login, String name) {
        return convert().apply(userAccessor.createUser(login, name));
    }

    @Override
    public void deleteUser(String login) {
        userAccessor.deleteUser(login);
    }

    @Override
    public User updateUser(String login, String name) {
        return convert().apply(userAccessor.updateUser(login, name));
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return userAccessor.getUser(userId)
                .map(convert());
    }

    private Function<BackendUser, User> convert() {
        return backendUser -> userMapper
                .convert(backendUser, accountMapper.convert(accountAccessor.getUserAccounts(backendUser.getLogin())));
    }

}
