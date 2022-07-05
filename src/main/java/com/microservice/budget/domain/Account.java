package com.microservice.budget.domain;

import com.microservice.budget.error.exception.InvalidAmountException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * docker
 * 49154: maq host internamente todo su golpe redirecciona al 3306
 * 49155: maq host internamente todo su golpe redirecciona al 3306
 * img no cambia su puerto sino la maq cambia su puerto
 * la imagen no cambia su puerto sino
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double balance = 0.0;
    private String name;
    private int status;


    public Account() {
    }

    public Account(String name, double initialAmount) {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private Long getId() {
        return id;
    }

    @Id
    private void setId(Long id) {
        this.id = id;
    }
}