package io.jocmer.exp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author merandj
 */
public class LauncherWithFormatedResultsTest {
    
    public LauncherWithFormatedResultsTest() {
    }

    @Test
    public void testMain() throws Exception {
        
        LauncherWithFormatedResults.Matrixer matrixer = new LauncherWithFormatedResults.Matrixer("Count", "Benchmark", 0d);
        
        matrixer.set("5", "A", 584d);
        matrixer.set("5", "B", 585d);
        matrixer.set("5", "C", 586d);
        matrixer.set("5", "D", 587d);
        matrixer.set("5", "E", 588d);
        matrixer.set("20", "A", 3434d);
        matrixer.set("20", "B", 3435d);
        matrixer.set("20", "C", 3436d);
        matrixer.set("20", "D", 3437d);
        matrixer.set("20", "E", 3438d);
        
        System.out.println(matrixer.toJson());
        
    }
    
}
