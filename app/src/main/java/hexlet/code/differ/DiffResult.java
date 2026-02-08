package hexlet.code.differ;

import java.util.List;
import java.util.Map;

public record DiffResult(List<Map.Entry<String, Object>> deletedKeys,
                  List<Map.Entry<String, Object>> addedKeys,
                  List<Map.Entry<String, Object>> changedKeys,
                  List<Map.Entry<String, Object>> commonKeys) {

}
