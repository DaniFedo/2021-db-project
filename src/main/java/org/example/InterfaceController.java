package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterfaceController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void addingButtonClicked(ActionEvent actionEvent) {
        System.out.println("kaboom");
    }

    public void editingButtonClicked(ActionEvent actionEvent) {

        System.out.println("Editkaboom");
    }
}
