package hexlet.code.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonParser {
    private JsonParser() {

    }

    public static Map<String, Object> getData(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() {
        });
    }
}
