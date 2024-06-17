import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@ExtendWith(MockitoExtension.class)
@Disabled
class ConfluenceApiClientTest {
    //unirest : https://www.baeldung.com/unirest
    //properties : https://sybernix.medium.com/how-to-add-a-config-file-to-a-java-project-99fd9b6cebca
    //credentials : https://blog.netwrix.com/2022/11/14/how-to-hide-api-keys-github/
    //api : https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-label/#api-pages-id-labels-get

    private static Properties CONFLUENCE_CREDENTIALS;

    private static Properties getConfluenceCredentials() throws IOException {
        final String SECRET = "src/main/resources/secret.properties";
        FileInputStream secretInput = new FileInputStream(SECRET);
        Properties confluenceCredentials = new Properties();
        confluenceCredentials.load(secretInput);
        return confluenceCredentials;
    }
    @BeforeAll
    static void init() throws IOException {
        CONFLUENCE_CREDENTIALS = getConfluenceCredentials();
    }

    @Test
    void readApiCredentialsFromProperties() throws IOException {
        CONFLUENCE_CREDENTIALS.forEach((key, value) -> System.out.println(key+" = "+value));
        Assertions.assertTrue(Boolean.TRUE, "test passed.");
    }

    @Test
    void getConfluencePage() throws UnirestException {
        //Using the postman unirest generation
        HttpResponse<String> response = Unirest.get("https://betaya-software-foundation.atlassian.net/wiki/api/v2/pages/44466614?body-format=view")
                .header("Authorization", CONFLUENCE_CREDENTIALS.getProperty("confluence.authorization"))
                .asString();
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    void getConfluencePageContentUsingMapsRouteAndQueryParams() throws UnirestException {
        final String URL = "https://betaya-software-foundation.atlassian.net/wiki/api/v2/pages/{pageId}";
        Map<String, String> header = Map.of("Authorization", CONFLUENCE_CREDENTIALS.getProperty("confluence.authorization"));
        HttpResponse<String> response = Unirest.get(URL)
                .headers(header)
                .routeParam("pageId", "44466614")
                .queryString("body-format", "view")
                .asString();
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

}
