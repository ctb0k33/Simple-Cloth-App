package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import main.LoadScene;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import static main.ConnectDB.connection;

public class clientInformationController {

    @FXML
    private Label addbalance;

    @FXML
    private Label balance;

    @FXML
    private Label fullname;

    @FXML
    private Label username;

    @FXML
    void addBalanceButtonOnAction(MouseEvent event) throws SQLException, IOException {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Add Balance");
        dialog.setHeaderText("Add Balance");
        dialog.setContentText("Please enter money: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("abc");
            String query = "update client set balance = ? where UserName = ?";
            PreparedStatement statement;
            statement =connection.prepareStatement(query);
            double newbalance = Double.parseDouble(balance.getText()) + Double.parseDouble(result.get());
            System.out.println(result.get());
            statement.setDouble(1,newbalance);
            statement.setString(2,username.getText());
            statement.executeUpdate();
            LoadScene.load(event,"manageClient.fxml","Manage Client");
        }
    }

    public void setData(String username1, String name, double balance1) {
        username.setText(username1);
        fullname.setText(name);
        balance.setText(String.valueOf(balance1));
    }
}
