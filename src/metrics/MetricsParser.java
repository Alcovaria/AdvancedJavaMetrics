package metrics;

import com.digiarea.es5.*;
import com.digiarea.es5.parser.ASTParser;
import com.digiarea.es5.parser.ParseException;
import javafx.beans.binding.ObjectExpression;

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

    //TODO: Parse recursively
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

    //Just for fun
    void printAllStatements(){
        for (Statement aList : statementList) {
            System.out.println(aList.getClass().toString());
        }
    }

    int linesOfCode(){
            String[] lines = code.split("\r\n|\r|\n");
            return lines.length;
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

    //TODO: Count variables for real
    public int numberOfVariables() {
        int k = 0;
        for (Statement aList : statementList) {
            if (aList instanceof VariableStatement)
                k++;
            if (aList instanceof ForStatement) //every loop + variable, lol
                k++;
        }
        return k;
    }

    //TODO: Maybe we can count AVERAGE number of methods?
    int numberOfMethods(){
        return 0;
    }

    //TODO: Maybe we can count AVERAGE number of attrs?
    int numberOfAttributes(){
        return 0;
    }

    //TODO: Maybe we can count AVERAGE function size?
    int functionSize(){
        return 0;
    }

    int numberOfLoops(){
        int k = 0;
        for (Statement aList : statementList) {
            if (aList instanceof ForStatement)
                k++;

        }
        return k;
    }

    /**
     * if-else statements
     */
    int numberOfConditions(){
        int k = 0;
        for (Statement aList : statementList) {
            if (aList instanceof IfStatement)
                k++;
        }
        return k;
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