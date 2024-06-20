package anki.csv.controller;

import anki.csv.export.Configuration;
import anki.csv.export.ExportService;
import anki.csv.mapping.AnkiToCsvMapping;
import anki.csv.model.CsvFile;
import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.CommonDataSetsMocker;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AnkiToCsvControllerTest {

    @InjectMocks
    private AnkiToCsvController controllerTest;

    @Mock
    private ExportService exportService;

    @Mock
    private AnkiToCsvMapping ankiToCsvMapping;

    private NotesBatch notes;

    @BeforeEach
    void init() throws IOException {
        notes = CommonDataSetsMocker.loadNotesBatchDataSet();
    }

    @Test
    void exportNotesToCsv() throws NoSuchFieldException, IllegalAccessException, IOException {
        List<String> expected = List.of("expected path for basic file",
                "expected path for cloze file");
        Field exportServiceInjection = AnkiToCsvController.class
                .getDeclaredField("exportService");
        Field ankiToCsvMappingInjection = AnkiToCsvController.class
                .getDeclaredField("ankiToCsvMapping");
        exportServiceInjection.setAccessible(Boolean.TRUE);
        ankiToCsvMappingInjection.setAccessible(Boolean.TRUE);
        exportServiceInjection.set(this.controllerTest, this.exportService);
        ankiToCsvMappingInjection.set(this.controllerTest, this.ankiToCsvMapping);

        Map<Class<? extends Note>, CsvFile> mockFiles = CommonDataSetsMocker.loadMapOfCsvFileByNoteType();
        Mockito.when(this.ankiToCsvMapping.mapTo(this.notes)).thenReturn(mockFiles);
        Mockito.when(this.exportService.export(mockFiles.get(Basic.class), Configuration.CURRENT_WORKING_DIRECTORY))
                .thenReturn(expected.getFirst());
        Mockito.when(this.exportService.export(mockFiles.get(Cloze.class), Configuration.CURRENT_WORKING_DIRECTORY))
                .thenReturn(expected.getLast());
        List<String> acutal = this.controllerTest.exportNotesToCsv(this.notes);
        Assertions.assertEquals(expected, acutal);
    }

    @Test
    @Disabled
    void exportNotesToCsvRealTest() {
        this.controllerTest = new AnkiToCsvController();
        System.out.print(controllerTest.exportNotesToCsv(notes));
        Assertions.assertTrue(Boolean.TRUE, "Test OK.");
    }
}
