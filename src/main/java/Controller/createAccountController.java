package Controller;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.ConnectDB;
import main.LoadScene;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static main.ConnectDB.connection;

public class createAccountController implements Initializable {
    @FXML
    private ToggleButton backButton;

    @FXML
    private CheckBox female;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private CheckBox male;

    @FXML
    private TextField passWord;

    @FXML
    private Button signUp;

    @FXML
    private CheckBox unknown;

    @FXML
    private TextField userName;

    @FXML
    private Label notification;
    public void backButtonOnAction(MouseEvent e) throws IOException {
        LoadScene.load(e,"login.fxml","Login");
    }

    public TextField getFirstName() {
        return firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    public TextField getPassWord() {
        return passWord;
    }

    public TextField getUserName() {
        return userName;
    }

    public boolean checkWhiteSpace(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if(checkFirstName() && checkLastName() && checkUserName() && checkLastName() && checkGender() &&checkDuplicateAccount()) {
                        notification.setText("Create Account Successful !");
                        notification.setTextFill(Color.GREEN);
                        CreateAccount();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        female.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(female.isSelected()){
                    unknown.setSelected(false);
                    male.setSelected(false);
                }
            }
        });
        male.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(male.isSelected()){
                    female.setSelected(false);
                    unknown.setSelected(false);
                }
            }
        });
        unknown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(unknown.isSelected()){
                    female.setSelected(false);
                    male.setSelected(false);
                }
            }
        });
    }
    public void CreateAccount() throws SQLException, ClassNotFoundException {
        String firstName= String.valueOf(getFirstName().getText());
        String passWord= String.valueOf(getPassWord().getText());
        String userName=String.valueOf(getUserName().getText());
        String lastName=String.valueOf(getLastName().getText());
        String gender=null;
        if(female.isSelected()){
            gender="female";
        }
        else if(male.isSelected()){
            gender="male";
        }
        else if(unknown.isSelected()){
            gender="unknown";
        }
        String query= "Insert into client(UserName,PassWord,FirstName,LastName,Gender,balance)" +
                " values('"+userName+"','"+passWord+"','"+firstName+"','"+lastName+"','"+gender+"',default)";
        ConnectDB.connectDB(query);
    }
    public boolean checkUserName(){
        if(checkWhiteSpace(userName.getText())){
            notification.setText("UserName can't contain Whitespace character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        if(userName.getText().length()<7){
            userName.setText("");
            notification.setText("UserName must be greater than 6 character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }
    public boolean checkPassWord(){
        if(checkWhiteSpace(passWord.getText())){
            notification.setText("PassWord can't contain Whitespace character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        if(passWord.getText().length()<6){
            passWord.setText("");
            notification.setText("PassWord must be greater than 6 character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }
    public boolean checkFirstName(){
        if(checkWhiteSpace(firstName.getText())){
            notification.setText("FirstName can't contain Whitespace character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        char[] chars = firstName.getText().toCharArray();
        for(char c: chars){
            if(Character.isDigit(c)){
                firstName.setText("");
                notification.setText("FirstName can't contain number !");
                notification.setTextFill(Color.RED);
                return false;
            }
        }
        if(firstName.getText().length()==0){
            firstName.setText("");
            notification.setText("FirstName must not null !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }

    public boolean checkLastName(){
        if(checkWhiteSpace(lastName.getText())){
            notification.setText("LastName can't contain Whitespace character !");
            notification.setTextFill(Color.RED);
            return false;
        }
        char[] chars = lastName.getText().toCharArray();
        for(char c: chars){
            if(Character.isDigit(c)){
                lastName.setText("");
                notification.setText("LastName can't contain number !");
                notification.setTextFill(Color.RED);
                return false;
            }
        }
        if(lastName.getText().length()==0){
            lastName.setText("");
            notification.setText("LastName must not null !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }

    public boolean checkGender(){
        if(!(male.isSelected()||female.isSelected()||unknown.isSelected())){
            notification.setText("Choice your gender !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }

    public boolean checkDuplicateAccount() throws SQLException {
        String query = "SELECT * FROM client WHERE UserName = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        statement = connection.prepareStatement(query);
        statement.setString(1, userName.getText());
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            notification.setText("UserName already exist !");
            notification.setTextFill(Color.RED);
            return false;
        }
        return true;
    }
}
