package com.microservice.budget.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
1 crear una representacion de una cuenta
empieza con un balance 0
    Puedo depositar dinero
    puedo retirar dinero
    no tiene limite maximo
    no puede tener menos de 0
    no puede tener un balance menor a 0
 */
public class TestAccount {
    /*
      Dado que tengo una cuenta recien creada
      cuando consulto el balance de la cuenta
      Obtengo 0
     */
    @Test
    public void testAccountInitialBalanceZero(){
        Account account = new Account();
        Double balance = account.getBalance();
        assertEquals(0.0,balance);
    }
}
