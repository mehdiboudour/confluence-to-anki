import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

@ExtendWith(MockitoExtension.class)
@Disabled
class PrintStringWriterToFile {

    //https://stackoverflow.com/questions/11883294/writing-to-txt-file-from-stringwriter

    private static final String DESTINATION = "src/main/resources/design/detailed-logical-views/anki/proof-of-concept";

    private static final String DATA_SET = """
                front 1,back 1,tags 1
                front 2,back 2,tags 2
                front 3,back 3,tags 3
                """;

    @Test
    void test() throws IOException {
        FileWriter fw = new FileWriter(DESTINATION+"/output.csv");
        StringWriter sw = new StringWriter();
        sw.write(DATA_SET);
        fw.write(sw.toString());
        fw.close();
    }
}
