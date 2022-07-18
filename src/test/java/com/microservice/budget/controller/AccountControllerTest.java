package com.microservice.budget.controller;

import com.microservice.budget.controller.request.DepositRequest;
import com.microservice.budget.domain.Account;
import org.apache.catalina.core.ApplicationContext;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.FileInputStream;

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
 * <p>
 * ACID TEST:TRANSACCIONES
 * ATOMICO: SUCEDER O NO SUCEDER TX: RETIRAS DEPOSITAS Y HACES EL COMMIT Y SI HAY UNA EXCEPTION HACES LA REVERSION
 * CONSISTENCIA: DATO SIEMPRE CONSISTENTE UN DETALLE SIN MAESTRO
 * AISLAMIENTO: CONCURRENCIA - DOS PERSO DEPOSITAR AL MISMO TIEMPO: LA EJECUCION DE ESTAS TX NO DEBEN VERSE AFECTADAS
 * O RETIRAR DOS VECES DE LA CUENTA.
 * <p>
 * SOLUCION: BLOQUEANDO TABLAS BLOQUEO DE LA ESCRITURA PERMITIENDO LECTURA
 * DURABILIDAD:
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountControllerTest {
    //configuramos la conexion a la bd
    private IDatabaseTester databaseTester;

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new
                FileInputStream("/Users/lionard.leyva/Documents" +
                "/proyectos/app-budget/src/test/resources/AccountTest.xml"));
    }

    @BeforeEach
    protected void setUp() throws Exception {

        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:53699/db_budget", "root", "root");

        // initialize your dataset here
        IDataSet dataSet = getDataSet();
        // ...

        databaseTester.setDataSet(dataSet);
        // will call default setUpOperation
        databaseTester.onSetup();
    }

    @AfterEach
    protected void tearDown() throws Exception {
        // will call default tearDownOperation
        databaseTester.onTearDown();
    }

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
                .uri("/account/{accountName}", "Cuenta1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta1");


    }

    /**
     * Dado que tengo una cuenta llamada "ahorro" con balance de 20
     * cuando deposito 5
     * la cuenta tiene 25
     */
    @Test
    public void depositAccount(@Autowired WebTestClient client) {
        DepositRequest request = new DepositRequest(10.0);
        client.post()
                .uri("/account/{accountName}/deposit", "Cuenta3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta3")
                .jsonPath("$.balance").isEqualTo(20);
    }

    /**
     * Dado que tengo una cuenta llamada Ahorros
     * cuando la elimino
     * debe devolver la cuenta con estado cerrado =0
     * Estados
     * 0 cerrada
     * 1 abierta
     * 2 bloqueada
     */
    @Test
    public void closeAccount(@Autowired WebTestClient client) {
        client.post()
                .uri("/account/{account}/close", "Cuenta3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta3")
                .jsonPath("$.status").isEqualTo(0);
    }

    /**
     * Dado que tengo una cuenta1 con 100 y cuenta2 otra con 80
     * cuando transfiero de la cuenta1 50 a cuenta2
     * El servicio retorna correcto y el estado de cuenta1
     */
    @Test
    public void transferAccountOk(@Autowired WebTestClient client) {
        AccountTransferRequest accountTransferRequest =
                new AccountTransferRequest("Cuenta2", 10.0);
        client.post()
                .uri("/account/{accountOrigin}/transfer", "Cuenta1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(accountTransferRequest))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Cuenta1")
                .jsonPath("$.balance").isEqualTo(80);
    }

    /**
     * Dado que tengo una una cuenta3 con 10 y cuenta2 con 80
     * cuando transfiero de la cuenta3 50 a cuenta2
     * el servicio retorna ERROR 500
     *
     * @param client
     */
    @Test
    public void transferAccountFromAccountOriginInsufficientFond(@Autowired WebTestClient client) {
        AccountTransferRequest request = new AccountTransferRequest("Cuenta2", 50.00);
        client.post()
                .uri("/account/{nameOrigin}/transfer", "Cuenta3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

    }

    /**
     * Da que tengo una cuenta origenbloqueada con 100 y cuent2 con 80
     * cuando transfiero a de la ciuentaorigenbloquead 50 a cuenta
     * el servicio retorna codigo error
     * @param client
     */
    @Test
    public void transferAccountFromAccountOriginBlocked(@Autowired WebTestClient client) {
        AccountTransferRequest request = new AccountTransferRequest("CuentaOrigenBloqueada", 50.00);
        client.post()
                .uri("/account/{nameOrigin}/transfer", "Cuenta3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();
    }
    /**
     * Da que tengo una cuenta ahorros con 20 y CuentaDestinoBloqueada con 100
     * cuando transfiero a de la cuenta de ahorros 50 a   CuentaDestinoBloqueada
     * el servicio retorna codigo error
     * @param client
     */
    @Test
    public void transferAccountFromAccountTargetBlocked(@Autowired WebTestClient client) {
        AccountTransferRequest request = new AccountTransferRequest("CuentaOrigenBloqueada", 50.00);
        client.post()
                .uri("/account/{nameOrigin}/transfer", "Cuenta3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();
    }
}