package com.microservice.budget.domain;

import com.microservice.budget.error.exception.InvalidAmountException;
import com.microservice.budget.error.exception.UnavailableAccountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test orientado a probar comportamiento
 * Lo minimo necesario para que le test pase :d
 */
public class TestAccount {
    /**
     * Dado que tengo una cuenta recien creada
     * cuando consulto el balance de la cuenta
     * Obtengo 0
     */
    @Test
    public void testAccountInitialBalanceZero() {
        Account account = new Account("cuenta", 0);
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
        Account account = new Account("", 20.0);
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
            new Account("", -10.0);
        });
    }

    /**
     * Dao que tengo una cuenta con un balance inicial de 15
     * retiro 100
     * la operacion debe arrojar un error
     */
    @Test()
    public void testWithDrawBalanceNegative() {
        Account account = new Account("", 15);

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
        Account account = new Account("", 20);
        try {
            account.withDraw(7);
            account.withDraw(7);
            account.withDraw(7); //Este ultimo dejara el saldo en negativo
            //Si llega a esta linea se muere el test
            fail(); //fuerzo el fallo ya que la 1,2,3 ejecuciones no manda la exception fuerzo a que el test se caiga.
        } catch (InvalidAmountException exception) {
            //Validamos el aserto en el segundo retiro
            // arroja la exception
            assertEquals(6.0, account.getBalance());
        }
    }

    /**
     * Dado que tengo una cuenta que inicia en 100 000 000
     * cuando hago un deposito que lo deja por encima del valor maximo soportado
     * debe arrojar error
     */
    @Test
    public void testDepositByTopLimit() {
        Account account = new Account("", 100000000.00);
        // spike cuanto seria lo que tengo que sumar
        // ME FALTA UN +1 PARA QUE CAUSE EL ERROR
        // EVITAR LLEGAR AL LIMITE
        double valorASumar = Double.MAX_VALUE - account.getBalance();

        assertThrows(InvalidAmountException.class, () -> {
            account.deposit(valorASumar + 1);
        });
    }

    /**
     * Dado que tengo una cuenta en estado BLOQUEADO
     * cuando intento un retiro
     * debe arrojar un error
     */
    @Test
    public void testWithDrawStatusInvalid() {
        Account account = new Account("", 10000000.00);
        account.setStatus(2);
        assertThrows(UnavailableAccountException.class, () -> {
            account.withDraw(1);
        });
    }
}