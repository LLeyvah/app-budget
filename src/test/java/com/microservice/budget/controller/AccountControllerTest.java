package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;
import com.microservice.budget.domain.Account;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Operaciones expuestas por el API
 * -> Crear una cuenta
 * Ejecutar un deposito a la cuenta
 * Ejecutar un retiro a la cuenta
 * Consultar el saldo de la cuenta
 * Cerrar la cuenta
 */

/**
 * SpringBootTest Levanta todo la aplicacion y busca stereotipo
 * levanta un controlador : encuentra al servicio y este busca su implementacion
 * anotandose con el @service .
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountControllerTest {


    public void setUp(ApplicationContext applicationContext) {
    }

    /**
     * Dado que no tengo una cuenta
     * Invoco la creacion de una cuenta sin nombre
     * Debe arrojar un status Bad Request
     */

    @Test
    public void createAccountIncompleteAttributes(@Autowired WebTestClient client) {
        Account account = new Account();
        client.post()
                .uri("/account")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus()
                .isBadRequest();


    }

    /**
     * Dado que no tengo una cuenta
     * Invoco la creacion de una cuenta nombre: Cuenta 01 balance 0
     * Debe devolverme la cuenta creada con status creado
     */

    @Test
    public void createAccount(@Autowired WebTestClient client) {
        Account account = new Account("Cuenta 01", 0.00);
        client.post()
                .uri("/account")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.balance")
                .isEqualTo(0.00);
    }

    /**
     * Dado que tengo una cuenta llamada Ahorros con balance 20
     * cuando retiro 5
     * la cuenta debe tener 15
     * POST: NO EDITAMOS UNA CUENTA
     * NO SIEMPRE SELECCIONAMOS UNA CUENTA O ACTUALIZANDO TODO OBJ
     * ES UN RETIRO PASANDO SOLO EL ID DE LA CUENTA NO NECESITAMOS TODO LA ENTIDAD CUENTA
     */
    @Test
    public void withDraw(@Autowired WebTestClient client) {
        DepositRequest request = new WithDrawRequest(5.0);
        client.post()
                .uri("/account/{accountName}/withdraw", "ahorros")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isOk();

    }

    /**
     * Dado que tengo una cuenta llamada "Ahorros"
     * cuando consulto la cuenta por nombre
     * me devuelve la cuenta solicitada
     *
     * @param client
     */
    @Test
    public void getAccountByName(@Autowired WebTestClient client) {
        client.get()
                .uri("/account/{accountName}", "AHORROS_LEO")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("AHORROS_LEO");


    }
}