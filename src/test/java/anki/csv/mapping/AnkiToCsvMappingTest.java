package anki.csv.mapping;

import anki.csv.model.CsvFile;
import anki.model.Note;
import anki.model.resources.NotesBatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.CommonDataSetsMocker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AnkiToCsvMappingTest {

    @InjectMocks
    private AnkiToCsvMapping mappingTest;

    @Test
    void mapToTest() throws IOException {
        Map<Class<? extends Note>, CsvFile> expected = CommonDataSetsMocker.loadMapOfCsvFileByNoteType();
        Map<Class<? extends Note>, CsvFile> actual =
                mappingTest.mapTo(CommonDataSetsMocker.loadNotesBatchDataSet());
        new HashMap<>(actual).forEach((key, value) -> {
            CsvFile expectedCsvFile = expected.get(key);
            Assertions.assertEquals(expectedCsvFile.getName(), value.getName());
            Assertions.assertEquals(expectedCsvFile.getRecords(), value.getRecords());
        });
    }

    @Test
    void maptToWithUnsupportedNoteTypeTest() {
        NotesBatch notes = new NotesBatch();
        notes.add(new UnsupportedNoteType());
        try {
            mappingTest.mapTo(notes);
        } catch (Error error) {
            Assertions.assertInstanceOf(AssertionError.class, error);
        }
    }

    private class UnsupportedNoteType extends Note {

    }

}
