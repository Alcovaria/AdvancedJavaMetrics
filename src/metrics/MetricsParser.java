package metrics;

import com.digiarea.es5.*;
import com.digiarea.es5.parser.ASTParser;
import com.digiarea.es5.parser.ParseException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            return lines.length;
    }

    int linesOfComments(){
        return 0;
    }

    int numberOfGlobalVars(){
        int k = 0;
        for (Node aList : statementList) {
            if (aList instanceof VariableStatement)
                k++;
        }
        return k;
    }

    int numberOfVars() throws ParseException {
        int k = 0;
        for (Node aList : statementList) {
            if (aList instanceof VariableStatement)
                k++;
            if (aList instanceof FunctionDeclaration) {
                    List<String> list = new ArrayList<>(Arrays.asList(aList.toString().split("\r\n|\r|\n")));
                    list.remove(0);
                    StringBuilder sb = new StringBuilder();
                    for (String l : list)
                        if (l.length() > 2)
                            sb.append(l);
                    MetricsParser parser = new MetricsParser(sb.toString());
                    k += parser.numberOfVars();
                }
        }
        return k;
    }

    int numberOfFunctions() throws ParseException {
        int k = 0;
        for (Node aList : statementList) {
            if (aList instanceof FunctionDeclaration) {
                k++;
                List<String> list = new ArrayList<>(Arrays.asList(aList.toString().split("\r\n|\r|\n")));
                list.remove(0);
                StringBuilder sb = new StringBuilder();
                for (String l : list)
                    if (l.length() > 2)
                        sb.append(l);
                MetricsParser parser = new MetricsParser(sb.toString());
                k += parser.numberOfFunctions();
            }
        }
        return k;
    }

    int averageFunctionSize(){
        int k = 0;
        StringBuilder functions = new StringBuilder();
        for (Node aList : statementList) {
            if (aList instanceof FunctionDeclaration){
                k++;
                functions.append(aList);
            }
        }
        return (functions.toString().split("\r\n|\r|\n").length - k - 1) / k;
    }

    int numberOfLoops() throws ParseException {
        int k = 0;
        for (Node aList : statementList) {
            if (aList instanceof DoWhileStatement || aList instanceof ForStatement || aList instanceof WhileStatement ||
                    aList instanceof ForeachStatement)
                k++;
            if (aList instanceof FunctionDeclaration) {
                List<String> list = new ArrayList<>(Arrays.asList(aList.toString().split("\r\n|\r|\n")));
                list.remove(0);
                StringBuilder sb = new StringBuilder();
                for (String l : list)
                    if (l.length() > 2)
                        sb.append(l);
                MetricsParser parser = new MetricsParser(sb.toString());
                k += parser.numberOfLoops();
            }
        }
        return k;
    }

    /**
     * if-else statements
     */
    int numberOfConditions() throws ParseException {
        int k = 0;
        for (Node aList : statementList) {
            if (aList instanceof IfStatement)
                k++;
            if (aList instanceof FunctionDeclaration) {
                List<String> list = new ArrayList<>(Arrays.asList(aList.toString().split("\r\n|\r|\n")));
                list.remove(0);
                StringBuilder sb = new StringBuilder();
                for (String l : list)
                    if (l.length() > 2)
                        sb.append(l);
                MetricsParser parser = new MetricsParser(sb.toString());
                k += parser.numberOfConditions();
            }
        }
        return k;
    }

    // test
    public static void main(String[] args) throws ParseException {
        String code = "function myFirstFunction(param1, param2){" +
                "alert('Hello, world!');" +
                "a = 10+15" +
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
        for (Statement aList : parser.statementList) {
            if (aList instanceof FunctionDeclaration) {
                List<String> list = new ArrayList<>(Arrays.asList(aList.toString().split("\r\n|\r|\n")));
                list.remove(0);

                //String s [] = aList.toString().split("\r\n|\r|\n");
                StringBuilder s2 = new StringBuilder();
                for (String l : list){
                    if (l.length() > 2) {
                        s2.append(l);
                    }
                }
                MetricsParser parser2 = new MetricsParser(s2.toString());
                System.out.println();
                for (Node s : parser2.statementList) {
                    if (s instanceof ExpressionStatement){
                        System.out.println('+');
                        Expression ex = ((ExpressionStatement) s).getExpression();
                        System.out.println();
                    }
                }
            }
        }
    }


}