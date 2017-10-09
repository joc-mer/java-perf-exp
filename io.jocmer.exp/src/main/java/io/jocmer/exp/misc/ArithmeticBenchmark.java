package io.jocmer.exp.misc;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 *
 * @author joc
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class ArithmeticBenchmark {

    @Param({"20"})
    public int count;

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public short primitiveShort() {
        short previous = 0;
        short current = (short) 1;

        for (int i = 0; i < count; i++) {
            short futur = (short) (previous + current);
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int primitiveInteger() {
        int previous = 0;
        int current = 1;

        for (int i = 0; i < count; i++) {
            int futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long primitiveLong() {
        long previous = 0;
        long current = 1;

        for (int i = 0; i < count; i++) {
            long futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float primitivefloat() {
        float previous = 0;
        float current = 1;

        for (int i = 0; i < count; i++) {
            float futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public double primitiveDouble() {
        double previous = 0;
        double current = 1;

        for (int i = 0; i < count; i++) {
            double futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public Integer wrapperInteger() {
        Integer previous = 0;
        Integer current = 1;

        for (int i = 0; i < count; i++) {
            Integer futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public Float wrapperFloat() {
        Float previous = new Float(0);
        Float current = new Float(1);

        for (int i = 0; i < count; i++) {
            Float futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public Double wrapperDouble() {
        Double previous = new Double(0);
        Double current = new Double(1);

        for (int i = 0; i < count; i++) {
            Double futur = previous + current;
            previous = current;
            current = futur;
        }

        return current;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public BigDecimal bigDecimal() {
        BigDecimal previous = new BigDecimal(0);
        BigDecimal current = new BigDecimal(1);

        for (int i = 0; i < count; i++) {
            BigDecimal futur = previous.add(current);
            previous = current;
            current = futur;
        }

        return current;
    }
}
