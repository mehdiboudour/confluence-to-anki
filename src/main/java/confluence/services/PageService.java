package confluence.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.model.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//specification=https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-page/#api-pages-get
public class PageService extends ResourceAbstractService{

    private static final Logger LOG = LogManager.getLogger(PageService.class);
    private static final String RESOURCE = "/pages/{pageId}";
    public PageService() {
        super(RESOURCE);
    }

    public Page getPage(String pageId, String bodyFormat) throws UnirestException {
        try {
            HttpResponse<Page> response = this.buildAuthorizedGetRequest()
                    .routeParam("pageId", pageId)
                    .queryString("body-format", bodyFormat)
                    .asObject(Page.class);
            return response.getBody();
        } catch (UnirestException exception) {
            LOG.error("GET %s failed, pageId=%s, body-format=%s.".formatted(RESOURCE, pageId, bodyFormat));
            throw exception;
        }
    }
}
