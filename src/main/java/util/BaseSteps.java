package util;

import com.google.gson.*;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Properties;

public class BaseSteps {
    protected static Properties properties = TestProperties.getInstance().getProperties();

    protected String state = "done";
    protected Logger logger = Logger.getLogger("logger");

    private JsonParser parser = new JsonParser();
    protected Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String apiUrl;

    protected void setUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    protected String getUrl() {
        return apiUrl;
    }

    String setPrettyPrinting(String jsonData){
        try {
            JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();
            jsonData = gson.toJson(jsonObject);
        } catch (IllegalStateException e){
            JsonArray jsonArray = parser.parse(jsonData).getAsJsonArray();
            jsonData = gson.toJson(jsonArray);
        }
        return jsonData;
    }

    protected JsonObject getJsonObject(String jsonData){
        JsonObject responseJO;
        responseJO = parser.parse(jsonData).getAsJsonObject();
        return responseJO;
    }

    protected JsonArray getJsonArray(String jsonData){
        JsonArray responseJO;
        responseJO = parser.parse(jsonData).getAsJsonArray();
        return responseJO;
    }

    protected void checkExceptions(String state){
        try {
            Assert.assertEquals(state,"done","checkExceptions");
        } catch (Exception e){
            logger.info("<p style='color:Red'>"+e+"</p>");
        }
    }

    protected void htmlBegin(){
        logger.info("<html>");
        logger.info("<head>");
        logger.info("<meta charset=Windows-1251>");
        logger.info("<style>.colortext{color: red;}</style>");
        logger.info("<body>");
    }
    protected void htmlEnd() {
        logger.info("</body>");
        logger.info("</head>");
        logger.info("</html>");
    }
}