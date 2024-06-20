package anki.csv.controller;

import anki.csv.export.Configuration;
import anki.csv.export.ExportService;
import anki.csv.model.CsvFile;
import anki.csv.mapping.AnkiToCsvMapping;
import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnkiToCsvController {

    private ExportService exportService;
    private AnkiToCsvMapping ankiToCsvMapping;

    private static final List<Class<? extends Note>> AVAILABLE_NOTE_TYPES = List.of(Basic.class, Cloze.class);

    public AnkiToCsvController() {
        this.exportService = new ExportService();
        this.ankiToCsvMapping = new AnkiToCsvMapping();
    }

    public List<String> exportNotesToCsv(NotesBatch notes) {
        Map<Class<? extends Note>, CsvFile> files = this.ankiToCsvMapping.mapTo(notes);
        return AVAILABLE_NOTE_TYPES.stream().map(
                noteType -> {
                    try {
                        return this.exportService.export(files.get(noteType), Configuration.CURRENT_WORKING_DIRECTORY);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();
    }
}
