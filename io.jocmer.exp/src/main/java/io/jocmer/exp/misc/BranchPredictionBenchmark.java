package io.jocmer.exp.misc;

import java.util.Arrays;
import java.util.Random;
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
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class BranchPredictionBenchmark {

    @State(Scope.Benchmark)
    public static class IntSamples {

        public final int[] unorderedInts;
        public final int[] orderedInts;

        public IntSamples() {
            unorderedInts = new int[1000000];
            Random r = new Random(System.nanoTime());

            for (int i = 0; i < unorderedInts.length; i++) {
                unorderedInts[i] = r.nextInt() % 512;
            }

            orderedInts = Arrays.copyOf(unorderedInts, unorderedInts.length);

            Arrays.sort(orderedInts);
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void unordered(IntSamples samples, Blackhole blackhole) {
        int countTo128 = 0;
        int countTo256 = 0;
        int countTo384 = 0;
        int countTo512 = 0;

        for (int i = 0; i < samples.unorderedInts.length; ++i) {
            if (samples.unorderedInts[i] < 128) {
                ++countTo128;
            } else if (samples.unorderedInts[i] < 256) {
                ++countTo256;
            } else if (samples.unorderedInts[i] < 384) {
                ++countTo384;
            } else if (samples.unorderedInts[i] < 512) {
                ++countTo512;
            }
        }

        blackhole.consume(countTo128);
        blackhole.consume(countTo256);
        blackhole.consume(countTo384);
        blackhole.consume(countTo512);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ordered(IntSamples samples, Blackhole blackhole) {
        int countTo128 = 0;
        int countTo256 = 0;
        int countTo384 = 0;
        int countTo512 = 0;

        for (int i = 0; i < samples.orderedInts.length; ++i) {
            if (samples.orderedInts[i] < 128) {
                ++countTo128;
            } else if (samples.orderedInts[i] < 256) {
                ++countTo256;
            } else if (samples.orderedInts[i] < 384) {
                ++countTo384;
            } else if (samples.orderedInts[i] < 512) {
                ++countTo512;
            }
        }

        blackhole.consume(countTo128);
        blackhole.consume(countTo256);
        blackhole.consume(countTo384);
        blackhole.consume(countTo512);
    }

}
