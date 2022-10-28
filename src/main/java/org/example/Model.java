package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private String title;
    private String description;
    private String manufacturer;
    private double price;
    private String productGroup;
    private double amount;
    public static double fullPrice;

    public static ObservableList<Model> outputDataOfModels = FXCollections.observableArrayList();

    public Model(String title, String description, String manufacturer, Double price,
                 String productGroup, Double amount) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productGroup = productGroup;
        this.amount = amount;
    }

    public Model(String title, Double price, Double amount) {
        this.title = title;
        this.price = price;
        this.amount = amount;
    }

    public Model() {
    }

    public double getPrice() {
        return price;
    }

    public double getAmount() {
        return amount;
    }

}

