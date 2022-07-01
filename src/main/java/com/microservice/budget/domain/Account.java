package com.microservice.budget.domain;

import com.microservice.budget.error.exception.InvalidAmountException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private double balance = 0.0;
    private String name;
    private int status;

    public Account() {
    }

    public Account(String name,double initialAmount) {
        if (initialAmount < 0.000000000000000000000) {
            throw new InvalidAmountException();
        }
        this.name = name;
        this.balance = initialAmount;
        this.status = 1;
    }

    public void deposit(double amount) {
        if (amount >= Double.MAX_VALUE - getBalance()) {
            throw new InvalidAmountException();
        }
        this.balance += amount;
    }

    public void withDraw(double amount) {
        if (amount > this.balance) {
            throw new InvalidAmountException();
        }
        this.balance -= amount;
    }
    public String getName() {
        return name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}