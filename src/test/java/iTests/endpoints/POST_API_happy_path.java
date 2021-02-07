package iTests.endpoints;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import io.qameta.allure.Description;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class POST_API_happy_path {
    public String activeID;
    RequestSpecification requestSpecification = RestAssured.given();


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://admin.neogara.com";
    }

    @Test
    @Description("Проверка получения ключа клика при запросе к 'https://admin.neogara.com/clicks' c валидными данными. Ожидается статус 200 и наличие id клика")
    public void testAclickRegister() throws IOException {
        String jsonBody = generateStringFromResource("src/test/java/iTests/jsons/post_register_click.json");

        activeID = given().
                contentType("application/json").
                body(jsonBody).
                when().
                post(baseURI + "/clicks").
                then().
                statusCode(200).
                body(containsString("id"))
                .extract().path("id");
        System.out.println(activeID);
    }

    @Test
    @Description("Проверка регистрации лида с валидными данными. Ожидаемый статус 200 и наличие lidId")
    public void testBlidRegister() {
        POST_API_happy_path click = new POST_API_happy_path();
        JSONObject requestParams = new JSONObject();
        requestParams.put("pid", "lalw70");
        requestParams.put("pipeline", 20);
        requestParams.put("firstname", "TestFirstName");
        requestParams.put("lastname", "TestLastName");
        requestParams.put("phone", "12345678");
        requestParams.put("email", "lid@gmail.com");
        requestParams.put("ref", "file:///C:/Users/akaia/Documents/4%20new%20work/Appside/test.html");
        requestParams.put("ip", "172.122.31.13");
        requestParams.put("city", "Kyiv");
        requestParams.put("country", "UA");
        requestParams.put("click", click.activeID);
        requestParams.put("sub1", "test");
        requestParams.put("sub2", "test");

        System.out.println(requestParams.toString());//


        int id = given().
                contentType("application/json").
                body(requestParams).
                when().
                post(baseURI + "/register/lid").
                then().
                statusCode(200).extract().path("lidId");
        System.out.println(id);

    }

    public String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }
}
