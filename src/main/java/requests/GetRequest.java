package requests;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.testng.Assert;
import entities.ObjectFromResponse;
import util.BaseSteps;
import util.HttpRequest;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class GetRequest extends BaseSteps {

    private List<ObjectFromResponse> objectsFromResponse;

    public void request(String url){
        try {
            HttpRequest httpRequest = new HttpRequest(200);
            httpRequest.request("GET",url);

            String responseBody = httpRequest.getResponseBody();
            JsonArray responseJO = getJsonArray(responseBody);

            String responseData = responseJO.toString();
            Type ObjectFromResponseType = new TypeToken<Collection<ObjectFromResponse>>() {}.getType();
            objectsFromResponse = gson.fromJson(responseData,ObjectFromResponseType);

        } catch (Exception e){logger.info("<p style='color:Red'>GET Request: " + e + "</p>"); state = "exception";}
    }

    public void checkResponse(){
        for (ObjectFromResponse object: objectsFromResponse){
            try {
                Assert.assertTrue(object.getId() != 0,"id");
                Assert.assertNotNull(object.getName(), "name");
            } catch (AssertionError e){
                logger.info("<br>" + e);
            } catch (Exception e){logger.info("<p style='color:Red'>Check Response: " + e + "</p>"); state = "exception";}
        }
    }
}