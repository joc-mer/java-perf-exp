package io.jocmer.exp.collections;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import static org.openjdk.jmh.annotations.Scope.Benchmark;
import org.openjdk.jmh.annotations.Warmup;

/**
 *
 * @author joc
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class EqualsBenchmark {

    public static class GeneratedEquals {

        public String nom;

        public int age;

        public String adresse;

        public GeneratedEquals(String nom, int age, String adresse) {
            this.nom = nom;
            this.age = age;
            this.adresse = adresse;
        }

        @Override
        public int hashCode() {
            int hash = 3;
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
            final GeneratedEquals other = (GeneratedEquals) obj;
            if (this.age != other.age) {
                return false;
            }
            if (!Objects.equals(this.nom, other.nom)) {
                return false;
            }
            if (!Objects.equals(this.adresse, other.adresse)) {
                return false;
            }
            return true;
        }

    }

    public static class ReflectionEquals {

        public String nom;

        public int age;

        public String adresse;

        public ReflectionEquals(String nom, int age, String adresse) {
            this.nom = nom;
            this.age = age;
            this.adresse = adresse;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

    }

    public static class ObjectsEquals {

        public String nom;

        public int age;

        public String adresse;

        public ObjectsEquals(String nom, int age, String adresse) {
            this.nom = nom;
            this.age = age;
            this.adresse = adresse;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(this, obj);
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS
    )
    public void generatedEquals() {
        GeneratedEquals obj1 = new GeneratedEquals("A", 5678, "rue des cedres");
        GeneratedEquals obj2 = new GeneratedEquals("B", 599077, "avenue de la gare");

        for (int i = 0; i < 100000; i++) {
            obj1.equals(obj2);
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflectionEquals() {
        ReflectionEquals obj1 = new ReflectionEquals("A", 5678, "rue des cedres");
        ReflectionEquals obj2 = new ReflectionEquals("B", 599077, "avenue de la gare");

        for (int i = 0; i < 100000; i++) {
            obj1.equals(obj2);
        }

    }

//    @Benchmark
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public void objectsEquals() {
//        ObjectsEquals obj1 = new ObjectsEquals("A", 5678, "rue des cedres");
//        ObjectsEquals obj2 = new ObjectsEquals("B", 599077, "avenue de la gare");
//
//        for (int i = 0; i < 100000; i++) {
//            obj1.equals(obj2);
//        }
//
//    }

}
