package io.jocmer.exp.charts;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author merandj
 */
public class MatrixerTest {
    
    public MatrixerTest() {
    }

    @Test
    public void testToArrayJson() throws Exception {
        
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
        
        System.out.println(matrixer.toArrayJson());
        
    }
    
        @Test
    public void testToCsvString() throws Exception {
        
        Matrixer matrixer = new Matrixer("Count", "Benchmark", 0d);
        
        matrixer.set("5", "A", 584d, "ms");
        matrixer.set("5", "B", 585d, "ms");
        matrixer.set("5", "C", 586d, "ms");
        matrixer.set("5", "D", 587d, "ms");
        matrixer.set("5", "E", 588d, "ms");

        Assert.assertTrue(matrixer.isSingleDimension());
        
        System.out.println(matrixer.toCsvString());
        
    }
    
}
