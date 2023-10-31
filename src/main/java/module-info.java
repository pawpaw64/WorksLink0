module com.example.workslink {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;

    opens com.example.workslink to javafx.fxml;
    exports com.example.workslink;
}