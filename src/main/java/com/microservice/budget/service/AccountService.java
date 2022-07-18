package com.microservice.budget.service;

import com.microservice.budget.domain.Account;

public interface AccountService {
    Account transfer(String accountName, String accountTargetName, Double amountTarget);
}
