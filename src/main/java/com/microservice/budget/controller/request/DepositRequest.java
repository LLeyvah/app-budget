package com.microservice.budget.controller.request;

public class DepositRequest {
    private double amount;

    public DepositRequest() {
    }

    public DepositRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
