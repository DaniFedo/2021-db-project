package partFive.controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.example.Database;
import org.example.Table;
import org.json.JSONObject;
import partFive.dto.Response;
import partFive.models.Model;
import partFive.views.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Controller {
    public static String PATH = "/user/";
    public static Response response = new Response();

    private static View view;
    private static int counter;
    private static String password = "";
    private static int loginId = 1;
    private static String query = "";
    private static Model model = new Model();
    private static String subPath;

    public static void setView(View newView) {
        view = newView;
    }

    public static int getLogin(String query){
        counter = 0;
        byte[] queryBytes = query.getBytes(StandardCharsets.UTF_8);
        if(query.contains("login")) {

            while ((char) queryBytes[counter] != '=') counter++;

            String check = "";
            loginId = 0;
            counter++;

            if(!query.contains("&")){
                return 0;
            }

            while ((char) queryBytes[counter] != '&') {
                check += (char) queryBytes[counter];
                loginId = Integer.parseInt(check);
                counter++;
            }
            return loginId;
        }

        else return 0;

    }

    public static String getPassword(String query){
        password = "";
        byte[] queryBytes = query.getBytes(StandardCharsets.UTF_8);
        if(query.contains("password")) {

            if (counter == 0)  getLogin(query);

            while ((char) queryBytes[counter] != '=') counter++;
            counter++;

            while (counter != queryBytes.length) {
                password += (char) queryBytes[counter];
                counter++;
            }

            counter = 0;
            return password;
        }
        else return null;

    }

    private static void login(HttpExchange httpExchange) {
            if(query.contains("login") && query.contains("password")) {

                loginId = getLogin(query);
                model.setId(loginId);
                model.setMessage("Authorized for user with id: " + loginId);
                password = getPassword(query);

                Database.connect();

                try {
                    String authToken = Table.lookForUniqToken
                            (loginId, password);
                    response.setStatusCode(200);
                    httpExchange.getResponseHeaders().add("Auth", authToken);
                } catch (Exception e) {
                    model.setId(null);
                    model.setMessage("Unauthorized");
                    response.setStatusCode(401);
                }

                Database.close();
            }
            else
            {
                model.setId(null);
                model.setMessage("Unauthorized");
                response.setStatusCode(401);
            }
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
        try {
            byte[] pathBytes = subPath.getBytes(StandardCharsets.UTF_8);
            int check = subPath.lastIndexOf('/');
            check++;
            String productIdTest = "";
            while (check != pathBytes.length) {
                productIdTest += (char) pathBytes[check];
                check++;
            }
            return Integer.parseInt(productIdTest);
        }
        catch(Exception e)
        {
            response.setStatusCode(409);
            return 0;
        }
    }

    private static void addingNewProduct(HttpExchange httpExchange) throws IOException {
        String[] wrongInput = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String jsonString = getTitle(httpExchange);

        if(jsonString == ""){
            model.setMessage("Wrong JSON");
            response.setStatusCode(400);
            return;
        }

        JSONObject json = new JSONObject(jsonString);

        String title = json.getString("title");

        if(Arrays.stream(wrongInput).anyMatch(title::contains) || title.isEmpty())
            response.setStatusCode(409);
        else if(httpExchange.getRequestMethod().equals("PUT")){

            Database.connect();
            //model.setMessage(Table.addProduct(title));
            model.setId(null);
            Database.close();
            response.setStatusCode(201);
        }
    }

    private static boolean checkId(int id){
        Database.connect();
        int maxId = Table.maxID();
        Database.close();

        if(id < 1)
        {
            response.setStatusCode(409);
            model.setMessage("Invalid id");
            return false;
        }
        else if(id > maxId)
        {
            response.setStatusCode(404);
            model.setMessage("Invalid id");
            return false;
        }
        return true;
    }

    private static void updatingExistingProduct(HttpExchange httpExchange) throws IOException {

        String jsonString = getTitle(httpExchange);
        if(jsonString == ""){
            response.setStatusCode(400);
            model.setMessage("Wrong json");
            return;
        }
        JSONObject json = new JSONObject(jsonString);
        String title = json.getString("title");
        int id = parseSubPathForId(subPath);

        if(checkId(id)){
            Database.connect();
            if(Table.checkWhetherAlive(id)) {

                //Table.updateProduct(id, title);
                Database.close();
                model.setMessage("Updated product with id = " + id + " on new title = " + title);
                //smth wrong with 204, so we use 202 instead
                response.setStatusCode(202);
            }
            else
            {
                model.setMessage("Haven't found this product");
                response.setStatusCode(404);
                Database.close();
            }

        }
        model.setId(null);

    }

    private static void deleteExistingProduct() {
        int id = parseSubPathForId(subPath);
        if(checkId(id)) {
                Database.connect();
            if(Table.checkWhetherAlive(id)) {
                Table.deleteProduct(id);
                Database.close();
                model.setMessage("Deleted product with id " + id);
                //smth wrong with 204, so we use 202 instead
                response.setStatusCode(202);
            }
            else{
                model.setMessage("Haven't found this product");
                response.setStatusCode(404);
                Database.close();
            }
        }
    }

    private static String getTitle(HttpExchange httpExchange) throws IOException
    {
            int reading = httpExchange.getRequestBody().read();
            String result = "";
            while (reading != -1) {
                result += (char) reading;
                reading = httpExchange.getRequestBody().read();
            }
            return result;
    }

    public static void serve(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();

        subPath = path.substring(PATH.length());

        Headers headers = httpExchange.getRequestHeaders();
        query = httpExchange.getRequestURI().getQuery();
        String authToken = headers.getFirst("Auth");


        if(subPath.equals("login")) {

            if((httpExchange.getRequestMethod().equals("POST")))
            {

                if(query != null) {
                    login(httpExchange);
                }
                else{
                    model.setMessage("Wrong params");
                    response.setStatusCode(400);
                }
            }
            else
            {
                model.setId(null);
                model.setMessage("Unauthorized - wrong command");
                response.setStatusCode(401);
            }
        }

        else {
            if (authorize(authToken)) {
                if (subPath.contains("api/good/")) {
                    int id = parseSubPathForId(subPath);
                    if(httpExchange.getRequestMethod().equals("GET")) {

                            Database.connect();
                            response.setStatusCode(200);
                            //model.setMessage(Table.showProduct(id));
                            Database.close();
                            model.setId(getId(authToken));
                    }
                    else if(httpExchange.getRequestMethod().equals("POST")) {
                        updatingExistingProduct(httpExchange);
                    }
                    else if (httpExchange.getRequestMethod().equals("DELETE")){
                        deleteExistingProduct();
                    }
                    else
                    {
                        response.setStatusCode(400);
                        model.setMessage("Wrong command");
                        model.setId(null);
                    }

                }
                else if (subPath.contains("api/good")) {
                    if(httpExchange.getRequestMethod().equals("PUT"))
                        addingNewProduct(httpExchange);
                    else{
                        response.setStatusCode(400);
                        model.setMessage("Wrong command");
                        model.setId(null);
                    }
                }
            }
            else {
                response.setStatusCode(403);
                model.setId(null);
                model.setMessage("Authorization failed");
            }
        }

        response.setTemplate("view_user");
        response.setData(model);
        response.setHttpExchange(httpExchange);

        view.view(response);
    }
}
