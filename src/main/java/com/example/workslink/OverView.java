package com.example.workslink;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverView {

        @FXML
        private Label AssignedTO;

        @FXML
        private Label Status;

        @FXML
        private Label taskName;

    public Label getAssignedTO() {
        return AssignedTO;
    }

    public void setAssignedTO(Label assignedTO) {
        AssignedTO = assignedTO;
    }

    public Label getStatus() {
        return Status;
    }

    public void setStatus(Label status) {
        Status = status;
    }

    public Label getTaskName() {
        return taskName;
    }

    public void setTaskName(Label taskName) {
        this.taskName = taskName;
    }
}


