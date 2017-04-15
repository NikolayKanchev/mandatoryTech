package sample;

import dataBase.Adapter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ContLogin implements Initializable{

    @FXML
    Label redLabel;

    @FXML
    ChoiceBox choice;

    @FXML
    TextField userNameField;

    @FXML
    PasswordField passField;

    public void signIn(ActionEvent actionEvent) throws IOException {
        String tableDB = "";

        if (choice.getSelectionModel().isEmpty()){
            redLabel.setText("You have to choose 'Status'");
            redLabel.setVisible(true);
            return;
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
            tableDB = "admins";
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Player")){
            tableDB = "players";
        }

        if(userNameField.getText().isEmpty() || passField.getText().isEmpty()){
            redLabel.setText("You have to fill out all the fields");
            redLabel.setVisible(true);
            return;
        }

        Adapter adapter = Adapter.getInstance();
        if(!adapter.checkUserAndPassword(userNameField.getText(), passField.getText(), tableDB)){
            redLabel.setText("Wrong username or password");
            redLabel.setVisible(true);
            return;
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
            changeScreen(actionEvent, "screenAdminChoice.fxml");
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Player")){
            changeScreen(actionEvent, "screenPlayerChoice.fxml");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice.setItems(FXCollections.observableArrayList("Admin", "Player"));
        choice.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
                    userNameField.setPromptText(null);
                    redLabel.setVisible(false);
                }else{
                    userNameField.setPromptText("e-mail");
                    redLabel.setVisible(false);
                }
            }
        });
    }

    public void changeScreen(ActionEvent e, String s) throws IOException {
        Stage stage = (Stage)(((Node) e.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(s)), 800, 600));
    }
}
