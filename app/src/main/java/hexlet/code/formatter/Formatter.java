package hexlet.code.formatter;

import hexlet.code.DiffResult;

public final class Formatter {
    private Formatter() {
    }

    public static String format(DiffResult diffResult, String format) throws Exception {
        return switch (format.toLowerCase()) {
            case "stylish" -> StylishFormatter.getFormattedData(diffResult);
            case "plain" -> PlainFormatter.getFormattedData(diffResult);
            case "json" -> JsonFormatter.getFormattedData(diffResult);
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
