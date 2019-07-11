package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class TestProperties {

    private final Properties properties = new Properties();
    private static TestProperties INSTANCE = null;

    private TestProperties(){
        try {
            properties.loadFromXML(new FileInputStream(new File("properties.xml")));
            //properties.load(new FileInputStream(new File("./" + "test.properties")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    static TestProperties getInstance(){
        if (INSTANCE == null){
            INSTANCE = new TestProperties();
        }
        return INSTANCE;
    }

    Properties getProperties() {return properties;}
}