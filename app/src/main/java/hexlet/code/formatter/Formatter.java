package hexlet.code.formatter;

import hexlet.code.differ.DiffResult;

public class Formatter {
    private Formatter() {
    }

    public static String format(DiffResult diffResult, String format) {
        return switch (format.toLowerCase()) {
            case "stylish" -> StylishFormatter.getFormattedData(diffResult);
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
