//area_details.fxml controller
package com.example.workslink;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class areaDetailsController implements Initializable {
    @FXML
    TabPane tabPane;
    @FXML
    StackPane stack1 = new StackPane();
    @FXML
    Tab task;
    @FXML
    private VBox todoVbox;
    @FXML
    private VBox completeVbox;
    @FXML
    private VBox doingVbox;



    public VBox getTodoVbox() {
        return todoVbox;
    }


    @FXML
    void add_task(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/workslink/FXML/add-task.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(new Scene(root));

            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void addPaneToVBox(Pane pane, VBox targetVBox) {
        System.out.println("hhg");
        targetVBox.getChildren().add(pane);
    }
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                    new PieChart.Data("Todo",100),
                    new PieChart.Data("Doing",30),
                    new PieChart.Data("Done",20));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(),"amount",data.pieValueProperty()
                        )
                )
                );
        pieChart.getData().addAll(pieChartData);
    }

}

