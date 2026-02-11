package hexlet.code;

import hexlet.code.formatter.Formatter;
import hexlet.code.parse.Parser;
import hexlet.code.parse.SupportedFileFormat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class Differ {
    private Differ() {
    }

    public static String generate(String filePath1, String filePath2, String outputFormat) throws Exception {
        Map<String, Object> data1 = getData(filePath1);
        Map<String, Object> data2 = getData(filePath2);

        DiffResult diffResult = DiffCalculator.getDiffResult(data1, data2);
        return Formatter.format(diffResult, outputFormat);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> data1 = getData(filePath1);
        Map<String, Object> data2 = getData(filePath2);

        DiffResult diffResult = DiffCalculator.getDiffResult(data1, data2);
        return Formatter.format(diffResult, "stylish");
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        String content = Files.readString(path);
        SupportedFileFormat format = getFileFormat(filePath);
        return Parser.parse(content, format);
    }

    private static SupportedFileFormat getFileFormat(String filePath) {
        String format = getExtension(filePath);
        return switch (format.toLowerCase()) {
            case "json" -> SupportedFileFormat.JSON;
            case "yaml", "yml" -> SupportedFileFormat.YAML;
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }

    private static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
