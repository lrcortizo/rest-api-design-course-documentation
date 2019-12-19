package io.trabe.teaching.rest.model.service;

import java.util.List;

public interface AuthorizationService {


    List<Long> getAuthorizedUsersForLogin(String login);
}
