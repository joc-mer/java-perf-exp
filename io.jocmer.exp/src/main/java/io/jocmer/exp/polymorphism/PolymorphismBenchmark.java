package io.jocmer.exp.polymorphism;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class PolymorphismBenchmark {

    @State(Scope.Thread)
    public static class BenchmarkState {

        @Param("500")
        int interrationCount;

        NotFinalOverride notFinalOverride = new NotFinalOverride();
        FinalOverride finalOverride = new FinalOverride();

        AbstractClass notFinalOverrideAsSuperClass = AbstractClass.notFinalOverrideAsSuperClass();
        AbstractClass finalOverrideAsSuperClass = AbstractClass.finalOverrideAsSuperClass();

        Interface interfaceImpl = new Implementation();
        Interface lambda = () -> 666;

        FinalClass finalClass = new FinalClass();

        SingleImplInterface singleImplInterface = SingleImplementation.impl();

        ActualyFinalClass actualyFinalClass = new ActualyFinalClass();

        Method method;

        public BenchmarkState() {
            try {
                method = ActualyFinalClass.class.getDeclaredMethod("produceIntValue");
            } catch (NoSuchMethodException | SecurityException ignore) {
            }
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void emptyLoop(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void staticCallOfNotFinalOverride(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.notFinalOverride.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void staticCallOfFinalOverride(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.finalOverride.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dynamicCallOfNotFinalOverride(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.notFinalOverrideAsSuperClass.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dynamicCallOfFinalOverride(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.finalOverrideAsSuperClass.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dynamicCallOfInterfaceImpl(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.interfaceImpl.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dynamicCallLambda(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.lambda.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void callOfFinalClass(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.finalClass.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dynamicCallSingleImpl(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.singleImplInterface.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void staticCallActualFinalClass(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            s.actualyFinalClass.produceIntValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflection(BenchmarkState s) {
        for (int i = 0; i < s.interrationCount; i++) {
            try {
                s.method.invoke(s.actualyFinalClass);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignore) {
            }
        }
    }
}
