package io.jocmer.exp.collections;

import com.gs.collections.impl.set.mutable.UnifiedSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Benchmark showing efficience of different collections.
 *
 * @author joc
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class MassiveContainsCallsBenchmark {

    @State(Scope.Benchmark)
    public static class LongSamples {

        public final long[] toBeChecked;

        public LongSamples() {
            toBeChecked = new long[1000000];
            Random r = new Random(System.nanoTime());

            for (int i = 0; i < toBeChecked.length; i++) {
                toBeChecked[i] = r.nextLong();
            }

        }

    }

    @State(Scope.Benchmark)
    public static class DataStructures {

        @Param({"1", "5", "10", "50", "100", "500", "1000"})
        int inserted;

        public LongBloomFilter bloomFilter;

        public Set<Long> hashSet = new HashSet<>();

        public Set<Long> treeSet = new TreeSet<>();

        public Set<Long> linkedHashSet = new LinkedHashSet<>();

        public Set<Long> skipList = new ConcurrentSkipListSet<>();

        public Set<Long> copyOnWriteArray = new CopyOnWriteArraySet<>();

        public Set<Long> gsUnifiedSet = new UnifiedSet<>();//HashSet from Goldman Sachs

        public ArrayList<Long> arrayList = new ArrayList<>();

        public LinkedList<Long> linkedList = new LinkedList<>();

        public long[] orderedArray;//for binary search

        public long[] unorderedArray;

        public DataStructures() {
        }

        @Setup(Level.Trial)
        public void setup() {
            Random r = new Random(System.nanoTime());
            unorderedArray = new long[inserted];
            for (int i = 0; i < inserted; ++i) {
                long newRandomLong = r.nextLong();
                unorderedArray[i] = newRandomLong;
                hashSet.add(newRandomLong);
                treeSet.add(newRandomLong);
                linkedHashSet.add(newRandomLong);
                skipList.add(newRandomLong);
                copyOnWriteArray.add(newRandomLong);
                gsUnifiedSet.add(newRandomLong);
                arrayList.add(newRandomLong);
                linkedList.add(newRandomLong);
            }

            bloomFilter = new LongBloomFilter(unorderedArray);

            orderedArray = Arrays.copyOf(unorderedArray, unorderedArray.length);
            Arrays.sort(orderedArray);
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void emptyLoop(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            blackhole.consume(true);
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void naiveLongBloomFilter(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.bloomFilter.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void hashSet(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.hashSet.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void treeSet(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.treeSet.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void linkedHashSet(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.linkedHashSet.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void skipList(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.skipList.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void copyOnWriteArray(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.copyOnWriteArray.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void goldmanSachsUnifiedSet(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.gsUnifiedSet.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void arrayList(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.arrayList.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void linkedList(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (dataStructures.linkedList.contains(samples.toBeChecked[i])) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void binarySearch(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            if (Arrays.binarySearch(dataStructures.orderedArray, samples.toBeChecked[i]) > 0) {
                ++successCount;
            }
        }
        blackhole.consume(successCount);
    }

    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void arraySearch(LongSamples samples, DataStructures dataStructures, Blackhole blackhole) {
        int successCount = 0;
        for (int i = 0; i < samples.toBeChecked.length; ++i) {
            for (int j = 0; j < dataStructures.unorderedArray.length; j++) {
                if (samples.toBeChecked[i] == dataStructures.unorderedArray[j]) {
                    ++successCount;
                }
            }
        }
        blackhole.consume(successCount);
    }

}
