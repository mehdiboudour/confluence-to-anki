package conversion.controller;

import anki.csv.controller.AnkiToCsvController;
import anki.model.resources.NotesBatch;
import com.mashape.unirest.http.exceptions.UnirestException;
import confluence.controller.ConfluenceController;
import confluence.model.resources.LabeledPage;
import conversion.mapping.ConfluencePageContentToAnkiNotesMapping;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ConversionController {

    private final ConfluenceController confluenceController;
    private final AnkiToCsvController ankiToCsvController;
    private final ConfluencePageContentToAnkiNotesMapping confluenceToAnkiMapping;

    public ConversionController() {
        this.confluenceController = new ConfluenceController();
        this.ankiToCsvController = new AnkiToCsvController();
        this.confluenceToAnkiMapping = new ConfluencePageContentToAnkiNotesMapping();
    }

    public List<String> convertConfluencePageToAnkiImportableCsv(String pageId) throws UnirestException, ParserConfigurationException, IOException, TransformerException, SAXException {
        LabeledPage confluencePage = this.confluenceController.getPageWithLabels(pageId);
        NotesBatch notes = this.confluenceToAnkiMapping.mapFrom(confluencePage);
        return this.ankiToCsvController.exportNotesToCsv(notes);
    }
}
