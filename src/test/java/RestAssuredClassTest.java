import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;


public class RestAssuredClassTest {
    @Test(groups = "GET")
    public static void getResponseBody(){

        given().when().get("https://reqres.in/api/users/2").then() .assertThat() .statusCode(200). log().body();
    }

    @Test(groups = "POST")
    public static void validatePost(){
        JSONObject request = new JSONObject();
        request.put("name","torque");
        request.put("job","student");
        System.out.println(request);
        given()
                .body(request.toJSONString()).when().post("https://reqres.in/api/users").then().statusCode(201).log().body();
    }

    @Test(groups = "PUT")
    public static void validatePUT(){
        JSONObject request = new JSONObject();
        request.put("name","torque");
        request.put("job","Software Engineer");
        System.out.println(request);
        given()
                .body(request.toJSONString()).when().put("https://reqres.in/api/users/2").then().statusCode(200).log().body();
    }

    @Test(groups = "POJO Post")
    public static void ValidatePOJOPost(){
        postTestPOJOClass data = new postTestPOJOClass("torque123", "student");
        System.out.println(data.getName());
        given().body(data.toString()).when().post("https://reqres.in/api/users").then().statusCode(201).log().all();
    }

    @Test(groups = "POJO Post")
    public static void ProcessResponsePOJOPost(){
        postTestPOJOClass data = new postTestPOJOClass("torque12", "student");
        Response res = given().body(data.toString()).when().post("https://reqres.in/api/users");

        //System.out.println(data.getName());
        ResponseBody body = res.getBody();
        System.out.println(body.prettyPrint());

        var responsePOJO = body.as(ProcessPostResponsePOJO.class);
        System.out.println(responsePOJO.id);


    }

    @Test(groups = "ASSERT")
    public static void ProcessAssert(){
        Response res = given().when().get("https://reqres.in/api/users?page=2");
        JsonPath j = res.jsonPath();
        int page = j.get("page");
        assertThat(page, equalTo(2));
        System.out.println(page);
    }


}
