package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import static picocli.CommandLine.*;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App {
    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filePath1;
    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filePath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]", paramLabel = "format")
    private String outputFormat;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
