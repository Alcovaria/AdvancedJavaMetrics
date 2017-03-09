package metrics;

import com.digiarea.es5.Comment;
import com.digiarea.es5.FunctionDeclaration;
import com.digiarea.es5.Statement;
import com.digiarea.es5.parser.ASTParser;
import com.digiarea.es5.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Anastasia on 18.02.2017.
 */

class MetricsParser {

    private ASTParser parser;
    private List<Statement> statementList;
    private String code;

    MetricsParser(String code) throws ParseException {
        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        parser = new ASTParser(stream);
        statementList = parser.StatementList();
        this.code = code;
    }

    MetricsParser(InputStream stream) throws ParseException, IOException {
        parser = new ASTParser(stream);
        statementList = parser.StatementList();
        stream.reset();
        int n = stream.available();
        byte[] bytes = new byte[n];
        stream.read(bytes, 0, n);
        this.code = new String(bytes, StandardCharsets.UTF_8);
    }

    int linesOfCode(){
            String[] lines = code.split("\r\n|\r|\n");
            return  lines.length;
    }

    int linesOfComments(){
        return 0;
    }

    int numberOfFunctions() throws ParseException {
        int k = 0;
        for (Statement aList : statementList) {
            if (aList instanceof FunctionDeclaration)
                k++;
        }
        return k;
    }

    int numberOfObjects(){
        return 0;
    }

    int numberOfMethods(){
        return 0;
    }

    int numberOfAttributes(){
        return 0;
    }

    int functionSize(){
        return 0;
    }

    int numberOfLoops(){
        return 0;
    }

    /**
     * if-else statements
     */
    int numberOfConditions(){
        return 0;
    }

    int numberOfOperators(){
        return 0;
    }

    int numberOfExceptions(){
        return 0;
    }

    // test
    public static void main(String[] args) throws ParseException {
        String code = "function myFirstFunction(param1, param2){" +
                "alert('Hello, world!');" +
                "}" +
                "function mySecondFunction(param3, param4){" +
                "alert('Hello, world!!!');" +
                "}" +
                "while(42 == 42){" +
                "alert('We are doomed')" +
                "}" +
                "a = 10+15";
        MetricsParser parser = new MetricsParser(code);
        System.out.println(parser.numberOfFunctions());
    }
}