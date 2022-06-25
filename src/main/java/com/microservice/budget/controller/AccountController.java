package com.microservice.budget.controller;

import com.microservice.budget.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();


    @PostMapping(value = { "/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {
        accounts.add(account);
        return account;

    }
}
