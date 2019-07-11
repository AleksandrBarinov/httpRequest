package test;

import org.testng.annotations.Test;
import requests.GetRequest;
import requests.PutRequest;
import util.BaseSteps;

public class TestRequest extends BaseSteps {

    @Test
    public void test() {
        htmlBegin();

        try {
            setUrl("http://api.url");
            GetRequest getRequest = new GetRequest();
            PutRequest putRequest = new PutRequest();

            putRequest.request(""+getUrl()+"/api/name");
            getRequest.request(""+getUrl()+"/api/name");
            getRequest.checkResponse();

        } catch (Exception e){logger.info("<p style='color:Red'>TestRequest: " + e + "</p>"); state = "exception";}

        htmlEnd();
        checkExceptions(state);
    }
}