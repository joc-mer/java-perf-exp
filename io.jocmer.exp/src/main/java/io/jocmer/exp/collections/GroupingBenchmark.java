package io.jocmer.exp.collections;

import java.util.HashMap;
import java.util.Objects;
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

/**
 *
 * @author joc
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class GroupingBenchmark {

    /**
     * Simple class which represents 2 integers.
     * Wrapper types have been choosen to slightly increase costs of hashcode and equals and thereby mignify 
     * the differences between map access style. 
     */
    static class KeyPairing {

        final Integer first;
        final Integer second;

        public KeyPairing(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.first);
            hash = 59 * hash + Objects.hashCode(this.second);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final KeyPairing other = (KeyPairing) obj;
            if (!Objects.equals(this.first, other.first)) {
                return false;
            }
            if (!Objects.equals(this.second, other.second)) {
                return false;
            }
            return true;
        }

    }

    @State(Scope.Benchmark)
    public static class Samples {

        public final KeyPairing[] keys;

        public Samples() {
            keys = new KeyPairing[1000000];
            Random r = new Random(System.nanoTime());

            for (int i = 0; i < keys.length; i++) {
                keys[i] = new KeyPairing(r.nextInt() % 32, r.nextInt() % 32);
            }
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashMapContainsThenGetAndPut(Samples samples) {
        HashMap<KeyPairing, Integer> countingMap = new HashMap<>(1024);

        for (KeyPairing key : samples.keys) {
            if (!countingMap.containsKey(key)) {
                countingMap.put(key, 1);
            } else {
                countingMap.put(key, countingMap.get(key) + 1);
            }
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashMapGetThenPut(Samples samples) {
        HashMap<KeyPairing, Integer> countingMap = new HashMap<>(1024);

        for (KeyPairing key : samples.keys) {
            Integer value = countingMap.get(key);

            if (value == null) {
                countingMap.put(key, 1);
            } else {
                countingMap.put(key, value + 1);
            }
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashMapCompute(Samples samples) {
        HashMap<KeyPairing, Integer> countingMap = new HashMap<>(1024);

        for (KeyPairing key : samples.keys) {
            countingMap.compute(key, (k, v) -> v == null ? 1 : v + 1);
        }

    }

}
