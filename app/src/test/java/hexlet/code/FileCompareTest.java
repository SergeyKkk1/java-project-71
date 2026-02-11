package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class FileCompareTest {
    private StringWriter sw;
    private CommandLine cmd;

    @BeforeEach
    void setUp() {
        App app = new App();
        cmd = new CommandLine(app);
        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cmd.setOut(pw);
        cmd.setErr(pw);
        cmd.setOut(new PrintWriter(sw));
    }

    @ParameterizedTest
    @MethodSource("formatCombinations")
    void testAllFormatCombinations(String outputFormat, String filePath1,
                                   String filePath2, String expectedFile) throws Exception {
        int exitCode = cmd.execute("-f", outputFormat, filePath1, filePath2);

        String actual = sw.toString();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(readExpected(expectedFile), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "./src/no/exist/file.json"})
    void testFilePathError(String filePath) {
        int exitCode = cmd.execute(filePath, "./src/test/resources/file2.json");

        assertEquals(1, exitCode, "Command should exit with error code");
    }

    @ParameterizedTest
    @MethodSource("defaultFormatCombinations")
    void testDefaultOutputFormat(String filePath1, String filePath2, String expectedFile) throws Exception {
        int exitCode = cmd.execute(filePath1, filePath2);

        String actual = sw.toString();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(readExpected(expectedFile), actual);
    }

    private static Stream<Arguments> formatCombinations() {
        return Stream.of(
                Arguments.of("stylish", "./src/test/resources/file1.json",
                        "./src/test/resources/file2.json", "expected_stylish.txt"),
                Arguments.of("plain", "./src/test/resources/file1.json",
                        "./src/test/resources/file2.json", "expected_plain.txt"),
                Arguments.of("json", "./src/test/resources/file1.json",
                        "./src/test/resources/file2.json", "expected_json.txt"),
                Arguments.of("stylish", "./src/test/resources/file1.yml",
                        "./src/test/resources/file2.yml", "expected_stylish.txt"),
                Arguments.of("plain", "./src/test/resources/file1.yml",
                        "./src/test/resources/file2.yml", "expected_plain.txt"),
                Arguments.of("json", "./src/test/resources/file1.yml",
                        "./src/test/resources/file2.yml", "expected_json.txt")
        );
    }

    private static Stream<Arguments> defaultFormatCombinations() {
        return Stream.of(
                Arguments.of("./src/test/resources/file1.json",
                        "./src/test/resources/file2.json", "expected_stylish.txt"),
                Arguments.of("./src/test/resources/file1.yml",
                        "./src/test/resources/file2.yml", "expected_stylish.txt")
        );
    }

    private String readExpected(String fileName) throws Exception {
        Path path = Paths.get("./src/test/resources/", fileName).toAbsolutePath().normalize();
        return Files.readString(path);
    }
}
