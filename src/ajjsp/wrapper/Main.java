package ajjsp.wrapper;

import com.digiarea.es5.FunctionDeclaration;
import com.digiarea.es5.Node;
import com.digiarea.es5.VariableDeclaration;
import com.digiarea.es5.parser.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String code = "a = 10; b = 15;";

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

        ASTParser parser = new ASTParser(stream);

        List<VariableDeclaration> var = parser.VariableDeclaratorList();
        for (VariableDeclaration aVar : var) {
            System.out.println(aVar.toString());

        }

    }
}