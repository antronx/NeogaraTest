package iTests.endpoints;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class POST_register_lid {
    RequestSpecification requestSpecification = RestAssured.given();


    @BeforeEach
    public  void setUp() {
        RestAssured.baseURI = "https://admin.neogara.com/register";
    }

    @Test
    public void registerLid(){
        POST_API_happy_path click = new POST_API_happy_path();
        JSONObject requestParams = new JSONObject();
        requestParams.put("pid", "lalw70");
        requestParams.put("ref", "file:///C:/Users/akaia/Documents/4%20new%20work/Appside/test.html");
        requestParams.put("click", click.activeID);

        requestSpecification.body(requestParams.toJSONString());
        Response response = requestSpecification.post("/lid");
        Assert.assertEquals(response.getStatusCode(), 201);

    }

}
