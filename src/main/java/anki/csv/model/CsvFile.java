package anki.csv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsvFile {
    private List<CsvRecord> records;
    private String name;

    public CsvFile(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }
}
