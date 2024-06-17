package confluence.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final String LOCATION = "src/main/resources/configuration.properties";
    private static final String BASE_URL_PROPERTY = "confluence.api.baseurl";
    private static final String CREDENTIAL_PROPERTY = "confluence.api.credentials";
    private static final String AUTHORIZATION_PROPERTY = "confluence.authorization";
    protected static String BASE_URL;
    protected static String BASIC_AUTH;

    private Configuration() {
        //Don't instanciate me
    }

    static {
        try {
            loadProperties();
            configureObjecMapper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadProperties() throws IOException {
        FileInputStream configuraitonFile = new FileInputStream(LOCATION);
        Properties configurations = new Properties();
        configurations.load(configuraitonFile);
        BASE_URL = configurations.getProperty(BASE_URL_PROPERTY);
        configuraitonFile = new FileInputStream(configurations.getProperty(CREDENTIAL_PROPERTY));
        configurations.load(configuraitonFile);
        BASIC_AUTH = configurations.getProperty(AUTHORIZATION_PROPERTY);
        configuraitonFile.close();
    }

    private static void configureObjecMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();
            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
