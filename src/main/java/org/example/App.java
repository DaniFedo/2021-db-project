package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

/**  showAllButtonClicked
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    public static StoreClientTCP client1;

    @Override
    public void start(Stage stage) throws IOException {
        Database.connect();
        Table.create(DBWorkspace.tableName);
        Table.create(DBWorkspace.productTableName);
        Database.close();
        client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);
        scene = new Scene(loadFXML("interface"));
        stage.setScene(scene);
        stage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}