package confluence.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.mapping.LabeledPageMapping;
import confluence.model.Labels;
import confluence.model.Page;
import confluence.model.resources.LabeledPage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConfluenceCompositeService {

    private final PageService pageService;
    private final LabelService labelService;
    private final LabeledPageMapping labeledPageMapping;

    private static final String BODY_FORMAT_VIEW = "view";

    public ConfluenceCompositeService() {
        this.pageService = new PageService();
        this.labelService = new LabelService();
        this.labeledPageMapping = new LabeledPageMapping();
    }

    public LabeledPage getPageWithLabels(String pageId) throws UnirestException {
        Page page = this.pageService.getPage(pageId, BODY_FORMAT_VIEW);
        Labels labels = this.labelService.getLabels(pageId);
        return this.labeledPageMapping.mapFrom(page, labels);
    }
}
