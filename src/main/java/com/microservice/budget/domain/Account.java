package com.microservice.budget.domain;

import com.microservice.budget.Exception.InvalidAmountException;

public class Account {

    private double balance = 0.0;

    public Account(double initialAmount) {
        if (initialAmount < 0.000000000000000000000) {
            throw new InvalidAmountException();
        }
        this.balance = initialAmount;
    }

    public Account() {

    }

    public Double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        /** Validamos que el monto ingresado sea mayor o igual al tope que
         * cualquier persona pueda abonar a su cuenta
         * recuerda DOUBLE_MAX_VALUE es el tope
         * la diferencia entre numero maximo y lo que tenemos
         *
         */

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
}
