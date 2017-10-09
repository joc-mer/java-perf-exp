package io.jocmer.exp.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class ListsFillUpBenchmark {

    @Param({"1", "10", "100", "10000", "100000"})
    public int addCount;

    static final int OVERALL_ADD_COUNT = 100000;

    @Benchmark
    public void localVector(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            Vector<Object> list = new Vector<>();
            blackhole.consume(obj);//to be fare, consume an object to make a method call and be able to compare to collection sharing in other benchmarks

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void sharedVector(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            Vector<Object> list = new Vector<>();
            blackhole.consume(list);

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void linkedList(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            LinkedList<Object> list = new LinkedList<>();
            blackhole.consume(list);

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void arrayList(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            ArrayList<Object> list = new ArrayList<>();
            blackhole.consume(list);

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void preparedArrayList(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            ArrayList<Object> list = new ArrayList<>(addCount);
            blackhole.consume(list);

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void arrayListOf10(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            ArrayList<Object> list = new ArrayList<>(10);
            blackhole.consume(list);

            for (int j = 0; j < addCount; j++) {
                list.add(obj);
            }
        }
    }

    @Benchmark
    public void array(final Blackhole blackhole) {
        int round = OVERALL_ADD_COUNT / addCount;
        Object obj = new Object();

        for (int i = 0; i < round; ++i) {
            Object[] objArray = new Object[addCount];
            blackhole.consume(objArray);

            for (int j = 0; j < addCount; j++) {
                objArray[j] = obj;
            }
        }
    }

}
