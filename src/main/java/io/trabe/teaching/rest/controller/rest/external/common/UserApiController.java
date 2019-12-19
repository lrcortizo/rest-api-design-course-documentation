package io.trabe.teaching.rest.controller.rest.external.common;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.trabe.teaching.rest.model.pojo.User;
import io.trabe.teaching.rest.model.service.UserService;

@RestController
@RequestMapping(value = "/api/1/users", produces = "application/json")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(params = "login")
    public User getUser(@RequestParam String login) {
        return userService.getUserByLogin(login).get();
    }


}
