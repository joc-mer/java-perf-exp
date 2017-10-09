package io.jocmer.exp;

import io.jocmer.exp.charts.Matrixer;
import org.openjdk.jmh.infra.BenchmarkParams;

/**
 *
 * @author joc
 */
public class ChartInfo {
    
    final Matrixer matrixer;
    String formatedResult;

    public ChartInfo(Matrixer matrixer, BenchmarkParams params) {
        this.matrixer = matrixer;
    }

    public String getFormatedResult() {
        return formatedResult;
    }

    public void setFormatedResult(String formatedResult) {
        this.formatedResult = formatedResult;
    }

    public Matrixer getMatrixer() {
        return matrixer;
    }
    
}
