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
    public Model(String title, Double price, Double amount)
    {
        this.title = title;
        this.price = price;
        this.amount = amount;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductGroup(){
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

        public double getAmount() {
            return amount;
        }
        public void setAmount(double amount) {
            this.price = amount;
        }

}

