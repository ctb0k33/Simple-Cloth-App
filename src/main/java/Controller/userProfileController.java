package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import main.ConnectDB;
import main.LoadScene;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static main.ConnectDB.connection;

public class userProfileController implements Initializable {

    @FXML
    ImageView userImage;
    @FXML
    private Label name;

    @FXML
    private Label password;

    @FXML
    private Label username;

    @FXML
    private Label balance;

    @FXML
    private Label name1;


    @FXML
    private void editProfileOnAction (MouseEvent e){

    }
    public void backButtonOnAction(MouseEvent e) throws IOException {
        LoadScene.load(e,"Cloth-shop.fxml","Cloth Shop");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query= "select * from client where isOnline =1";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                name.setText(resultSet.getString("LastName")+" "+resultSet.getString("FirstName"));
                name1.setText(resultSet.getString("LastName")+" "+resultSet.getString("FirstName"));
                username.setText(resultSet.getString("UserName"));
                password.setText(resultSet.getString("PassWord"));
                balance.setText(String.valueOf(resultSet.getDouble("balance")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
