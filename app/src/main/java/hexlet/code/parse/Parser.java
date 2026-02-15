package hexlet.code.parse;

import java.util.Map;

public final class Parser {
    private Parser() {
    }

    public static Map<String, Object> parse(String content, String dataFormat) throws Exception {
        SupportedDataFormat format = getDataFormat(dataFormat);
        return switch (format) {
            case JSON -> JsonParser.parse(content);
            case YAML -> YamlParser.parse(content);
        };
    }

    private static SupportedDataFormat getDataFormat(String dataFormat) {
        String format = dataFormat == null ? "" : dataFormat;
        return switch (format.toLowerCase()) {
            case "json" -> SupportedDataFormat.JSON;
            case "yaml", "yml" -> SupportedDataFormat.YAML;
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
