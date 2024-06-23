package cli.command;

import com.mashape.unirest.http.exceptions.UnirestException;
import conversion.controller.ConversionController;
import org.xml.sax.SAXException;
import picocli.CommandLine;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "confluence-to-anki",
        version = "confluence-to-anki 1.0"
)
public class ConfluenceToAnkiCommand implements Callable<Integer> {
    //alias confluence-to-anki='java -cp "/Users/mehdi/IdeaProjects/confluence-to-anki/target/confluence-to-anki-1.0-SNAPSHOT-jar-with-dependencies.jar" cli.command.ConfluenceToAnkiCommand'

    @CommandLine.Parameters(index = "0")
    private String confluencePageId;

    private ConversionController conversionController;

    @Override
    public Integer call() throws UnirestException, ParserConfigurationException, IOException, TransformerException, SAXException {
        this.conversionController = new ConversionController();
        this.conversionController.convertConfluencePageToAnkiImportableCsv(this.confluencePageId)
                .forEach(System.out::println);
        return CommandLine.ExitCode.OK;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ConfluenceToAnkiCommand()).execute(args);
        System.exit(exitCode);
    }
}
