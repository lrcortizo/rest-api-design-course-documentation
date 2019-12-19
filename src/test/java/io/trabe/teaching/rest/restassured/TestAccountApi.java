package io.trabe.teaching.rest.restassured;

import java.util.Calendar;

import org.junit.Test;

import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccount;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccountCreationRequest;
import io.trabe.teaching.rest.model.pojo.api.external.common.ApiAccountKind;

import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class TestAccountApi {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSchema() {
        when().get("http://localhost:8080/api/public/1/users/{login}/accounts","marcos").then().
                assertThat().
                body(matchesJsonSchemaInClasspath("accounts-schema.json"));
    }

    @Test
    public void testGetAccounts() {
        when().
                get("http://localhost:8080/api/public/1/users/marcos/accounts").
                then()
                .statusCode(200)
                .body("code", hasItems("ES43INGB2596749386", "CH3489144371634777488"))
                .body("id", hasItems(1, 2));
    }

    @Test
    public void testPostAccount() {
        ApiAccount response = given().
                contentType("application/json").
                accept("application/json").body(
                ApiAccountCreationRequest.builder()
                        .balance(10000.50)
                        .creationDate(Calendar.getInstance())
                        .code("TEST-ACCOUNT")
                        .description("Test")
                        .kind(ApiAccountKind.SAVINGS)
                        .build()).
                post("http://localhost:8080/api/public/1/users/1/accounts").
                then().
                statusCode(200)
                .body("balance", equalTo(10000.50f))
                .body("description", equalTo("Test"))
                .body("code", equalTo("TEST-ACCOUNT"))
                .extract()
                .as(ApiAccount.class);

        // Delete created account and check status

        when().delete("http://localhost:8080/api/public/1/users/1/accounts/" + response.getId())
                .then().statusCode(200);


    }

}
