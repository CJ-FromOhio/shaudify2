package com.hezix.shaudifymain.integration.controller;

import com.hezix.shaudifymain.BaseIntegrationTest;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest extends BaseIntegrationTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        userRepository.deleteAll();

        List<User> users = List.of(
                User.builder()
                        .username("egor1")
                        .password("12341234")
                        .firstName("egor")
                        .lastName("daniel")
                        .email("egor@gmail.com")
                        .build(),
                User.builder()
                        .username("nikita1")
                        .password("12341234")
                        .firstName("nikita")
                        .lastName("daniel")
                        .email("nikita@gmail.com")
                        .build()
        );
        userRepository.saveAll(users);
    }

    @Test
    void shouldGetAllUsers() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/users/")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
        Response response = RestAssured.given().get("/api/v1/users/");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/users/"))
                .build();
        HttpResponse<String> response1;
        try {
            response1 = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body().toString());
        System.out.println(response1.body());
    }
}
