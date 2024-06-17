package confluence.mapping;

import confluence.model.Label;
import confluence.model.Labels;
import confluence.model.Page;
import confluence.model.resources.LabeledPage;

import java.util.List;

public class LabeledPageMapping {

    public LabeledPage mapFrom(Page page, Labels labels) {
        String body = page.getBody().getView().getValue();
        List<String> labelsList = labels.getResults().stream().map(
                Label::getName
        ).toList();
        return new LabeledPage(body, labelsList);
    }
}
