package io.jocmer.exp;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.CommandLineOptionException;
import org.openjdk.jmh.runner.options.CommandLineOptions;

/**
 *
 * @author joc
 */
public class LauncherWithFormatedResults {

    public static void main(String... args) throws CommandLineOptionException, RunnerException {
        CommandLineOptions cmdOptions = new CommandLineOptions(args);

        Collection<RunResult> results = new Runner(cmdOptions).run();

        System.out.println(results.size() + " results...");

//        StringBuilder b = new StringBuilder();
        Matrixer matrixer = new Matrixer("count", "Impl", 0d);

        for (RunResult result : results) {
            Collection<BenchmarkResult> benchmarkResults = result.getBenchmarkResults();

            Collection<String> paramsKeys = result.getParams().getParamsKeys();

            String concatenatedParam = "";

            for (String key : paramsKeys) {
                if (!concatenatedParam.isEmpty()) {
                    concatenatedParam += ".";
                }
                concatenatedParam += result.getParams().getParam(key);
            }

            for (BenchmarkResult benchmarkResult : benchmarkResults) {
                BenchmarkParams params = benchmarkResult.getParams();
                Result primaryResult = benchmarkResult.getPrimaryResult();
//                b.append(primaryResult.getLabel());
//                b.append(",");
//                b.append(primaryResult.getScore());
//                b.append("\n");
                matrixer.set(concatenatedParam, primaryResult.getLabel(), primaryResult.getScore());
            }

        }

        System.out.println(matrixer.toCsvString());
        System.out.println(matrixer.toJson());
//        System.out.println(b);
    }

    public static class Matrixer {

        Map<String, Map<String, Double>> byLineMap = new LinkedHashMap<>();
        LinkedHashSet<String> columns = new LinkedHashSet<>();

        final String paramDimension;
        final String benchmarkDimension;
        final double defaultValue;

        public Matrixer(String paramDimension, String benchmarkDimension, double defaultValue) {
            this.paramDimension = paramDimension;
            this.benchmarkDimension = benchmarkDimension;
            this.defaultValue = defaultValue;
        }

        public void set(final String lineKey, final String columnKey, final double value) {
            columns.add(columnKey);

            byLineMap.compute(lineKey, (k, cm) -> {
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

        public String toCsvString() {
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
//            for (String column : columns) {
//                if (first) {
//                    first = false;
//                } else {
//                    builder.append(",");
//                }
//                builder.append("\"");
//                builder.append(column);
//                builder.append("\"");
//            }
//            builder.append("],\n");
//
//            builder.append("\t\"params\":[");
//            first = true;
//            for (String column : byLineMap.keySet()) {
//                if (first) {
//                    first = false;
//                } else {
//                    builder.append(",");
//                }
//                builder.append("\"");
//                builder.append(column);
//                builder.append("\"");
//            }
//            builder.append("]");

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

}
