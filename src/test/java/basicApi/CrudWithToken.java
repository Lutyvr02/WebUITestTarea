package basicApi;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CrudWithToken {
    @Test
    public void createUpdateReadDeleteProject() {
        JSONObject bodyProject = new JSONObject();
        bodyProject.put("Content", "LucianoJson");
        bodyProject.put("Icon", 2);

        Response tokenResponse = given()
                .auth()
                .preemptive()
                .basic("lutyvr02@gmail.com", "hola1234")
                .log()
                .all().
                when()
                .get("https://todo.ly/api/authentication/token.json");

        tokenResponse.then()
                .log()
                .all()
                .statusCode(200);

        // Extraer el token de la respuesta
        String authToken = tokenResponse.then().extract().path("TokenString");

        // create
        Response response = given()
                .header("Token", authToken) // Usar el token de autenticación
                .body(bodyProject.toString())
                .log()
                .all().
                when()
                .post("https://todo.ly/api/projects.json");


        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo(bodyProject.get("Content")))
                .body("Icon", equalTo(2));

        int idProject = response.then().extract().path("Id");
        // update
        bodyProject.put("Content", "JSON Update");
        response = given()
                .header("Token", authToken)  // Usar el token de autenticación
                .body(bodyProject.toString())
                .log()
                .all().
                when()
                .put("https://todo.ly/api/projects/"+idProject+".json");


        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo(bodyProject.get("Content")))
                .body("Icon", equalTo(2));

        // read
        response = given()
                .header("Token", authToken)  // Usar el token de autenticación
                .body(bodyProject.toString())
                .log()
                .all().
                when()
                .get("https://todo.ly/api/projects/"+idProject+".json");


        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo(bodyProject.get("Content")))
                .body("Icon", equalTo(2));


        // delete
        response = given()
                .header("Token", authToken)  // Usar el token de autenticación
                .body(bodyProject.toString())
                .log()
                .all().
                when()
                .delete("https://todo.ly/api/projects/"+idProject+".json");

        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo(bodyProject.get("Content")))
                .body("Deleted", equalTo(true))
                .body("Icon", equalTo(2));


    }
}
