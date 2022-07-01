package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;

public class WithDrawRequest extends DepositRequest {

    private double amount;

    public WithDrawRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
