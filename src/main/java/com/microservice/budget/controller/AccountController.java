package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;
import com.microservice.budget.domain.Account;
import com.microservice.budget.error.exception.InvalidCreateAccountRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();


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

    @PostMapping(value = {"/account/{accountName}/withdraw"})
    @ResponseStatus(HttpStatus.OK)
    public Account withDraw(@RequestBody DepositRequest request, @PathVariable("accountName") String name) {
        return null;
    }
}