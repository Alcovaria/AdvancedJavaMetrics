package metrics;

import com.digiarea.es5.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Anastasia on 18.02.2017.
 */
class MetricsParserTests {

    private static MetricsParser parser;

    @BeforeAll
    static void setUp() {

        Path filePath = Paths.get("MetricsParserTests.js");

        InputStream stream = null;

        try {
            stream = new ByteArrayInputStream(Files.readAllBytes(filePath));
            parser = new MetricsParser(stream);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        parser.printAllStatements();
    }

    @Test
    void linesOfCode() {
        assertEquals(25, parser.linesOfCode(), "Wrong number of lines of code.");
    }

    @Test
    void linesOfComments() {
        assertEquals(0, parser.linesOfComments(), "Wrong number of lines of comments.");
    }

    @Test
    void numberOfFunctions() throws ParseException {
        assertEquals(3, parser.numberOfFunctions(), "Wrong number of functions.");
    }

    @Test
    void numberOfObjects() {
        assertEquals(2, parser.numberOfObjects(), "Wrong number of objects.");
    }

    @Test
    void numberOfVariables() {
        assertEquals(4, parser.numberOfVariables(), "Wrong number of objects.");
    }


    @Test
    void numberOfMethods() {
        assertEquals(0, parser.numberOfMethods(), "Wrong number of methods.");
    }

    @Test
    void numberOfAttributes() {
        assertEquals(0, parser.numberOfAttributes(), "Wrong number of attributes.");
    }

    @Test
    void functionSize() {
        assertEquals(0, parser.functionSize(), "Wrong function size.");
    }

    @Test
    void numberOfLoops() {
        assertEquals(1, parser.numberOfLoops(), "Wrong number of loops.");
    }

    @Test
    void numberOfConditions() {
        assertEquals(1, parser.numberOfConditions(), "Wrong number of conditions");
    }

    @Test
    void numberOfOperators() {
        assertEquals(0, parser.numberOfOperators(), "Wrong number of operators");
    }

    @Test
    void numberOfExceptions() {
        assertEquals(0, parser.numberOfExceptions(), "Wrong number of exceptions");
    }
}