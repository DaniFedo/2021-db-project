package partFive;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.example.DBWorkspace;
import org.example.Database;
import org.example.Table;
import partFive.controllers.ExampleHelloController;
import partFive.views.JsonView;
import partFive.views.View;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerOneMethod {

    final static int HTTP_SERVER_PORT = 8080;
    public static String TableName = "Users";

    final static View VIEW = new JsonView();
    private static void DBInitialization()
    {
        Database.connect();

        Table.create(TableName);
        Table.create(DBWorkspace.tableName);
        Table.addProduct("Watermelon");


       /* Table.addCustomer("fed3b61b26081849378080b34e693d2e",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6InRlc3RQYXNzd29yZCIsImlhdCI6MTUxNjIzOTAyMn0.p1cdLX_lWk8TXb15EI3DD4lDsp6k7L_xeAdmqkXZk00");
*/
        Database.close();
    }

    public static void main(String[] args) {
        try {
            DBInitialization();
            ExampleHelloController.setView(VIEW);

            HttpServer server = HttpServer.create();

            server.bind(new InetSocketAddress(HTTP_SERVER_PORT), 0);

            HttpContext context = server.createContext(ExampleHelloController.PATH); // http://localhost:8888/hello
            context.setHandler(ExampleHelloController::serve);

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}