package confluence.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.mapping.LabeledPageMapping;
import confluence.model.Labels;
import confluence.model.Page;
import confluence.model.resources.LabeledPage;
import confluence.services.LabelService;
import confluence.services.PageService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConfluenceController {

    private final PageService pageService;
    private final LabelService labelService;
    private final LabeledPageMapping labeledPageMapping;

    private static final String BODY_FORMAT_VIEW = "view";

    public ConfluenceController() {
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
