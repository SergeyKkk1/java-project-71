package hexlet.code.parse;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class YamlParser {
    private YamlParser() {

    }

    public static Map<String, Object> parse(String content) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, new TypeReference<>() {
        });
    }
}
