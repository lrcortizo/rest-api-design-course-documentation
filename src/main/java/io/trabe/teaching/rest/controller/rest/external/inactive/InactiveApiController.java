package io.trabe.teaching.rest.controller.rest.external.inactive;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InactiveApiController {

    @RequestMapping("/api/public/0/**")
    @ResponseStatus(HttpStatus.GONE)
    public void inactiveApi() {

    }
}
