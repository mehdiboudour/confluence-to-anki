package anki.csv.mapping;

import anki.csv.model.CsvFile;
import anki.csv.model.CsvRecord;
import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;

import java.util.Map;


public class AnkiToCsvMapping {

    public Map<Class<? extends Note>, CsvFile> mapTo(NotesBatch notes) {
        CsvFile basics = new CsvFile("basic");
        CsvFile clozes = new CsvFile("cloze");
        notes.forEach(
                note -> {
                    if(note instanceof Basic) {
                        Basic basic = (Basic) note;
                        basics.getRecords().add(new CsvRecord(
                                basic.getFront(),
                                basic.getBack(),
                                basic.getTags()
                        ));
                    } else if( note instanceof Cloze) {
                        Cloze cloze = (Cloze) note;
                        clozes.getRecords().add(new CsvRecord(
                                cloze.getText(),
                                cloze.getBackExtra(),
                                cloze.getTags()
                        ));
                    } else {
                        assert Boolean.FALSE
                                : "Unsupported note type.";
                    }
                }
        );

        return Map.of(
                Basic.class, basics,
                Cloze.class, clozes
        );
    }
}
