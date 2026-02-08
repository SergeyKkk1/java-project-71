package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Model.CommandSpec;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;
import static picocli.CommandLine.Spec;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<String> {
    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filePath1;
    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filePath2;

    @Option(names = {"-f", "--format"}, defaultValue = "stylish", description = "output format [default: stylish]", paramLabel = "format")
    private String outputFormat;

    @Spec
    private CommandSpec spec;

    public static void main(String[] args) {
        App app = new App();
        int exitCode = new CommandLine(app).execute(args);
        System.exit(exitCode);
    }

    @Override
    public String call() throws Exception {
        if (filePath1 == null || filePath2 == null || filePath1.isEmpty() || filePath2.isEmpty()) {
            throw new IllegalArgumentException("filePath1 or filePath2 is empty");
        }
        String diffResult = Differ.generate(filePath1, filePath2, outputFormat);
        if (spec != null) {
            spec.commandLine().getOut().print(diffResult);
        } else {
            System.out.println(diffResult);
        }
        return "0";
    }

    public CommandSpec getSpec() {
        return spec;
    }

    public void setSpec(CommandSpec spec) {
        this.spec = spec;
    }
}
