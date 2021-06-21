package partFive;

import com.sun.net.httpserver.HttpContext;
import org.example.DBWorkspace;
import org.example.Database;
import org.example.Table;
import partFive.controllers.Controller;
import partFive.views.JsonView;
import partFive.views.View;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {

    final static int HTTP_SERVER_PORT = 8080;
    public static String TableName = "Users";

    final static View VIEW = new JsonView();

    //here is possible to add more customers and products to test through Postman
    private static void DBInitialization()
    {
        Database.connect();

        Table.create(TableName);
        Table.create(DBWorkspace.tableName);
        Table.addProduct("Watermelon");

        Table.addCustomer("fed3b61b26081849378080b34e693d2e",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6InRlc3RQYXNzd29yZCIsImlhdCI6MTUxNjIzOTAyMn0.p1cdLX_lWk8TXb15EI3DD4lDsp6k7L_xeAdmqkXZk00");

        Database.close();
    }

    public static void main(String[] args) {
        try {
            DBInitialization();
            Controller.setView(VIEW);

            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create();

            server.bind(new InetSocketAddress(HTTP_SERVER_PORT), 0);

            HttpContext context = server.createContext(Controller.PATH); // http://localhost:8888/hello
            context.setHandler(Controller::serve);

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}