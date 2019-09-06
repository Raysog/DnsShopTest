package other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ivan on 06.09.2019.
 */
public class TestProperties {
    private static TestProperties INSTANCE = null;
    private Properties properties = new Properties();


    private TestProperties() {
        try {
            String propertyFileName = System.getProperty("environment", "dev");
            properties.load(new FileInputStream(new File("src/main/resources/"+propertyFileName+".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TestProperties getInstance(){
        if (INSTANCE==null){
            INSTANCE = new TestProperties();
        }
        return INSTANCE;
    }

    public Properties getProperties(){
        return properties;
    }

    public String getProperty(String key){
        return properties.get(key).toString();
    }

}
