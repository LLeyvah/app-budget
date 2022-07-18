package com.microservice.budget.controller;

public class AccountTransferRequest {

    private String accountTargetName;
    private Double amountTarget;

    public AccountTransferRequest(String accountTargetName, double amountTarget) {
        this.accountTargetName = accountTargetName;
        this.amountTarget = amountTarget;
    }

    public String getAccountTargetName() {
        return accountTargetName;
    }

    public void setAccountTargetName(String accountTargetName) {
        this.accountTargetName = accountTargetName;
    }

    public Double getAmountTranfer() {
        return amountTarget;
    }

    public void setAmountTarget(Double amountTarget) {
        this.amountTarget = amountTarget;
    }
}
