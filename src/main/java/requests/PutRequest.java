package requests;

import entities.RequestBody;
import util.BaseSteps;
import util.HttpRequest;

public class PutRequest extends BaseSteps {

    public void request(String url) {
        try {
            RequestBody body = new RequestBody();

                body.setId(Integer.valueOf(properties.getProperty("id")));
                body.setName(properties.getProperty("name"));

            String jsonData = gson.toJson(body);

            HttpRequest httpRequest = new HttpRequest(204);
            httpRequest.request("PUT", jsonData, url);

        } catch (Exception e) {
            logger.info("<p style='color:Red'>PUT Request: " + e + "</p>");
            state = "exception";
        }
    }
}