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
@Warmup(iterations = 4, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class StringConcatBenchmark {

    @State(Scope.Benchmark)
    public static class Samples {

        /**
         * @return the intValue
         */
        public int getIntValue() {
            return intValue;
        }

        /**
         * @return the longValue
         */
        public long getLongValue() {
            return longValue;
        }

        /**
         * @return the stringValue
         */
        public String getStringValue() {
            return stringValue;
        }
        private int intValue = 3579;
        private long longValue = 13579L;
        private String stringValue = "toto";
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void plusOperator(Blackhole blackhole, Samples samples) {
        String concatenated = "aaaa" + samples.getIntValue() + "bbbb" + samples.getLongValue() + "cccc" + samples.getStringValue();
        blackhole.consume(concatenated);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void stringBuilder(Blackhole blackhole, Samples samples) {
        StringBuilder builder = new StringBuilder();
        String concatenated = builder.append("aaaa").
                append(samples.getIntValue()).
                append("bbbb").
                append(samples.getLongValue()).
                append("cccc").
                append(samples.getStringValue()).toString();
        blackhole.consume(concatenated);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void plusEqualOperator(Blackhole blackhole, Samples samples) {
        String concatenated = "aaaa";
        concatenated += samples.getIntValue();
        concatenated += "bbbb";
        concatenated += samples.getLongValue();
        concatenated += "cccc";
        concatenated += samples.getStringValue();
        blackhole.consume(concatenated);
    }

}
