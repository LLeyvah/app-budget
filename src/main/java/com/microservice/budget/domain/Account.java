package com.microservice.budget.domain;

import com.microservice.budget.Exception.InvalidAmountException;

public class Account {

    private double balance = 0.0;
    public Account(){

    }
    public Account(double initialAmount) {
        if(initialAmount<0.000000000000000000000) {
          throw new InvalidAmountException();
        }
        this.balance = initialAmount;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withDraw(double amount) {
        if(amount>this.balance){
            throw new InvalidAmountException();
        }
        this.balance -=amount;
    }
}
