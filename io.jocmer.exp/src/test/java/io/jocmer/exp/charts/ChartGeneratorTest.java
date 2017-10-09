package io.jocmer.exp.charts;

import io.jocmer.exp.ChartInfo;
import org.junit.Test;

/**
 *
 * @author joc
 */
public class ChartGeneratorTest {

    public ChartGeneratorTest() {
    }

    @Test
    public void testGenerateFromMatrix() throws Exception {
        Matrixer matrixer = new Matrixer("Count", "Benchmark", 0d);

        matrixer.set("5", "A", 584d, "ms");
        matrixer.set("5", "B", 585d, "ms");
        matrixer.set("5", "C", 586d, "ms");
        matrixer.set("5", "D", 587d, "ms");
        matrixer.set("5", "E", 588d, "ms");
        matrixer.set("20", "A", 3434d, "ms");
        matrixer.set("20", "B", 3435d, "ms");
        matrixer.set("20", "C", 3436d, "ms");
        matrixer.set("20", "D", 3437d, "ms");
        matrixer.set("20", "E", 3438d, "ms");

        ChartGenerator generator = new ChartGenerator("/barchart/barchartTemplate.html");

        generator.generateFromMatrix(new ChartInfo(matrixer, null), "test/test.html");
    }

}
