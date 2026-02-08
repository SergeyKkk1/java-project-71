package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YamlCompareTest {
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
    }

    @Test
    void testFlatYaml() {
        int exitCode = cmd.execute("./src/test/resources/file1.yml", "./src/test/resources/file2.yml");

        String actual = sw.toString().trim();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(expectedCompareResult().trim(), actual);
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
