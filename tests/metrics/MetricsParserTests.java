package metrics;

import com.digiarea.es5.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Anastasia on 18.02.2017.
 */
class MetricsParserTests {

    private static MetricsParser parser;

    @BeforeAll
    static void setUp() {
        String code = "function fun1(arg1, arg2){\n" +
                "\talert('Me is function1');\n" +
                "}\n" +
                "\n" +
                "function fun2(arg1, arg2){\n" +
                "\talert('Me is function2');\n" +
                "}\n" +
                "\n" +
                "function fun3(arg1, arg2){\n" +
                "\talert('Me is function3');\n" +
                "}";
        try {
            parser = new MetricsParser(code);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void linesOfCode() {
        assertEquals(11, parser.linesOfCode(), "Wrong number of lines of code.");
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
        assertEquals(0, parser.numberOfObjects(), "Wrong number of objects.");
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
        assertEquals(0, parser.numberOfLoops(), "Wrong number of loops.");
    }

    @Test
    void numberOfConditions() {
        assertEquals(0, parser.numberOfConditions(), "Wrong number of conditions");
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