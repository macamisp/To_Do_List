package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Todomain {

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnToDoList;

    @FXML
    void btnHistoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnToDoListOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/todolist.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
