package metrics;

import com.digiarea.es5.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by Anastasia on 09.03.2017.
 */
public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        /*
         * reading JS code snippet
         */

        //String fileName = args[0];
        String fileName = "code.js";
        InputStream code = null;

        Path filePath = Paths.get("code.js");

        InputStream stream = null;
        try {
            stream = new ByteArrayInputStream(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Metrics
         */



        MetricsParser parser = new MetricsParser(stream);

        System.out.println(parser.numberOfFunctions());
        System.out.println((stream));

        /*
         * Writing metrics to csv file
         */
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("metrics.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("metric one");
        sb.append(';');
        sb.append("metric two");
        sb.append('\n');

        sb.append("+");
        sb.append(';');
        sb.append("-");
        sb.append('\n');

        writer.write(sb.toString());
        writer.close();
    }
}
