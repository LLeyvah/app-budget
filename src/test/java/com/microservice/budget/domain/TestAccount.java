package com.microservice.budget.domain;

import com.microservice.budget.Exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 1 crear una representacion de una cuenta
 * empieza con un balance 0
 * Puedo depositar dinero
 * puedo retirar dinero
 * no tiene limite maximo
 * no puede tener menos de 0
 * no puede tener un balance menor a 0
 */
public class TestAccount {
    /**
     * Dado que tengo una cuenta recien creada
     * cuando consulto el balance de la cuenta
     * Obtengo 0
     */
    @Test
    public void testAccountInitialBalanceZero() {
        Account account = new Account();
        Double balance = account.getBalance();
        assertEquals(0.0, balance);
    }

    /**
     * DADO que tengo una cuenta recien creada
     * cuando deposito 10
     * el balance es 10
     */

    @Test
    public void testFirstDeposit() {
        Account account = new Account();
        account.deposit(10.0);
        double balance = account.getBalance();
        assertEquals(10.0, balance);
    }

    /**
     * Dado que tengo una cuenta con 20 de saldo
     * retiro 14
     * el saldo debe ser 6
     */
    @Test
    public void testWithDraw() {
        Account account = new Account(20.0);
        account.withDraw(14.0);
        assertEquals(6, account.getBalance());
    }

    /**
     * Dado que tengo una cuenta que inicia en balance negativo
     * la aplicacion arroja una exception
     */
    @Test
    public void testInitialBalanceNegative() {
        assertThrows(InvalidAmountException.class, () -> {
            new Account(-10.0);
        });
    }

    /**
     * Dao que tengo una cuenta con un balance inicial de 15
     * retiro 100
     * la operacion debe arrojar un error
     */
    @Test()
    public void testWithDrawBalanceNegative() {
        Account account = new Account(15);

        assertThrows(InvalidAmountException.class, () -> {
            account.withDraw(100);
        });
    }

    /**
     * Dado que tengo una cuenta balance inicial 20
     * y ejecuto 3 retiros de 7
     * la operacion debe arrojar un error al dejar un balance negativo
     */
    @Test
    public void testMultipleWithDraw() {
        // Testeamos dos cosas 1: Saldo negativo 2: A que me "aserte" el segundo retiro wao:0
        Account account = new Account(20);
        try {
            account.withDraw(7);
            account.withDraw(7);
            account.withDraw(7); //Este ultimo dejara el saldo en negativo
            //Si pasa a esta linea se muere el test
            fail(); //fuerzo el fallo ya que la 1,2,3 ejecuciones no manda la exception fuerzo a que el test se caiga.
        } catch (InvalidAmountException exception) {
            //Validamos el aserto en el segundo retiro
            // arroja la exception
            assertEquals(6.0, account.getBalance());
        }
    }

}
