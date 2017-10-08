package io.jocmer.exp;

import java.util.Collection;
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

        StringBuilder b = new StringBuilder();

        for (RunResult result : results) {
            Collection<BenchmarkResult> benchmarkResults = result.getBenchmarkResults();

//            result.getPrimaryResult()
            for (BenchmarkResult benchmarkResult : benchmarkResults) {
                BenchmarkParams params = benchmarkResult.getParams();
                Result primaryResult = benchmarkResult.getPrimaryResult();
                b.append(primaryResult.getLabel());
                b.append(",");
                b.append(primaryResult.getScore());
            }
//            BenchmarkResult aggregatedResult = result.getAggregatedResult();
//            Multimap<String, Result> benchmarkResults = aggregatedResult.getBenchmarkResults();
//            System.out.println(benchmarkResults.keys().size() + " aggregated results...");
//
//            for (Map.Entry<String, Collection<Result>> entry : benchmarkResults.entrySet()) {
//                b.append(entry.getKey());
//                for (Result res : entry.getValue()) {
//                    b.append(", ");
//                    b.append(res.getScore());
//                }
//                b.append("\n");
//            }
        }

        System.out.println(b);

    }

}
