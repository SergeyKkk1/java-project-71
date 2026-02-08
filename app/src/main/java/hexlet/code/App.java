package hexlet.code;

import hexlet.code.differ.DiffResult;
import hexlet.code.differ.Differ;
import hexlet.code.formatter.Formatter;
import hexlet.code.parse.Parser;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
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
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        if (Files.exists(path1) && Files.exists(path2)) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath1));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(filePath2));
            bufferedReader.lines().forEach(line -> stringBuilder1.append(line).append("\n"));
            bufferedReader2.lines().forEach(line -> stringBuilder2.append(line).append("\n"));
            Map<String, Object> data1 = Parser.parse(stringBuilder1.toString(), filePath1);
            Map<String, Object> data2 = Parser.parse(stringBuilder2.toString(), filePath2);
            DiffResult diffResult = Differ.calculateDiffResult(data1, data2);
            this.spec.commandLine().getOut().println(Formatter.format(diffResult, outputFormat));
            return "0";
        } else {
            throw new IllegalArgumentException("no files found");
        }
    }

    public CommandSpec getSpec() {
        return spec;
    }

    public void setSpec(CommandSpec spec) {
        this.spec = spec;
    }
}
