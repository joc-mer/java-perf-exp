package io.jocmer.exp.charts;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 *
 * @author joc
 */
public class Matrixer {
    
    private Map<String, Map<String, Double>> byLineMap = new LinkedHashMap<>();
    private LinkedHashSet<String> columns = new LinkedHashSet<>();
    private final String paramDimension;
    private final String benchmarkDimension;
    private final double defaultValue;
    private String scale;

    public Matrixer(String paramDimension, String benchmarkDimension, double defaultValue) {
        this.paramDimension = paramDimension;
        this.benchmarkDimension = benchmarkDimension;
        this.defaultValue = defaultValue;
    }

    public String getScale() {
        return scale;
    }
    
    public void set(final String lineKey, final String columnKey, final double value, final String scale) {
        this.scale = scale;
        columns.add(columnKey);
        byLineMap.compute(lineKey, (java.lang.String k, java.util.Map<java.lang.String, java.lang.Double> cm) -> {
            Map<String, Double> nm;
            if (cm == null) {
                nm = new LinkedHashMap<>();
            } else {
                nm = cm;
            }
            nm.put(columnKey, value);
            return nm;
        });
    }

    public String toFlippedCsvString() {
        StringBuilder builder = new StringBuilder();
        builder.append(benchmarkDimension);
        for (String column : columns) {
            builder.append(",");
            builder.append(column);
        }
        for (Map.Entry<String, Map<String, Double>> entry : byLineMap.entrySet()) {
            builder.append("\n").append(entry.getKey());
            for (String column : columns) {
                builder.append(",");
                Double inMatrix = entry.getValue().get(column);
                if (inMatrix == null) {
                    builder.append(defaultValue);
                } else {
                    builder.append(inMatrix);
                }
            }
        }
        return builder.toString();
    }
    
    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("\t\"keys\":[");
        boolean first = true;
        for (String column : columns) {
            if (first) {
                first = false;
            } else {
                builder.append(",");
            }
            builder.append("\"");
            builder.append(column);
            builder.append("\"");
        }
        builder.append("],\n");
        builder.append("\t\"params\":[");
        first = true;
        for (String column : byLineMap.keySet()) {
            if (first) {
                first = false;
            } else {
                builder.append(",");
            }
            builder.append("\"");
            builder.append(column);
            builder.append("\"");
        }
        builder.append("]");
        for (Map.Entry<String, Map<String, Double>> entry : byLineMap.entrySet()) {
            builder.append(",");
            builder.append("\n");
            builder.append("\t\"").append(entry.getKey()).append("\": {");
            first = true;
            for (String column : columns) {
                if (first) {
                    first = false;
                } else {
                    builder.append(",");
                }
                builder.append("\"").append(column).append("\": ");
                Double inMatrix = entry.getValue().get(column);
                if (inMatrix == null) {
                    builder.append(defaultValue);
                } else {
                    builder.append(inMatrix);
                }
            }
            builder.append("}");
        }
        builder.append("\n}\n");
        return builder.toString();
    }

    public String toArrayJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        //            builder.append("\t\"keys\":[");
        boolean first = true;
        for (Map.Entry<String, Map<String, Double>> entry : byLineMap.entrySet()) {
            if (first) {
                first = false;
            } else {
                builder.append(",");
            }
            builder.append("{\"").append(benchmarkDimension).append("\" : ").append(entry.getKey()).append(", ");
            for (String column : columns) {
                builder.append("\"").append(column).append("\": ");
                Double inMatrix = entry.getValue().get(column);
                if (inMatrix == null) {
                    builder.append(defaultValue);
                } else {
                    builder.append(inMatrix);
                }
            }
            builder.append("}\n");
        }
        builder.append("]\n");
        return builder.toString();
    }
    
}
