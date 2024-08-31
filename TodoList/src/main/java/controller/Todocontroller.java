package controller;

import Db.DbConnection;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.TODOtask;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static java.sql.Date.valueOf;

@AllArgsConstructor
@NoArgsConstructor
public class Todocontroller implements Initializable {

    @FXML
    private TableColumn colDate;

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnReload;

    @FXML
    private JFXCheckBox chckDone1;

    @FXML
    private JFXCheckBox chckDone2;

    @FXML
    private JFXCheckBox chckDone3;

    @FXML
    private JFXCheckBox chckDone4;

    @FXML
    private JFXCheckBox chckDone5;

    @FXML
    private TableView tblCompletedTask;

    @FXML
    private TextField txtAddTaskDesc;

    @FXML
    private TextField txtAddTaskTitle;

    @FXML
    private TextArea txtArea1;

    @FXML
    private TextArea txtArea2;

    @FXML
    private TextArea txtArea3;

    @FXML
    private TextArea txtArea4;

    @FXML
    private TextArea txtArea5;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colTitle;

    private int currentTextAreaIndex = 0;
    private TextArea[] textAreas;
    public LocalDate doneDate = LocalDate.now();

    @FXML
    void btnAddTaskOnAction(ActionEvent event) {
        String taskTitle = txtAddTaskTitle.getText();
        String taskDescription = txtAddTaskDesc.getText();
        Date taskDate = valueOf(txtDate.getValue());
        String SQL = "INSERT INTO newtasks (task_title, task_description, completion_date) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolistmanager", "root", "sachin");
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setString(1, taskTitle);
            psTm.setString(2, taskDescription);
            psTm.setDate(3, taskDate);

            int dataEntered = psTm.executeUpdate();
            if (dataEntered > 0){
                System.out.println("Data Added");
                textAreas[currentTextAreaIndex].setText(taskTitle);
                currentTextAreaIndex++;
                if (currentTextAreaIndex >= textAreas.length) {
                    currentTextAreaIndex = 0;
                }
                clearInputFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        checkAndAddCompletedTask(chckDone1, txtArea1);
        checkAndAddCompletedTask(chckDone2, txtArea2);
        checkAndAddCompletedTask(chckDone3, txtArea3);
        checkAndAddCompletedTask(chckDone4, txtArea4);
        checkAndAddCompletedTask(chckDone5, txtArea5);

        colId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("doneDate"));
    }

    private void checkAndAddCompletedTask(JFXCheckBox checkBox, TextArea textArea) {
        if (checkBox.isSelected()) {
            String taskTitle = textArea.getText();
            CompletedTaskList(taskTitle);
            textArea.clear();
            checkBox.setSelected(false);
        }
    }

    private void CompletedTaskList(String taskTitle) {
        int taskId = getTaskIdByTitle(taskTitle);
        String taskDescription = getTaskDescByTitle(taskTitle);

        if (taskId != -1 && taskDescription != null) {
            TODOtask completedTask = new TODOtask(taskId, taskTitle, taskDescription, doneDate);

            List<TODOtask> completedTaskList = DbConnection.getInstance().getConnection();
            ObservableList<TODOtask> completedTaskObservableList = FXCollections.observableArrayList(completedTaskList);

            completedTaskObservableList.add(completedTask);
            tblCompletedTask.setItems(completedTaskObservableList);

            System.out.println("Task Added");
        } else {
            System.out.println("Task Not Found");
        }
    }

    @FXML
    void chckDone1OnAction(ActionEvent event) {

    }

    @FXML
    void chckDone2OnAction(ActionEvent event) {

    }

    @FXML
    void chckDone3OnAction(ActionEvent event) {

    }

    @FXML
    void chckDone4OnAction(ActionEvent event) {

    }

    @FXML
    void chckDone5OnAction(ActionEvent event) {

    }

    private void clearInputFields() {
        txtAddTaskTitle.clear();
        txtAddTaskDesc.clear();
        txtDate.setValue(null);
    }

    private int getTaskIdByTitle(String taskTitle) {
        String SQL = "SELECT task_id FROM newtasks WHERE task_title = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolistmanager", "root", "sachin");
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setString(1, taskTitle);
            ResultSet rs = psTm.executeQuery();
            if (rs.next()) {
                return rs.getInt("task_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getTaskDescByTitle(String taskTitle) {
        String SQL = "SELECT task_description FROM newtasks WHERE task_title = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolistmanager", "root", "sachin");
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setString(1, taskTitle);
            ResultSet rs = psTm.executeQuery();
            if (rs.next()) {
                return rs.getString("task_description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textAreas = new TextArea[]{txtArea1, txtArea2, txtArea3, txtArea4, txtArea5};

        chckDone1.setText("");
        chckDone2.setText("");
        chckDone3.setText("");
        chckDone4.setText("");
        chckDone5.setText("");
    }
}
