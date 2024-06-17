package confluence.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.model.Labels;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LabelService extends ResourceAbstractService{

    private static final Logger LOG = LogManager.getLogger(LabelService.class);

    private static final String RESOURCE = "/pages/{pageId}/labels";
    protected LabelService() {
        super(RESOURCE);
    }

    public Labels getLabels(String pageId) throws UnirestException {
        try {
            HttpResponse<Labels> response = this.buildAuthorizedGetRequest()
                    .routeParam("pageId", pageId)
                    .asObject(Labels.class);
            return response.getBody();
        } catch (UnirestException exception) {
            LOG.error("Get Labels for page with pageId=%s failed.".formatted(pageId));
            throw exception;
        }
    }
}
