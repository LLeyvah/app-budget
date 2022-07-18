package com.microservice.budget.service.impl;

import com.microservice.budget.domain.Account;
import com.microservice.budget.repository.AccountRepository;
import com.microservice.budget.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account transfer(String accountName, String accountTargetName, Double amountTarget) {
        Account accountOrigin = repository.findByName(accountName);
        Account accountTarget = repository.findByName(accountTargetName);
        //Descontamos al origin el monto que estamos indicando en el body
        accountOrigin.withDraw(amountTarget);
        accountTarget.deposit(amountTarget);
        repository.save(accountTarget);
        Account updatedAccountOrigin = repository.save(accountOrigin);
        return updatedAccountOrigin;
    }
}
