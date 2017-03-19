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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    int numberOfComments(){
        Pattern pattern = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)");
        Matcher matcher = pattern.matcher(code);

        int count = 0;
        while (matcher.find())
            count++;

        return count;
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
        if(k==0) return 0;
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
}