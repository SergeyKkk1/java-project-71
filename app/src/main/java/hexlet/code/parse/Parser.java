package hexlet.code.parse;

import java.util.Map;

public final class Parser {
    private Parser() {
    }

    public static Map<String, Object> parse(String content, SupportedFileFormat format) throws Exception {
        return switch (format) {
            case JSON -> JsonParser.parse(content);
            case YAML -> YamlParser.parse(content);
        };
    }
}
