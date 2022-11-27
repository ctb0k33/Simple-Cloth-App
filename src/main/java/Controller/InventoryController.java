package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.LoadScene;
import main.MyListener;
import model.Cloth;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static main.ConnectDB.connection;

public class InventoryController implements Initializable {

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private Label notification;

    @FXML
    private GridPane grid;


    @FXML
    private Button buyBtn;
    public void setActionBackButton(MouseEvent e) throws IOException {
        LoadScene.load(e,"Cloth-shop.fxml","Cloth Shop");
    }

    public boolean checkBalance() throws SQLException {
            String query="Select * from orders Where UserName =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,ShopController.getuserName());
            ResultSet resultSet = statement.executeQuery();
            double amount = 0;
            while (resultSet.next()){
                amount+=resultSet.getDouble("TotalPrice");
            }
        if(ShopController.balance()>amount){
            ShopController.setbalance(amount);
            return true;
        }
        return false;
    }
    public void buyButtonOnAction(MouseEvent e) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Confirm to buy ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            if (checkBalance()) {
                notification.setText("Order Successfully!");
                notification.setTextFill(Color.GREEN);
                String query = "SELECT * FROM orders WHERE UserName = ?";
                String query2 = " insert into finalorders (UserName, ProductName, Quantity, TotalPrice, OrderDate)"
                        + " values (?, ?, ?, ?, ?)";
                try {
                    String username = ShopController.getuserName();
                    PreparedStatement st = connection.prepareStatement(query);
                    st.setString(1, username);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        PreparedStatement statement = connection.prepareStatement(query2);
                        statement.setString(1, ShopController.getuserName());
                        statement.setString(2, rs.getString("ProductName"));
                        statement.setInt(3, rs.getInt("Quantity"));
                        statement.setDouble(4, rs.getDouble("TotalPrice"));
                        statement.setDate(5, rs.getDate("OrderDate"));
                        statement.executeUpdate();
                    }
                } catch (SQLException ev) {
                    ev.printStackTrace();
                }
                String deletequery = "delete from orders where UserName =? ";
                PreparedStatement statement1;
                statement1 =connection.prepareStatement(deletequery);
                statement1.setString(1,ShopController.getuserName());
                statement1.executeUpdate();
                LoadScene.load(e,"inventory.fxml","Your Order");
            }
            else{
                notification.setText("Not enough money!");
                notification.setTextFill(Color.RED);
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> productName = new ArrayList<>();
        List<Integer>quantity = new ArrayList<>();
        List<Double> totalPrice = new ArrayList<>();
        List<Date> orderDate = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE UserName = ?";
        try {
            String username = ShopController.getuserName();
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                productName.add(rs.getString("ProductName"));
                quantity.add(rs.getInt("Quantity"));
                totalPrice.add(rs.getDouble("TotalPrice"));
                orderDate.add(rs.getDate("OrderDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productName.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/ordersItem.fxml"));
                VBox anchorPane = fxmlLoader.load();

                orderItemController orderItemController = fxmlLoader.getController();
                String imageurl = "/image/"+productName.get(i)+".jpg";
                System.out.println(imageurl);
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageurl)));
//                Image image = new Image("/image/BlackShoes.jpg");
                orderItemController.setData(image,productName.get(i),totalPrice.get(i), quantity.get(i),orderDate.get(i));
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
