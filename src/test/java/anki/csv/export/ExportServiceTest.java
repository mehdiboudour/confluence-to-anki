package anki.csv.export;

import anki.csv.export.util.FilePrintUtils;
import anki.csv.model.CsvFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.JsonLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    @InjectMocks
    private ExportService serviceTest;

    private static final String DATA_SET = "src/test/resources/anki/csv-file.json";
    private static final String RESOURCES_PATH = "src/test/resources/anki";

    private CsvFile mockCsvFile;

    @BeforeEach
    void init() throws IOException {
        mockCsvFile = JsonLoader.loadObjectFrom(CsvFile.class, DATA_SET);
    }

    @Test
    void exportTest() throws IOException {
        String expected = "src/test/resources/anki/fileName.csv";
        try (MockedConstruction<FileWriter> mockedFileWriter =
                     Mockito.mockConstruction(FileWriter.class, (_, _) -> {
        })) {
            try (MockedStatic<FilePrintUtils> mockedStatic = Mockito.mockStatic(FilePrintUtils.class)) {
                mockedStatic.when(() -> FilePrintUtils
                                .printContentInCsvFile(
                                        ArgumentMatchers.any(StringWriter.class),
                                        ArgumentMatchers.any(FileWriter.class)))
                        .thenAnswer(invocation -> {
                            // Do nothing, so the actual method is not executed
                            return null;
                        });

                String actual = serviceTest.export(mockCsvFile, RESOURCES_PATH);
                Assertions.assertEquals(expected, actual);
            }
        }
    }

    @Test
    @Disabled
    void exportRealTest() throws IOException {
        String destination = "%s/%s".formatted(Configuration.CURRENT_WORKING_DIRECTORY, RESOURCES_PATH);
        serviceTest.export(mockCsvFile, destination);
        Assertions.assertTrue(Boolean.TRUE, "Test OK.");
    }
}
