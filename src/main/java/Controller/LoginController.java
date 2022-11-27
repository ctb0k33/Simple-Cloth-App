package Controller;

import com.mysql.cj.callback.UsernameCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.LoadScene;
import main.main;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.ConnectDB.connection;
import static main.main.stage;

public class LoginController implements Initializable {
    @FXML
    private Button createAccountButton;

    @FXML
    private Label forgotPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginNotification;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username ;

    public void loginButtonOnAction(ActionEvent e) throws IOException, SQLException {

        if (isValidAccount()) {
            String currentUserName = username.getText();
            LoadScene.load(e,"Cloth-shop.fxml","Cloth shop");
        }
        else if(isAdmin()){
            LoadScene.load(e,"admin.fxml","Admin");
        }
        else{
            loginNotification.setText("Wrong UserName or PassWord !");
            loginNotification.setTextFill(Color.RED);
        }
    }

    public void forgotPassWordOnAction(MouseEvent e){
        System.out.println("hello2");
    }

    public void CreateAccountButtonOnAction(ActionEvent e) throws IOException {
        LoadScene.load(e, "createAccount.fxml", "Create Account");
    }

    public boolean isAdmin(){
        if(username.getText().equals("admin") && password.getText().equals("admin")){
            return true;
        }
        return false;
    }
    public boolean isValidAccount() throws SQLException {
        String query = "SELECT * FROM client WHERE UserName = ? and PassWord = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        statement = connection.prepareStatement(query);
        statement.setString(1, username.getText());
        statement.setString(2,password.getText());
        resultSet = statement.executeQuery();

        String query2 = "update client set isOnline = true where UserName =?";
        PreparedStatement pstmt = connection.prepareStatement(query2);
        pstmt.setString(1, username.getText());
        int rowAffected = pstmt.executeUpdate();

        if(resultSet.next()){
            loginNotification.setText("UserName already exist !");
            loginNotification.setTextFill(Color.RED);
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String query = "update client set isOnline = false";
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
