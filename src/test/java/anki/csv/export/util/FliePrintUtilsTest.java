package anki.csv.export.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

@ExtendWith(MockitoExtension.class)
class FliePrintUtilsTest {

    @Test
    void printContentInCsvFile() throws IOException {
        StringWriter sw = new StringWriter();
        sw.write("test");
        FileWriter mockFileWriter = Mockito.mock(FileWriter.class);
        FilePrintUtils.printContentInCsvFile(sw, mockFileWriter);
        Mockito.verify(mockFileWriter).write(sw.toString());
        Mockito.verify(mockFileWriter).close();
    }
}
