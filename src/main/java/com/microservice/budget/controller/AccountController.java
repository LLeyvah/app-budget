package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;
import com.microservice.budget.domain.Account;
import com.microservice.budget.error.exception.InvalidCreateAccountRequestException;
import com.microservice.budget.repository.AccountRepository;
import com.microservice.budget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Component: Service, Repository, Controller
 */

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();
    // Spring scanea y busca la implemetacion  y a la ves el stereatipo @Service
    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository accountRepository;


    @PostMapping(value = {"/account"})
    @ResponseStatus(HttpStatus.OK)
    public Account create(@RequestBody Account account) {
        boolean validRequest = true;
        Account savedAccount;
        validRequest &= account.getName() != null;
        if (validRequest) {
            savedAccount = accountRepository.save(account);
        } else {
            throw new InvalidCreateAccountRequestException(account);
        }
        return savedAccount;
    }

    @GetMapping(value = {"/account/{accountName}"})
    @ResponseStatus(HttpStatus.OK)
    public Account getStatus(@PathVariable("accountName") String name) {

        return accountRepository.findByName(name);

    }

    @PostMapping(value = {"/account/{accountName}/withdraw"})
    @ResponseStatus(HttpStatus.OK)
    public Account withDraw(@RequestBody DepositRequest request, @PathVariable("accountName") String name) {
        Account account = new Account(name, 15);
        return account;
    }

    @PostMapping(value = {"/account/{accountName}/deposit"})
    @ResponseStatus(HttpStatus.OK)
    public Account deposit(@RequestBody DepositRequest request, @PathVariable("accountName") String name) {
        Account accountToDeposit = accountRepository.findByName(name);
        accountToDeposit.deposit(request.getAmount());
        Account savedAccount = accountRepository.save(accountToDeposit);
        return savedAccount;
    }

    @PostMapping(value = {"/account/{name}/close"})
    @ResponseStatus(HttpStatus.OK)
    public Account close(@PathVariable("name") String name) {
        Account account = accountRepository.findByName(name);
        account.setStatus(0);
        return accountRepository.save(account);
    }

}