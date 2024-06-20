package utils;

import anki.csv.model.CsvFile;
import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;

import java.io.IOException;
import java.util.Map;

public class CommonDataSetsMocker {

    private static final String BASIC_DATA_SET_1 = "src/test/resources/anki/basic1.json";
    private static final String BASIC_DATA_SET_2 = "src/test/resources/anki/basic2.json";
    private static final String CLOZE_DATA_SET_1 = "src/test/resources/anki/cloze1.json";
    private static final String CLOZE_DATA_SET_2 = "src/test/resources/anki/cloze2.json";
    private static final String BASIC_CSV_FILE_DATA_SET = "src/test/resources/anki/basic-csv-file.json";
    private static final String CLOZE_CSV_FILE_DATA_SET = "src/test/resources/anki/cloze-csv-file.json";

    public static NotesBatch loadNotesBatchDataSet() throws IOException {
        NotesBatch notes = new NotesBatch();
        notes.add(JsonLoader.loadObjectFrom(Basic.class, BASIC_DATA_SET_1));
        notes.add(JsonLoader.loadObjectFrom(Basic.class, BASIC_DATA_SET_2));
        notes.add(JsonLoader.loadObjectFrom(Cloze.class, CLOZE_DATA_SET_1));
        notes.add(JsonLoader.loadObjectFrom(Cloze.class, CLOZE_DATA_SET_2));
        return notes;
    }

    public static Map<Class<? extends Note>, CsvFile> loadMapOfCsvFileByNoteType() throws IOException {
        return Map.of(
                Basic.class, JsonLoader.loadObjectFrom(CsvFile.class, BASIC_CSV_FILE_DATA_SET),
                Cloze.class, JsonLoader.loadObjectFrom(CsvFile.class, CLOZE_CSV_FILE_DATA_SET)
        );
    }
}
