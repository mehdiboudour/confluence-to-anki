package anki.csv.export;

import anki.csv.export.util.FilePrintUtils;
import anki.csv.model.CsvFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class ExportService {

    private static final Logger LOG = LogManager.getLogger(ExportService.class);

    private static final String CSV_EXTENTION = ".csv";

    public String export(CsvFile csvFile, String destination) throws IOException { //returns the full name of the exported file
        String filename = "%s/%s%s".formatted(destination, csvFile.getName(),CSV_EXTENTION);
        StringWriter content = this.formatToCsv(csvFile);
        FileWriter fileWriter = new FileWriter(filename);
        FilePrintUtils.printContentInCsvFile(content, fileWriter);
        return filename;
    }

    private StringWriter formatToCsv(CsvFile csvFile) {
        StringWriter output = new StringWriter();
        CSVFormat format = CSVFormat.DEFAULT.builder().setRecordSeparator("\n").build();
        for (List<String> csvRecord : csvFile.getRecords()) {
            final boolean[] newRecord = {Boolean.TRUE};
            csvRecord.forEach(
                    cell -> {
                        try {
                            format.print(cell, output, newRecord[0]);
                            newRecord[0] = Boolean.FALSE;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            output.append(format.getRecordSeparator());
        }
        return output;
    }


}
