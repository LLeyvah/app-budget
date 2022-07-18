package com.microservice.budget.controller;

public class AccountTransferRequest {

    private String accountTargetName;
    private double amount;

    public AccountTransferRequest(String accountTargetName, double amount) {
        this.accountTargetName=accountTargetName;
        this.amount = amount;

    }

    public String getAccountTargetName() {
        return accountTargetName;
    }

    public void setAccountTargetName(String accountTargetName) {
        this.accountTargetName = accountTargetName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
