package ajjsp.wrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.digiarea.es5.FunctionDeclaration;
import com.digiarea.es5.VariableDeclaration;
import com.digiarea.es5.parser.ASTParser;
import com.digiarea.es5.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vyacheslav on 17-Feb-17.
 */
class MainTest {
    @Test
    public void functionDeclarationTest() throws ParseException {

        String code = "function myFirstFunction(param1, param2){" +
                "alert('Hello, world!');" +
                "}";
        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ASTParser parser = new ASTParser(stream);
        FunctionDeclaration fun = parser.FunctionDeclaration();

        assertEquals("myFirstFunction", fun.getName(), "Name must be myFirstFunction");
        assertEquals("{\n" +
                "    alert('Hello, world!');\n" +
                "}", fun.getBody().toString(), "Body must be correct");
        assertEquals("param1", fun.getParameters().get(0).toString(), "1st parameter name should be param1");
        assertEquals("param2", fun.getParameters().get(1).toString(), "2nd parameter name should be param2");

    }

    @Test
    public void variableDeclarationTest() throws ParseException {

        String code = "a = 10+15";
        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ASTParser parser = new ASTParser(stream);
        VariableDeclaration var = parser.VariableDeclaration();

        assertEquals("a", var.getName(), "Name must be a");
        assertEquals("10 + 15", var.getExpression().toString(), "Expression must be 10+15");

    }
}