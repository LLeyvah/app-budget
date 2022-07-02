package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;
import com.microservice.budget.domain.Account;
import com.microservice.budget.error.exception.InvalidCreateAccountRequestException;
import com.microservice.budget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();
    @Autowired
    private AccountService service;


    @PostMapping(value = {"/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {
        boolean validRequest = true;
        validRequest &= account.getName() != null;
        if (validRequest) {
            accounts.add(account);
        } else {
            throw new InvalidCreateAccountRequestException(account);
        }
        return account;
    }

    @GetMapping(value = {"/account/{accountName}"})
    @ResponseStatus(HttpStatus.OK)
    public Account getStatus(@PathVariable("accountName") String name) {
        Account account = new Account(name, 20);
        return account;
    }

    @PostMapping(value = {"/account/{accountName}/withdraw"})
    @ResponseStatus(HttpStatus.OK)
    public Account withDraw(@RequestBody DepositRequest request, @PathVariable("accountName") String name) {
        return null;
    }
}