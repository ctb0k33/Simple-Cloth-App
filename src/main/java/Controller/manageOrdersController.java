package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import main.LoadScene;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static main.ConnectDB.connection;

public class manageOrdersController implements Initializable {

    @FXML
    private AnchorPane back;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void setActionBackButton(MouseEvent event) throws IOException {
        LoadScene.load(event, "admin.fxml","Admin");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> UserName = new ArrayList<>();
        List<String> productName = new ArrayList<>();
        List<Integer>quantity = new ArrayList<>();
        List<Double> totalPrice = new ArrayList<>();
        List<Date> orderDate = new ArrayList<>();
        List<String> status = new ArrayList<>();
        String query = "SELECT * FROM finalorders";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserName.add(rs.getString("UserName"));
                productName.add(rs.getString("ProductName"));
                quantity.add(rs.getInt("Quantity"));
                totalPrice.add(rs.getDouble("TotalPrice"));
                orderDate.add(rs.getDate("OrderDate"));
                status.add(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productName.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/clientsOrderItem.fxml"));
                VBox anchorPane = fxmlLoader.load();

                clientOrderItemController clientOrderItemController = fxmlLoader.getController();
                clientOrderItemController.setData(UserName.get(i),productName.get(i),totalPrice.get(i), quantity.get(i),orderDate.get(i),status.get(i));
                if(column ==1){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);


                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane,new Insets(0,0,20,10));


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
