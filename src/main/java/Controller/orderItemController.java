package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.MyListener;
import main.main;
import model.Cloth;

import javax.persistence.criteria.Order;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import main.LoadScene;
import static main.ConnectDB.connection;

public class orderItemController  {
    @FXML
    private ImageView image;

    @FXML
    private Label price;

    @FXML
    private Label productName;

    @FXML
    private Label quantity;

    @FXML
    private Label remove;

    @FXML
    private Label date;


    public void removeOnAction(MouseEvent ev) throws SQLException, IOException {
        String query = "delete from orders where UserName =? and ProductName =? ";
        PreparedStatement statement;
        statement =connection.prepareStatement(query);
        statement.setString(1,ShopController.getuserName());
        statement.setString(2,productName.getText());
        statement.executeUpdate();
        LoadScene.load(ev,"inventory.fxml","Your Order");
    }

    public void setData(Image image1, String name, double total, int quantity1,Date date1) {
        image.setImage(image1);
        productName.setText(name);
        price.setText(String.valueOf(total));
        quantity.setText(String.valueOf(quantity1));
        date.setText(String.valueOf(date1));
    }
}
