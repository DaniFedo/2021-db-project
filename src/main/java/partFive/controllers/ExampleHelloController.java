package partFive.controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.example.Database;
import org.example.Table;
import partFive.dto.Response;
import partFive.models.User;
import partFive.views.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ExampleHelloController {
    private static View view;
    public static String PATH = "/user/";
    public static Response response = new Response();
    private static int counter;
    private static String password = "";
    private static int loginId = 1;
    private static String query = "";
    private static User user = new User();

    public static void setView(View newView) {
        view = newView;
    }

    public static int getLogin(String query){
        counter = 0;
        byte[] queryBytes = query.getBytes(StandardCharsets.UTF_8);

        while((char)queryBytes[counter] != '=') counter++;

        String check = "";
        loginId = 0;
        counter++;
        while((char) queryBytes[counter] != '&') {
            check += (char)queryBytes[counter];
            loginId = Integer.parseInt(check);
            counter++;
        }
        return loginId;
    }

    public static String getPassword(String query){
        password = "";
        byte[] queryBytes = query.getBytes(StandardCharsets.UTF_8);

        if(counter == 0)
            getLogin(query);


        while((char)queryBytes[counter] != '=') counter++;
        counter++;

        while(counter != queryBytes.length) {
            password += (char)queryBytes[counter];
            counter++;
        }

        counter = 0;
        return password;
    }

    private static void login(HttpExchange httpExchange) {
            System.out.println(httpExchange.getRequestURI());

             loginId = getLogin(query);
             user.setId(loginId);
             user.setProduct(null);
             password = getPassword(query);
             System.out.println("login is " + loginId + " password is " + password);


            Database.connect();

            try {
                String authToken = Table.lookForUniqToken
                        (loginId, password);
                System.out.println("user is found: " + authToken);
                response.setStatusCode(200);
                httpExchange.getResponseHeaders().add("Auth", authToken);
            }
            catch(Exception e){
                response.setStatusCode(401);
            }

            Database.close();
            //------
    }

    private static boolean authorize(String token){

        Database.connect();
        boolean result = Table.checkUniqToken(token);
        Database.close();
        if(result) return true;
        return false;

    }

    private static int getId(String token){
        Database.connect();
        int result = Table.getIdByToken(token);
        Database.close();
        return result;
    }

    private static int parseSubPathForId(String subPath){
        byte[] pathBytes = subPath.getBytes(StandardCharsets.UTF_8);
        int check = subPath.lastIndexOf('/');
        check++;
        String productIdTest = "";
        while(check != pathBytes.length) {
            productIdTest += (char)pathBytes[check];
            check++;
        }
        return Integer.parseInt(productIdTest);
    }

    public static void serve(HttpExchange httpExchange) throws IOException {
        String objectId = "";

        //secret is secretHeHe
        //password is testPassword
        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6InRlc3RQYXNzd29yZCIsImlhdCI6MTUxNjIzOTAyMn0.p1cdLX_lWk8TXb15EI3DD4lDsp6k7L_xeAdmqkXZk00

        //path - /user/...
        String path = httpExchange.getRequestURI().getPath();

        //subPath - йде після user/
        String subPath = path.substring(PATH.length());
        //---------------------------------------

        Headers headers = httpExchange.getRequestHeaders();
        query = httpExchange.getRequestURI().getQuery();
        String authToken = headers.getFirst("Auth");


        if(subPath.equals("login")) {
            login(httpExchange);
        }

        //System.out.println(subPath);
        System.out.println("header is " + authToken);
        if(subPath.contains("api/good/")) {
            if(authorize(authToken)){
                int id = parseSubPathForId(subPath);

                Database.connect();
                response.setStatusCode(200);
                user.setProduct(Table.showProductById(id));
                user.setId(getId(authToken));
                Database.close();
            }
            else{
                response.setStatusCode(403);
            }

        }


        //----------------------------------------




        response.setTemplate("view_user");
        response.setData(user);
        response.setHttpExchange(httpExchange);

        view.view(response);
    }
}
