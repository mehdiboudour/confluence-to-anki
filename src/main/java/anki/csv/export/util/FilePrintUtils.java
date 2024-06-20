package anki.csv.export.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class FilePrintUtils {

    private FilePrintUtils() {
        //Not to be created
    }
    public static void printContentInCsvFile(StringWriter content, FileWriter fileWriter) throws IOException {
        fileWriter.write(content.toString());
        fileWriter.close();
    }
}
