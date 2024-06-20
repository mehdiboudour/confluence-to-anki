package anki.csv.model;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor
public class CsvRecord extends ArrayList<String> {
    //Each element of the list represents a value of the record.

    public CsvRecord(String... values) {
        this.addAll(Arrays.asList(values));
    }
}
