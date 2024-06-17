package confluence.services;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import java.util.Map;

public abstract class ResourceAbstractService {
    private static final Map<String, String> AUTHORIZATION =
            Map.of("Authorization", Configuration.BASIC_AUTH);
    protected final String uri;

    protected ResourceAbstractService(String uri) {
        this.uri = uri;
    }

    private String buildUrl() {
        return Configuration.BASE_URL+this.uri;
    }

    protected GetRequest buildAuthorizedGetRequest() {
        return Unirest.get(this.buildUrl()).headers(AUTHORIZATION);
    }
}
