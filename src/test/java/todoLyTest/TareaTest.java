package todoLyTest;

import config.Configuration;
import factoryRequest.FactoryRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class TareaTest extends TestBase{

    @Test
    public void CreateUser() {
        JSONObject body = new JSONObject();
        body.put("Email","tyty123456789@tyty123456789.com");
        body.put("Password","hola1234");
        body.put("FullName","hola1234@hola1234.com");

        this.CreateUserTy(Configuration.host + "/api/user.json", body, post);
        body.put("Content","Refactor");

        this.createItem(Configuration.host + "/api/items.json", body, post);
        int idProject = response.then().extract().path("Id");
    }

    private void CreateUserTy(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200);

    }

    private void createItem(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200);
    }
}
