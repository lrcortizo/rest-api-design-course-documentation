package io.trabe.teaching.rest.model.accessor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jooq.lambda.Seq;
import org.springframework.stereotype.Component;

import io.trabe.teaching.rest.model.exception.NonExistentUserException;
import io.trabe.teaching.rest.model.exception.UserAlreadyExistsException;
import io.trabe.teaching.rest.model.pojo.backend.BackendUser;

@Component
public class UserAccessorImpl implements UserAccessor {

    private final ConcurrentHashMap<String, BackendUser> storage;
    private Long lastId = 1L;

    public UserAccessorImpl() {
        storage = new ConcurrentHashMap<>();
        createUser("marcos", "Marcos");
    }

    @Override
    public List<BackendUser> getAllUsers() {
        return Seq.seq(storage.elements()).toList();
    }

    @Override
    public BackendUser createUser(String login, String name) {
        BackendUser backendUser = BackendUser.builder()
                .id(getNextId())
                .login(login)
                .name(name)
                .build();
        if (storage.putIfAbsent(login, backendUser) != null) {
            throw new UserAlreadyExistsException();
        }
        return backendUser;
    }

    @Override
    public Optional<BackendUser> getUserByLogin(String login) {
        return storage.containsKey(login) ? Optional.of(storage.get(login)) : Optional.empty();
    }

    @Override
    public void deleteUser(String login) {
        storage.remove(login);
    }

    @Override
    public BackendUser updateUser(String login, String name) {
        Optional<BackendUser> backendUser = getUserByLogin(login);
        if (backendUser.isPresent()) {
            storage.put(login, BackendUser.builder()
                    .id(backendUser.get().getId())
                    .name(name)
                    .build());
        }
        throw new NonExistentUserException();
    }

    @Override
    public Optional<BackendUser> getUser(Long userId) {
        return Seq.seq(storage.values())
                .filter(backendUser -> userId.equals(backendUser.getId()))
                .findAny();
    }

    private synchronized Long getNextId() {
        return lastId++;
    }


}
