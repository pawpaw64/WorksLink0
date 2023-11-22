module com.example.workslink {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires com.jfoenix;

    opens com.example.workslink to javafx.fxml;
    exports com.example.workslink;
    exports com.example.workslink.chat;
    opens com.example.workslink.chat to javafx.fxml;
}