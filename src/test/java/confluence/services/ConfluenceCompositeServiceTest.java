package confluence.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.mapping.LabeledPageMapping;
import confluence.model.Labels;
import confluence.model.Page;
import confluence.model.resources.LabeledPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ConfluenceCompositeServiceTest {

    private ConfluenceCompositeService serviceTest;

    @Mock
    private PageService pageService;
    @Mock
    private LabelService labelService;
    @Mock
    private LabeledPageMapping labeledPageMapping;

    @Test
    void getPageWithLabelTest() throws UnirestException, IOException {
        serviceTest = new ConfluenceCompositeService(
                pageService, labelService, labeledPageMapping
        );
        String expected = "As expected.";
        Mockito.when(pageService.getPage(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()
        )).thenReturn(new Page());
        Mockito.when(labelService.getLabels(ArgumentMatchers.anyString()))
                .thenReturn(new Labels());
        Mockito.when(labeledPageMapping.mapFrom(
                ArgumentMatchers.any(Page.class), ArgumentMatchers.any(Labels.class)))
                .thenReturn(new LabeledPage(expected, List.of(expected, expected, expected)));
        LabeledPage actual = serviceTest.getPageWithLabels("");
        Assertions.assertEquals(expected, actual.getBody());
        actual.getLabels().forEach(
                label -> Assertions.assertEquals(expected, label)
        );
    }

    @Test
    @Disabled
    //Real life test to run the service with real data and perform a real query
    void getPageWithLabelsRealTest() throws UnirestException {
        serviceTest = new ConfluenceCompositeService();
        LabeledPage labeledPage = serviceTest.getPageWithLabels("44466614");
        Assertions.assertNotNull(labeledPage);
    }
}
