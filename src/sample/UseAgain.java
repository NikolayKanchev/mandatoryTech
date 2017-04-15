package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Didi on 04/11/2017.
 */
public class UseAgain {
    private static UseAgain instance;


    private UseAgain(){

    }

    public synchronized static UseAgain getInstance(){
        if (instance == null){
            instance = new UseAgain();
        }
        return instance;
    }

    public void goBack(ActionEvent actionEvent, String fxml) throws IOException {
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
    }

    public void exitOrLogOut(MouseEvent mouseEvent, ChoiceBox exitOptions) {
        exitOptions.getSelectionModel().selectedItemProperty().addListener((v,oldValue, newValue) -> {
            if(exitOptions.getSelectionModel().getSelectedItem().equals("Exit")){
                System.exit(0);
            }

            if(exitOptions.getSelectionModel().getSelectedItem().equals("Log out")){
                Stage stage = (Stage)(((Node) mouseEvent.getSource()).getScene().getWindow());
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("screenLogin.fxml")),800, 600));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
