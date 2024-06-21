package conversion.mapping;

import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;
import confluence.model.resources.LabeledPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;
import utils.CommonDataSetsMocker;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class ConfluencePageContentToAnkiNotesMappingTest {

    @InjectMocks
    private ConfluencePageContentToAnkiNotesMapping mappingTest;

    private static LabeledPage mockedLabeledPage;
    private static NotesBatch expectedNotesBatch;

    @BeforeAll
    static void init() throws IOException {
        mockedLabeledPage = CommonDataSetsMocker.loadLabeledPageSample();
        expectedNotesBatch = CommonDataSetsMocker.loadNotesBatchDataSet();
    }

    @Test
    void mapFromTest() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        NotesBatch acutalualNotesBatch = mappingTest.mapFrom(mockedLabeledPage);
        Assertions.assertEquals(expectedNotesBatch.size(), acutalualNotesBatch.size());
        for(int i=0; i<expectedNotesBatch.size(); i++){
            Note expected = expectedNotesBatch.get(i);
            Note actual = acutalualNotesBatch.get(i);
            Assertions.assertEquals(expected.getTags(), actual.getTags());
            Assertions.assertEquals(expected.getClass(), actual.getClass());
            if(expected instanceof Basic) {
                Assertions.assertEquals(((Basic) expected).getFront(), ((Basic) actual).getFront());
                Assertions.assertEquals(((Basic) expected).getBack(), ((Basic) actual).getBack());
            }
            if(expected instanceof Cloze) {
                Assertions.assertEquals(((Cloze) expected).getText(), ((Cloze) actual).getText());
                Assertions.assertEquals(((Cloze) expected).getBackExtra(), ((Cloze) actual).getBackExtra());
            }
        }
    }

    @Test
    @Disabled
    void mapFromRealTest() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        NotesBatch batch = mappingTest.mapFrom(mockedLabeledPage);
        System.out.println(batch);
        Assertions.assertTrue(Boolean.TRUE);
    }
}
