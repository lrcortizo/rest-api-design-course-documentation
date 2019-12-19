package io.trabe.teaching.rest.model.accessor;

import java.util.List;
import java.util.Optional;

import io.trabe.teaching.rest.model.pojo.backend.BackendUser;


public interface UserAccessor {

    List<BackendUser> getAllUsers();

    BackendUser createUser(String login, String name);

    Optional<BackendUser> getUserByLogin(String login);

    void deleteUser(String login);

    BackendUser updateUser(String login, String name);

    Optional<BackendUser> getUser(Long userId);
}
