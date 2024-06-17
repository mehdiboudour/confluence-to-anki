package confluence.mapping;

import confluence.model.Labels;
import confluence.model.Page;
import confluence.model.resources.LabeledPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.JsonLoader;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class LabeledPageMappingTest {

    private static final String PAGE_SAMPLE = "src/test/resources/confluence/services/page.json";
    private static final String LABELS_SAMPLE = "src/test/resources/confluence/services/labels.json";

    @InjectMocks
    private LabeledPageMapping mappingTest;

    @Test
    void mapFromTest() throws IOException {
        Page page = JsonLoader.loadObjectFrom(Page.class, PAGE_SAMPLE);
        Labels labes = JsonLoader.loadObjectFrom(Labels.class, LABELS_SAMPLE);
        LabeledPage labeledPage = mappingTest.mapFrom(page, labes);
        String expected = "Test OK.";
        Assertions.assertEquals(expected, labeledPage.getBody());
        labeledPage.getLabels().forEach(
                label -> Assertions.assertEquals(expected, label)
        );
    }
}
