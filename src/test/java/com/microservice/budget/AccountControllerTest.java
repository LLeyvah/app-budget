package com.microservice.budget;

import com.microservice.budget.controller.AccountController;
import com.microservice.budget.domain.Account;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountControllerTest {


    public void setUp(ApplicationContext applicationContext){}
    /**
     * Dado que no tengo una cuenta
     * Invoco la creacion de una cuenta
     * Dbe tener una cuenta con balance de 0
     */

    @Test
    public void createAccount(@Autowired WebTestClient client){
        Account account = new Account();

        client.post()
                .uri("/account")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(account))
                .exchange()
                .expectStatus()
                .isOk();


    }
}
