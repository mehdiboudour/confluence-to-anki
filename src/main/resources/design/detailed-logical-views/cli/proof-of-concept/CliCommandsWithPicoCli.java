import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

@ExtendWith(MockitoExtension.class)
@Disabled
@CommandLine.Command(name = "mainCommand")
class CliCommandsWithPicoCli implements Runnable{

    @CommandLine.Parameters(index = "0")
    private String pageId;

    @Test
    void runApplication() {
        CommandLine.run(new CliCommandsWithPicoCli(),"34234234");
    }

    @Override
    public void run() {
        System.out.println("Command called with page id = "+this.pageId);
    }

}