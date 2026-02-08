package hexlet.code.parse;

import java.util.Map;

public class Parser {
    private Parser() {
    }

    public static Map<String, Object> parse(String content, String filePath) throws Exception {
        String format = getExtension(filePath);
        return switch (format.toLowerCase()) {
            case "json" -> JsonParser.parse(content);
            case "yaml", "yml" -> YamlParser.parse(content);
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
