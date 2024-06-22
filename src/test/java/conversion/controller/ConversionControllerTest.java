package conversion.controller;

import anki.csv.controller.AnkiToCsvController;
import anki.model.resources.NotesBatch;
import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.controller.ConfluenceController;
import confluence.model.resources.LabeledPage;
import conversion.mapping.ConfluencePageContentToAnkiNotesMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ConversionControllerTest {

    @InjectMocks
    private ConversionController controllerTest;

    @Mock
    private ConfluenceController confluenceController;
    @Mock
    private AnkiToCsvController ankiToCsvController;
    @Mock
    private ConfluencePageContentToAnkiNotesMapping confluenceToAnkiMapping;

    private void injectDependency(String filed, Object mock) throws NoSuchFieldException, IllegalAccessException {
        Field dependency = ConversionController.class.getDeclaredField(filed);
        dependency.setAccessible(Boolean.TRUE);
        dependency.set(controllerTest, mock);
    }

    @BeforeEach
    void init() {
        controllerTest = new ConversionController();
    }

    @Test
    void mapFromTest() throws UnirestException, IOException, ParserConfigurationException, TransformerException, SAXException, NoSuchFieldException, IllegalAccessException {
        injectDependency("confluenceController", confluenceController);
        injectDependency("ankiToCsvController", ankiToCsvController);
        injectDependency("confluenceToAnkiMapping", confluenceToAnkiMapping);
        List<String> expected = List.of(
                "/Users/mehdi/IdeaProjects/confluence-to-anki/basic.csv",
                "/Users/mehdi/IdeaProjects/confluence-to-anki/cloze.csv"
        );
        Mockito.when(confluenceController.getPageWithLabels(ArgumentMatchers.anyString())).thenReturn(new LabeledPage());
        Mockito.when(confluenceToAnkiMapping.mapFrom(ArgumentMatchers.any(LabeledPage.class))).thenReturn(new NotesBatch());
        Mockito.when(ankiToCsvController.exportNotesToCsv(ArgumentMatchers.any(NotesBatch.class))).thenReturn(expected);
        List<String> actual = controllerTest.convertConfluencePageToAnkiImportableCsv("");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void convertConfluencePageToAnkiImportableCsvRealTest() throws UnirestException, ParserConfigurationException, IOException, TransformerException, SAXException {
        List<String> exports = this.controllerTest.convertConfluencePageToAnkiImportableCsv("129531907");
        System.out.print(exports);
    }
}
