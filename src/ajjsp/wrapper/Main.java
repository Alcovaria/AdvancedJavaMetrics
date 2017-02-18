package ajjsp.wrapper;

import com.digiarea.es5.*;
import com.digiarea.es5.parser.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String code = "for (var prop in obj) {\n" +
                "   console.log('obj.' + prop, '=', obj[prop]);\n" +
                "}";
        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ASTParser parser = new ASTParser(stream);
        ForeachStatement forins = parser.ForInVarStatement();
        System.out.println(forins.getVariable().toString());
    }
}