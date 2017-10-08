package io.jocmer.exp.misc;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 *
 * @author joc
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class StringConcatBenchmark {

    @State(Scope.Benchmark)
    public static class Samples {
        public int intValue = 3579;
        public long longValue = 13579L;
        public String stringValue = "toto";
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void plusOperator(Blackhole blackhole, Samples samples) {
        String concatenated = "aaaa" + samples.intValue + "bbbb" + samples.longValue + "cccc" + samples.stringValue;
        blackhole.consume(concatenated);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void stringBuilder(Blackhole blackhole, Samples samples) {
        StringBuilder builder = new StringBuilder();
        String concatenated = builder.append("aaaa").
                append(samples.intValue).
                append("bbbb").
                append(samples.longValue).
                append("cccc").
                append(samples.stringValue).toString();
        blackhole.consume(concatenated);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void plusEqualOperator(Blackhole blackhole, Samples samples) {
        String concatenated = "aaaa";
        concatenated += samples.intValue;
        concatenated += "bbbb";
        concatenated += samples.longValue;
        concatenated += "cccc";
        concatenated += samples.stringValue;
        blackhole.consume(concatenated);
    }

}
