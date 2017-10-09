package io.jocmer.exp;

import io.jocmer.exp.charts.ChartGenerator;
import io.jocmer.exp.charts.Matrixer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatFactory;
import org.openjdk.jmh.runner.Defaults;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.CommandLineOptionException;
import org.openjdk.jmh.runner.options.CommandLineOptions;

/**
 *
 * @author joc
 */
public class LauncherWithFormatedResults {

    public static void main(String... args) throws CommandLineOptionException, RunnerException, IOException {
        CommandLineOptions cmdOptions = new CommandLineOptions(args);

        Collection<RunResult> results = new Runner(cmdOptions).run();

        Map<String, ChartInfo> chartInfoMap = new LinkedHashMap<>();
        
        Map<String, List<RunResult>> resultsGroupByBenchmarkClass = new HashMap<>();

        for (RunResult result : results) {
            String benchClassName = result.getParams().getBenchmark().replaceFirst("\\.[^.]*$", "");

            ChartInfo ci = chartInfoMap.get(benchClassName);
            Matrixer m;

            if (ci == null) {
                m = new Matrixer("params", "Benchmark", 0);
                ci = new ChartInfo(m, result.getParams());
                chartInfoMap.put(benchClassName, ci);
            } else {
                m = ci.matrixer;
            }
            
            List<RunResult> resultsForThisClass = resultsGroupByBenchmarkClass.get(benchClassName);
            
            if(resultsForThisClass == null) {
                resultsForThisClass = new LinkedList<>();
                resultsGroupByBenchmarkClass.put(benchClassName, resultsForThisClass);
            }
            resultsForThisClass.add(result);

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
                Result primaryResult = benchmarkResult.getPrimaryResult();
                m.set(concatenatedParam, primaryResult.getLabel(), primaryResult.getScore(), benchmarkResult.getScoreUnit());
            }

        }

        for (Map.Entry<String, ChartInfo> entry : chartInfoMap.entrySet()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos, true, "utf-8");

            ResultFormatFactory.getInstance(
                    Defaults.RESULT_FORMAT,
                    ps
            ).writeOut(resultsGroupByBenchmarkClass.get(entry.getKey()));

            String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            ps.close();
            entry.getValue().setFormatedResult(content);

            ChartGenerator chartGenerator = new ChartGenerator("/barchart/barchartTemplate.html");

            chartGenerator.generateFromMatrix(entry.getValue(), "output/" + entry.getKey().replaceAll("\\.", "/") + ".html");
        }
    }


}
