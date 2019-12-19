package io.trabe.teaching.rest.model.service;

import java.util.List;
import java.util.Optional;

import io.trabe.teaching.rest.model.pojo.User;


public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserByLogin(String login);
    User createUser(String login, String name);
    void deleteUser(String login);
    User updateUser(String login, String name);


    Optional<User> getUser(Long userId);
}
