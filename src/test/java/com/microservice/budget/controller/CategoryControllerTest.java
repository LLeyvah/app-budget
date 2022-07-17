//package com.microservice.budget.controller;
///*
//Operaciones Expuestas por el API  (No CRUD  create read update delete)
//        *  Crear una categoria
//        *  Consultar una cuenta por nombre
//        ->  Listar Categorias
//        -   Modificar Categorias
//        -   Cerrar Categorias
//*/
//
//
//import org.dbunit.IDatabaseTester;
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.FileInputStream;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//public class CategoryControllerTest {
//    private IDatabaseTester databaseTester;
//
//    protected IDataSet getDataSet() throws Exception {
//        return new FlatXmlDataSetBuilder().build(new
//                FileInputStream("/Users/lionard.leyva/Documents" +
//                "/proyectos/app-budget/src/test/resources/ExpenseCategoryTestDataSet.xml"));
//    }
//
//    @BeforeEach
//    protected void setUp() throws Exception {
//
//        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
//                "jdbc:mysql://localhost:53699/db_budget", "root", "root");
//
//        // initialize your dataset here
//        IDataSet dataSet = getDataSet();
//        // ...
//
//        databaseTester.setDataSet(dataSet);
//        // will call default setUpOperation
//        databaseTester.onSetup();
//    }
//
//    @AfterEach
//    protected void tearDown() throws Exception {
//        // will call default tearDownOperation
//        databaseTester.onTearDown();
//    }
//}
