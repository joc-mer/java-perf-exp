package io.jocmer.exp.charts;

import io.jocmer.exp.ChartInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author joc
 */
public class ChartGenerator {

    final String templateName;

    public ChartGenerator(String templateName) {
        this.templateName = templateName;
    }

    private void createDirRecursive(String path) throws IOException {
        String[] split = path.split("/");

        String acc = "";

        for (int i = 0; i < split.length; i++) {
            String file = acc.isEmpty() ? split[i] : acc + "/" + split[i];
            File f = new File(file);

            if (!f.exists()) {
                if (i < split.length - 1) {
                    f.mkdir();
                } else {
                    f.createNewFile();
                }
            }
            acc = file;
        }
    }

    public void generateFromMatrix(ChartInfo info, String outputName) throws IOException {
        InputStream stream = ChartGenerator.class.getResourceAsStream(templateName);

        createDirRecursive(outputName);

        File outputFile = new File(outputName);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(new File(outputName));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String str = reader.readLine();
            while (str != null) {
                if (str.contains(CSV_FLIPPED)) {
                    int index = str.indexOf(CSV_FLIPPED);
                    String csvString = info.getMatrixer().toFlippedCsvString();
                    substituteCSV(writer, str, index, info, csvString);
                    writer.append(str.substring(index + CSV_FLIPPED.length(), str.length()));
                } else if (str.contains(CSV_TAG)) {
                    int index = str.indexOf(CSV_TAG);
                    String csvString = info.getMatrixer().toCsvString();
                    substituteCSV(writer, str, index, info, csvString);
                    writer.append(str.substring(index + CSV_TAG.length(), str.length()));
                } else if (str.contains(SCALE_TAG)) {
                    writer.append(str.replace(SCALE_TAG, info.getMatrixer().getScale()));
                } else if (str.contains(RAW_OUTPUT)) {
                    for (String rawLine : info.getFormatedResult().split("\n")) {
                        writer.append(rawLine).append("<br/>");
                    }
                } else {
                    writer.append(str).append("\n");
                }
                str = reader.readLine();
            }
        } finally {
            writer.close();
        }
    }

    private void substituteCSV(BufferedWriter writer, String str, int index, ChartInfo info, String csvString) throws IOException {
        writer.append(str.substring(0, index));
        boolean first = true;
        for (String csvLine : csvString.split("\n")) {
            if (first) {
                first = false;
            } else {
                writer.append("\" + \n\t");
            }
            writer.append("\"").append(csvLine).append("\\n");
        }
        writer.append("\"");
    }
    private static final String CSV_TAG = "###CSV###";
    private static final String CSV_FLIPPED = "###CSV_FLIPPED###";
    private static final String SCALE_TAG = "###SCALE###";
    private static final String RAW_OUTPUT = "###RAW_OUTPUT###";
}
