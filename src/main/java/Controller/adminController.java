package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main.LoadScene;

import java.io.IOException;

public class adminController {
    @FXML
    private Button manageClients;

    @FXML
    private Button manageOrders;

    @FXML
    void ManageOrderButtonOnAction(MouseEvent event) throws IOException {
        LoadScene.load(event,"manageOrders.fxml","Manage Orders");
    }

    @FXML
    void manageClientButtonOnAction(MouseEvent event) throws IOException {
        LoadScene.load(event,"manageClient.fxml","Manage Clients");
    }
    @FXML
    void backButtonOnAction(MouseEvent event) throws IOException{
        LoadScene.load(event,"login.fxml","Login");
    }
}
