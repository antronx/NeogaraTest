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

public class POST_Click_Mandatory_Fields {



        public String activeID;
        RequestSpecification requestSpecification = RestAssured.given();


        @BeforeEach
        public void setUp() {
            RestAssured.baseURI = "https://admin.neogara.com";
        }

        @Test
        @Description("Проверка получения ключа клика при запросе к 'https://admin.neogara.com/clicks' c валидными данными. Ожидается статус 200 и наличие id клика")
        public void testAclickRegister() throws IOException {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("pid", "lalw70");
            jsonBody.put("ref", "https://admin.neogara.com/script/instruction.html");


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




}
