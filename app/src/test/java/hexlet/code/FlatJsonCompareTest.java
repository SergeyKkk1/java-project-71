package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatJsonCompareTest {
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

    @Test
    void testFlatJson() {
        int exitCode = cmd.execute("./src/test/resources/file1.json", "./src/test/resources/file2.json");

        String actual = sw.toString().trim();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(expectedCompareResult().trim(), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "./src/no/exist/file.json"})
    void testFlatJsonEmptyFilePath(String filePath) {
        int exitCode = cmd.execute(filePath, "./src/test/resources/file2.json");

        assertEquals(1, exitCode, "Command should exit with error code");
    }

    private String expectedCompareResult() {

        return """
                {
                  - follow: false
                  host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
    }
}
