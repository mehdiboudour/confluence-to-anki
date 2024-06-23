import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "poc",
        mixinStandardHelpOptions = true,
        version = "poc 1.0",
        description = "Proof of concept cli command."
)
public class ProofOfConceptCommand implements Callable<Integer> {
    //https://picocli.info/#_packaging_your_application
    //https://stackoverflow.com/questions/574594/how-can-i-create-an-executable-runnable-jar-with-dependencies-using-maven
    //alias proof-of-concept='java -cp "/Users/mehdi/IdeaProjects/confluence-to-anki/target/confluence-to-anki-1.0-SNAPSHOT-jar-with-dependencies.jar" cli.command.ProofOfConceptCommand'
    @CommandLine.Parameters(index = "0", description = "A sample parameter")
    private String parameterOfCommand;

    @Override
    public Integer call() {
        System.out.printf("Running business logic with parameter of value : %s.%n", this.parameterOfCommand);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ProofOfConceptCommand()).execute(args);
        System.exit(exitCode);
    }
}
