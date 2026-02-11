package hexlet.code.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffResult;

public final class JsonFormatter {

    private JsonFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) throws Exception {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(diffResult);
    }
}
