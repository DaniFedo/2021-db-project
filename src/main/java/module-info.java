module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens org.example to javafx.fxml;
    exports org.example;
}