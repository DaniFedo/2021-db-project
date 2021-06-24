package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class InterfaceController {

    //-----------------------------buttons--------------------------
    @FXML
    private Button addingGroupSubmitButton;

    @FXML
    private Button closeGroup;

    @FXML
    private Button updateProductSubmitButton;

    @FXML
    private Button showProductSubmitButton;

    @FXML
    private Button addingSubmitButton;

    @FXML
    private Button addingGroupButton;

    @FXML
    private Button showAllSubmitButton;

    @FXML
    private Button updateGroupSubmitButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button deleteGroupSubmitButton;

    @FXML
    private Button fullPriceSubmitButton;

    @FXML
    private Button amountSubmitButton;
















    //---------------------------text fields--------------------------
    @FXML
    private TextField titleInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField manufacturerInput;

    @FXML
    private TextField productGroupInput;

    @FXML
    private TextField amountInput;

    @FXML
    private TextField inputGroupName;

    @FXML
    private TextField inputGroupDescription;

    @FXML
    private TextField showAllGroupName;

    @FXML
    private TextField newTitleInput;

    @FXML
    private TextField inputNewGroupName;

    @FXML
    private TextField updateAmountInput;















    //----------------------------anchors----------------------
    @FXML
    private AnchorPane productMainAnchor;

    @FXML
    private AnchorPane addingAnchor;

    @FXML
    private AnchorPane buttonAnchor;

    @FXML
    private AnchorPane addingGroupAnchor;

    @FXML
    private AnchorPane buttonGroupsAnchor;

    @FXML
    private AnchorPane showAllAnchor;



    //---------------------------other------------------------
    private static int commandType;

    @FXML
    private Label amountTitle;

    @FXML
    private Label newTitleLabel;

    @FXML
    private Label newGroupTitleLabel;

    @FXML
    private Label deleteProductLabel;

    @FXML
    private Label showAllLabel;

    @FXML
    private Label groupDescriptionLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private TableColumn <Model, String> titleColumn;

    @FXML
    private TableColumn <Model, String> descriptionColumn;

    @FXML
    private TableColumn <Model, String> manufacturerColumn;

    @FXML
    private TableColumn <Model, Double> priceColumn;

    @FXML
    private TableColumn <Model, String> groupColumn;

    @FXML
    private TableColumn <Model, Double> amountColumn;

    @FXML
    private TableView <Model> tableViewShow;

    @FXML
    private TableColumn <Model, String> titleColumn1;

    @FXML
    private TableColumn <Model, String> descriptionColumn1;

    @FXML
    private TableColumn <Model, String> manufacturerColumn1;

    @FXML
    private TableColumn <Model, Double> priceColumn1;

    @FXML
    private TableColumn <Model, String> groupColumn1;

    @FXML
    private TableColumn <Model, Double> amountColumn1;

    @FXML
    private TableView <Model> tableViewShow1;
    @FXML
    private TableColumn <Model, String> titleColumn11;


    @FXML
    private TableColumn <Model, Double> priceColumn11;

    @FXML
    private TableColumn <Model, Double> amountColumn11;

    @FXML
    private TableView <Model> tableViewShow11;

    public static ObservableList<Model> outputData = FXCollections.observableArrayList();



    //tableViewShow









    //--------------------------functions-----------------------


    //---------------------------Products-----------------------
    //------------------------main buttons----------------------
    @FXML
    public void addingButtonClicked() {
        buttonAnchor.setVisible(false);
        addingAnchor.setVisible(true);
        addingSubmitButton.setVisible(true);
        showProductSubmitButton.setVisible(false);

    }

    @FXML
    public void editingButtonClicked() {

        buttonAnchor.setVisible(false);
        addingAnchor.setVisible(true);
        addingSubmitButton.setVisible(false);
        updateProductSubmitButton.setVisible(true);
        newTitleInput.setVisible(true);
        newTitleLabel.setVisible(true);
    }

    @FXML
    public void deletingButtonClicked() {
        buttonAnchor.setVisible(false);
        showAllAnchor.setVisible(true);
        showAllSubmitButton.setVisible(false);
        deleteProductButton.setVisible(true);
        deleteProductLabel.setVisible(true);
        showAllLabel.setVisible(false);
    }
    @FXML
    public void showButtonClicked() {
        buttonAnchor.setVisible(false);
        addingAnchor.setVisible(true);
        addingSubmitButton.setVisible(false);
        showProductSubmitButton.setVisible(true);
        amountTitle.setVisible(true);
        amountInput.setVisible(true);
        showAllSubmitButton.setVisible(false);
        titleColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("description"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("manufacturer"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Model, Double>("price"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("productGroup"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Model, Double>("amount"));
    }

    @FXML
    public void showAllButtonClicked() {
        buttonAnchor.setVisible(false);
        showAllAnchor.setVisible(true);
        titleColumn1.setCellValueFactory(new PropertyValueFactory<Model, String>("title"));
        descriptionColumn1.setCellValueFactory(new PropertyValueFactory<Model, String>("description"));
        manufacturerColumn1.setCellValueFactory(new PropertyValueFactory<Model, String>("manufacturer"));
        priceColumn1.setCellValueFactory(new PropertyValueFactory<Model, Double>("price"));
        groupColumn1.setCellValueFactory(new PropertyValueFactory<Model, String>("productGroup"));
        amountColumn1.setCellValueFactory(new PropertyValueFactory<Model, Double>("amount"));
    }

    @FXML
    public void fullPriceButtonClicked() {
        buttonAnchor.setVisible(false);
        showAllAnchor.setVisible(true);
        showAllSubmitButton.setVisible(false);
        fullPriceSubmitButton.setVisible(true);
        tableViewShow11.setVisible(false);
        titleColumn11.setCellValueFactory(new PropertyValueFactory<Model, String>("title"));
        priceColumn11.setCellValueFactory(new PropertyValueFactory<Model, Double>("price"));
        amountColumn11.setCellValueFactory(new PropertyValueFactory<Model, Double>("amount"));

    }

    @FXML
    public void addAmountButtonClicked() {
        buttonAnchor.setVisible(false);
        showAllAnchor.setVisible(true);
        showAllSubmitButton.setVisible(false);
        amountSubmitButton.setVisible(true);
        updateAmountInput.setVisible(true);
        amountLabel.setVisible(true);
        showAllLabel.setText("Product name:");
    }

    //-------------------------adding group---------------------
    @FXML
    public void addingSubmitButtonClicked() {
        commandType = 5;
        String result = titleInput.getText() + "," + descriptionInput.getText()
                + "," + manufacturerInput.getText() + "," + priceInput.getText() + "," + productGroupInput.getText();
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        addingAnchor.setVisible(false);
        buttonAnchor.setVisible(true);
    }

    @FXML
    public void closeButtonClicked() {
        outputData.clear();
        addingAnchor.setVisible(false);
        buttonAnchor.setVisible(true);
        showProductSubmitButton.setVisible(false);
        amountTitle.setVisible(false);
        amountInput.setVisible(false);
        updateProductSubmitButton.setVisible(false);
        newTitleInput.setVisible(false);
        newTitleLabel.setVisible(false);
        tableViewShow.setVisible(false);
        tableViewShow1.setVisible(false);
        tableViewShow11.setVisible(false);
        showAllSubmitButton.setVisible(true);
    }

    //--------------------------showing product------------------
    @FXML
    public void showProductSubmitButtonClicked() {
        commandType = 9;
        String price= priceInput.getText();
        String amount= amountInput.getText();
        if(price.equals(""))
            price = "0";
        //resultView
        if(amount.equals(""))
            amount = "-1";

        String result = titleInput.getText() + "," + descriptionInput.getText()
                + "," + manufacturerInput.getText() + "," + price + ","
                + productGroupInput.getText() + "," + amount;
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        //---------------------------------------------

        showProductSubmitButton.setVisible(false);
        tableViewShow.setItems(outputData);
        tableViewShow.setVisible(true);
        //----------------------------------------------
        //addingAnchor.setVisible(false);
        //buttonAnchor.setVisible(true);
//        addingSubmitButton.setVisible(false);
//        amountTitle.setVisible(false);
//        amountInput.setVisible(false);

    }


    //---------------------------show all------------------------
    @FXML
    public void showAllSubmitButtonClicked() {
        commandType = 10;
        String groupName = showAllGroupName.getText();

        String result = groupName;
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        tableViewShow1.setItems(outputData);
        tableViewShow1.setVisible(true);
        showAllSubmitButton.setVisible(false);


        /*showAllAnchor.setVisible(false);
        buttonAnchor.setVisible(true);*/
    }

    @FXML
    public void closeButtonShowAllClicked() {
        showAllAnchor.setVisible(false);
        buttonAnchor.setVisible(true);
        showAllSubmitButton.setVisible(true);
        deleteProductButton.setVisible(false);
        deleteProductLabel.setVisible(false);
        showAllLabel.setVisible(true);
        fullPriceSubmitButton.setVisible(false);
        amountSubmitButton.setVisible(false);
        updateAmountInput.setVisible(false);
        amountLabel.setVisible(false);
        tableViewShow.setVisible(false);
        tableViewShow1.setVisible(false);
        tableViewShow11.setVisible(false);
        outputData.clear();

        showAllLabel.setText("Group name:");
    }

    //-----------------------------update------------------------
    @FXML
    public void updateProductSubmitButtonClicked() {
        commandType = 17;
        String result = titleInput.getText() + "," + newTitleInput.getText() +
                "," + descriptionInput.getText() + "," + manufacturerInput.getText() +
                "," + priceInput.getText() + "," + productGroupInput.getText();
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        newTitleInput.setVisible(false);
        newTitleLabel.setVisible(false);
        addingAnchor.setVisible(false);
        buttonAnchor.setVisible(true);

    }

    //-------------------------delete----------------------------
    @FXML
    public void deleteProductButtonClicked() {
        commandType = 33;
        String productName = showAllGroupName.getText();

        String result = productName;
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        showAllSubmitButton.setVisible(true);
        deleteProductButton.setVisible(false);
        deleteProductLabel.setVisible(false);
        showAllLabel.setVisible(true);
        showAllAnchor.setVisible(false);
        buttonAnchor.setVisible(true);

    }



    //------------------------full price-------------------------
    @FXML
    public void fullPriceSubmitButtonClicked() {
        Model.fullPrice = 0;
        commandType = 65;
        String groupName = showAllGroupName.getText();

        String result = groupName;
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        for(int i = 0; i < outputData.size(); i++)
        {
            Model forTry = outputData.get(i);
            Model.fullPrice += outputData.get(i).getPrice() * outputData.get(i).getAmount();
            System.out.println(forTry.getAmount());


        }
        Model model = new Model("Overall:", 0.0, Model.fullPrice);
        outputData.add(model);
        tableViewShow11.setItems(outputData);
        tableViewShow11.setVisible(true);
        /*showAllAnchor.setVisible(false);
        buttonAnchor.setVisible(true);*/
        fullPriceSubmitButton.setVisible(false);

    }


    //-----------------------------updating amount----------------
    @FXML
    public void amountSubmitButtonClicked() {

        commandType = 66;
        String groupName = showAllGroupName.getText() + "," + updateAmountInput.getText();

        String result = groupName;
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();

        showAllAnchor.setVisible(false);
        buttonAnchor.setVisible(true);
        amountSubmitButton.setVisible(false);
        updateAmountInput.setVisible(false);
        amountLabel.setVisible(false);

        showAllLabel.setText("Group name:");
    }




    //-------------------------GROUP PRODUCTS---------------------
    //---------------------------main buttons---------------------
    @FXML
    public void addingGroupButtonClicked() {
        buttonGroupsAnchor.setVisible(false);
        addingGroupAnchor.setVisible(true);
    }

    @FXML
    public void updateGroupButtonClicked() {
        buttonGroupsAnchor.setVisible(false);
        addingGroupSubmitButton.setVisible(false);
        updateGroupSubmitButton.setVisible(true);
        addingGroupAnchor.setVisible(true);
        inputNewGroupName.setVisible(true);
        newGroupTitleLabel.setVisible(true);
    }

    @FXML
    public void deleteGroupButtonClicked() {
        buttonGroupsAnchor.setVisible(false);
        addingGroupSubmitButton.setVisible(false);
        addingGroupAnchor.setVisible(true);
        deleteGroupSubmitButton.setVisible(true);
        inputGroupDescription.setVisible(false);
        groupDescriptionLabel.setVisible(false);
        //deleteGroupButton
    }

    //-------------------------adding groups---------------------
    @FXML
    public void addingGroupSubmitButtonClicked() {
        commandType = 6;
        String result = inputGroupName.getText() + "," + inputGroupDescription.getText();
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        addingGroupAnchor.setVisible(false);
        buttonGroupsAnchor.setVisible(true);
    }

    @FXML
    public void closeGroupButtonClicked() {
        addingGroupAnchor.setVisible(false);
        buttonGroupsAnchor.setVisible(true);
        inputNewGroupName.setVisible(false);
        newGroupTitleLabel.setVisible(false);
        addingGroupSubmitButton.setVisible(true);
        updateGroupSubmitButton.setVisible(false);
        deleteGroupSubmitButton.setVisible(false);
        inputGroupDescription.setVisible(true);
        groupDescriptionLabel.setVisible(true);
    }


    //---------------------------updating groups-----------------


    @FXML
    public void updateGroupSubmitButton() {
        commandType = 18;
        String result = inputGroupName.getText() + "," + inputNewGroupName.getText() + "," + inputGroupDescription.getText();
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        addingGroupAnchor.setVisible(false);
        buttonGroupsAnchor.setVisible(true);
        inputNewGroupName.setVisible(false);
        newGroupTitleLabel.setVisible(false);
        addingGroupSubmitButton.setVisible(true);
        updateGroupSubmitButton.setVisible(false);
    }


    //--------------------------------------delete--------------------
    @FXML
    public void deleteGroupSubmitButtonClicked() {
        commandType = 34;
        String result = inputGroupName.getText();
        Message message = new Message(commandType, result);
        App.client1.sendPackage(message.messagePackaging());
        App.client1.receive();
        addingGroupAnchor.setVisible(false);
        buttonGroupsAnchor.setVisible(true);
        addingGroupSubmitButton.setVisible(true);
        deleteGroupSubmitButton.setVisible(false);
        inputGroupDescription.setVisible(true);
        groupDescriptionLabel.setVisible(true);
    }



}
