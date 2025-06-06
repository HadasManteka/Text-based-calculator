package calculator;

import java.util.LinkedHashMap;
import java.util.Map;

public class Context {
    private final Map<String, Double> vars = new LinkedHashMap<>();

    public double get(String name) {
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable '" + name + "' is not defined.");
        }
        return vars.get(name);
    }

    public void set(String name, double value) {
        vars.put(name, value);
    }

    public Map<String, Double> getAll() {
        return vars;
    }
}
