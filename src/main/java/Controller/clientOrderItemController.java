package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.LoadScene;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static main.ConnectDB.connection;

public class clientOrderItemController {
    @FXML
    private Label date;

    @FXML
    private Label finish;

    @FXML
    private Label status;

    @FXML
    private Label price;

    @FXML
    private Label productName;

    @FXML
    private Label quantity;

    @FXML
    private Label remove;

    @FXML
    private Label username;
    @FXML
    private Label onGoing;

    // On Going
    @FXML
    private void onGoingButtonOnAction (MouseEvent e) throws SQLException,IOException{
        String query = "UPDATE finalorders SET status = ? WHERE UserName = ? and ProductName = ? ";
        PreparedStatement statement;
        statement =connection.prepareStatement(query);
        statement.setString(1,"On Going");
        statement.setString(2,username.getText());
        statement.setString(3,productName.getText());
        statement.executeUpdate();
        LoadScene.load(e,"manageOrders.fxml","Manage Orders");
    }
    //postpone
    public void removeButtonOnAction(MouseEvent e) throws SQLException, IOException {
        String query = "UPDATE finalorders SET status = ? WHERE UserName = ? and ProductName = ? ";
        PreparedStatement statement;
        statement =connection.prepareStatement(query);
        statement.setString(1,"Postpone");
        statement.setString(2,username.getText());
        statement.setString(3,productName.getText());
        statement.executeUpdate();
        LoadScene.load(e,"manageOrders.fxml","Manage Orders");

    }

    public void finishButtonOnAction(MouseEvent e) throws SQLException, IOException {
        String query = "UPDATE finalorders SET status = ? WHERE UserName = ? and ProductName = ? ";
        PreparedStatement statement;
        statement =connection.prepareStatement(query);
        statement.setString(1,"Finished");
        statement.setString(2,username.getText());
        statement.setString(3,productName.getText());
        statement.executeUpdate();
        LoadScene.load(e,"manageOrders.fxml","Manage Orders");
    }
    public void setData( String username1,String name, double total, int quantity1, Date date1,String status1) {
        username.setText(username1);
        productName.setText(name);
        price.setText(String.valueOf(total));
        quantity.setText(String.valueOf(quantity1));
        date.setText(String.valueOf(date1));
        status.setText(status1);
        if(status1.equals("Finished")){
            status.setTextFill(Color.GREEN);
        }
        if(status1.equals("Postpone")){
            status.setTextFill(Color.RED);
        }
        if(status1.equals("On going")){
            status.setTextFill(Color.WHITE);
        }
    }
}
