package util;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpRequest extends BaseSteps {

    private int expectedResponseCode;
    private String responseBody;
    private String authorization = properties.getProperty("Authorization");

    public HttpRequest(int expectedResponseCode){
        this.expectedResponseCode = expectedResponseCode;
    }

    public HttpRequest(String authorization,int expectedResponseCode){
        this.authorization = authorization;
        this.expectedResponseCode = expectedResponseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void request(String method, String url){
        request(method,"", url);
    }

    public void request(String method, String jsonData, String url) {
        Date start;
        Date end;
        long execTime;
        int statusCode;
        StringEntity bodyEntity;

        try {
            jsonData = setPrettyPrinting(jsonData);
            System.out.println(""+method+" "+url+" - request body:" + "\n" + jsonData);
        } catch (Exception ignored){}

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            switch (method) {

                case "POST":
                    HttpPost post = new HttpPost(url);
                    bodyEntity = new StringEntity(jsonData, "UTF-8");
                    post.setHeader("Content-type", "application/json; charset=UTF-8");
                    post.setEntity(bodyEntity);
                    post.setHeader("Authorization", authorization);

                        start = new Date();
                        CloseableHttpResponse postResponse = httpClient.execute(post);
                        end = new Date();
                        execTime = end.getTime() - start.getTime();

                    statusCode = postResponse.getStatusLine().getStatusCode();
                    logger.info("<br>["+new SimpleDateFormat("HH:mm:ss").format(start)+"] POST <a href="+url+">"+url+"</a>: "
                            + postResponse.getStatusLine() + " execution time " + execTime + " ms");
                    checkStatusCode(method,url,statusCode,expectedResponseCode);
                    try {
                        responseBody = EntityUtils.toString(postResponse.getEntity());
                    } catch (IllegalArgumentException ignored){}
                    httpClient.close();
                    break;

                case "GET":
                    HttpGet get = new HttpGet(url);
                    get.setHeader("Authorization", authorization);

                        start = new Date();
                        CloseableHttpResponse getResponse = httpClient.execute(get);
                        end = new Date();
                        execTime = end.getTime() - start.getTime();

                    statusCode = getResponse.getStatusLine().getStatusCode();
                    logger.info("<br>["+new SimpleDateFormat("HH:mm:ss").format(start)+"] GET <a href="+url+">"+url+"</a>: "
                            + getResponse.getStatusLine() + " execution time " + execTime + " ms");
                    checkStatusCode(method,url,statusCode,expectedResponseCode);
                    responseBody = EntityUtils.toString(getResponse.getEntity());
                    httpClient.close();
                    break;

                case "PUT":
                    HttpPut put = new HttpPut(url);
                    bodyEntity = new StringEntity(jsonData, "UTF-8");
                    put.setHeader("Content-type", "application/json; charset=UTF-8");
                    put.setEntity(bodyEntity);
                    put.setHeader("Authorization", authorization);

                        start = new Date();
                        CloseableHttpResponse putResponse = httpClient.execute(put);
                        end = new Date();
                        execTime = end.getTime() - start.getTime();

                    statusCode = putResponse.getStatusLine().getStatusCode();
                    logger.info("<br>["+new SimpleDateFormat("HH:mm:ss").format(start)+"] PUT <a href="+url+">"+url+"</a>: "
                            + putResponse.getStatusLine() + " execution time " + execTime + " ms");
                    checkStatusCode(method,url,statusCode,expectedResponseCode);
                    httpClient.close();
                    break;

                case "DELETE":
                    HttpDelete delete = new HttpDelete(url);
                    delete.setHeader("Authorization", authorization);

                        start = new Date();
                        CloseableHttpResponse deleteResponse = httpClient.execute(delete);
                        end = new Date();
                        execTime = end.getTime() - start.getTime();

                    statusCode = deleteResponse.getStatusLine().getStatusCode();
                    logger.info("<br>["+new SimpleDateFormat("HH:mm:ss").format(start)+"] DELETE <a href="+url+">"+url+"</a>: "
                            + deleteResponse.getStatusLine() + " execution time " + execTime + " ms");
                    checkStatusCode(method,url,statusCode,expectedResponseCode);
                    httpClient.close();
                    break;
            }

        } catch (Exception e) {
            logger.info("<p style='color:Red'>HttpRequest " + method + " " + url + ":" + e + "</p>");
            state = "exception";
        }

        try {
            responseBody = setPrettyPrinting(responseBody);
            System.out.println(responseBody);
        } catch (Exception ignored){}
    }

    private void checkStatusCode(String method,String url,int responseCode,int expectedResponseCode){
        try{
            Assert.assertEquals(responseCode,expectedResponseCode);
        } catch (AssertionError e){logger.info("<p style='color:Red'>"+method+" "+url+": " + e + "</p>");state="exception";}
    }
}