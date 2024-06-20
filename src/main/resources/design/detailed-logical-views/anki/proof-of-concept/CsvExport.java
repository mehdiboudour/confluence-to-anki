import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Disabled
class CsvExport {
    //baeldung : https://www.baeldung.com/apache-commons-csv

    private static final List<List<String>> DATA_SET =
            List.of(
                    List.of("front 1", "back 1", "tags 1"),
                    List.of("front 2", "back 2", "tags 2"),
                    List.of("front 3", "back 3", "tags 3")
            );

    @Test
    void exportACsvCellByCell() throws IOException {
        final String expected = """
                front 1,back 1,tags 1
                front 2,back 2,tags 2
                front 3,back 3,tags 3
                """;

        StringWriter output = new StringWriter();
        CSVFormat format = CSVFormat.DEFAULT.builder().setRecordSeparator("\n").build();
        for(List<String> csvRecord : DATA_SET) {
            final boolean[] newLine = {Boolean.TRUE};
            csvRecord.forEach(
                    cell -> {
                        try {
                            format.print(cell, output, newLine[0]);
                            newLine[0] = Boolean.FALSE;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            output.append(format.getRecordSeparator());
        }
        System.out.print(output);
        Assertions.assertEquals(expected, output.toString());
    }

}
