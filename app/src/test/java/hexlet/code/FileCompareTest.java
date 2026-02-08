package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileCompareTest {
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
    void testJsonCompare() {
        int exitCode = cmd.execute("./src/test/resources/file1.json", "./src/test/resources/file2.json");

        String actual = sw.toString().trim();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(expectedCompareResult().trim(), actual);
    }

    @Test
    void testYamlCompare() {
        int exitCode = cmd.execute("./src/test/resources/file1.yml", "./src/test/resources/file2.yml");

        String actual = sw.toString().trim();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(expectedCompareResult().trim(), actual);
    }

    @Test
    void testPlainFormat() {
        int exitCode = cmd.execute("-f", "plain",
                "./src/test/resources/file1.json", "./src/test/resources/file2.json");

        String actual = sw.toString().trim();
        assertEquals(0, exitCode, "Command should exit with code 0");
        assertEquals(expectedPlainResult().trim(), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "./src/no/exist/file.json"})
    void testErrorFilePathError(String filePath) {
        int exitCode = cmd.execute(filePath, "./src/test/resources/file2.json");

        assertEquals(1, exitCode, "Command should exit with error code");
    }

    private String expectedCompareResult() {

        return """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
    }

    private String expectedPlainResult() {
        return """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";
    }
}
