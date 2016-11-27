package com.esgi.controllers;

import com.esgi.exceptions.BadTokenException;
import com.esgi.repositories.datasets.AccountDataset;
import org.junit.Test;
import org.slf4j.Logger;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.ACCEPTED;

/**
 * Created by valentin on 26/11/2016.
 */
@AccountDataset
public class TokenControllerBigTest {

    protected static final Logger LOGGER = getLogger(PostItControllerBigTest.class);

    @Test
    public void should_accept_token() {
        String token = "1234567";
        LOGGER.info("token is ::: "+token);
        given()
                .log().all()
                .contentType(JSON)
                .queryParam("tokenValue", "1234567")
                .when()
                .post("/token/check")
                .then()
                .log().all()
                .body("uuid", is("ed05768b-068c-4f9a-aaed-3c0c441972f1"))
                .statusCode(ACCEPTED.value());
    }

    @Test
    public void should_refuse_token_because_is_expired() {
        String token = "ABCDEFGHL";
        LOGGER.info("token is ::: "+token);
        given()
                .log().all()
                .contentType(JSON)
                .queryParam("token", token)
                .when()
                .post("/token/check")
                .then()
                .log().all()
                .body("exception", is(BadTokenException.class.getName()));
    }

    @Test
    public void should_refuse_token_because_is_not_existing() {
        String token = "JKNKLDNLKDKLDKL?LKD?LKD?LD?LK?DL?DLK?LKD?LD?LKD?LKD?LD?";
        LOGGER.info("token is ::: "+token);
        given()
                .log().all()
                .contentType(JSON)
                .queryParam("token", token)
                .when()
                .post("/token/check")
                .then()
                .log().all()
                .body("exception", is(BadTokenException.class.getName()));
    }

}