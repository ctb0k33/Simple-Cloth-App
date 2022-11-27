package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.main;
import main.MyListener;
import model.Cloth;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import main.LoadScene;

import static main.ConnectDB.connection;

public class ShopController implements Initializable {

    @FXML
    private VBox chosenClothCard;

    @FXML
    private ImageView clothImage;

    @FXML
    private ImageView inventory;

    @FXML
    private Label clothNameLabel;

    @FXML
    private Label clothPriceLabel;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ComboBox comboBox1;

    @FXML
    private Label Price;

    @FXML
    private Button addToCart;

    private List<Cloth> cloths = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private int quantity=1;


    private List<Cloth> getdata() {
        List<Cloth> cloths = new ArrayList<>();
        Cloth cloth;

        cloth = new Cloth();
        cloth.setName("BlackHoodie");
        cloth.setPrice(7.99);
        cloth.setImgSrc("/image/BlackHoodie.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("Beanie");
        cloth.setPrice(6.99);
        cloth.setImgSrc("/image/Beanie.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("BlackCloth");
        cloth.setPrice(8.99);
        cloth.setImgSrc("/image/BlackCloth.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("BlackShoes");
        cloth.setPrice(3.99);
        cloth.setImgSrc("/image/BlackShoes.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("BlackTrouser");
        cloth.setPrice(5.99);
        cloth.setImgSrc("/image/BlackTrouser.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("GreenHat");
        cloth.setPrice(2.99);
        cloth.setImgSrc("/image/GreenHat.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth = new Cloth();
        cloth.setName("Trouser");
        cloth.setPrice(8.99);
        cloth.setImgSrc("/image/Trouser.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("WhiteCloth");
        cloth.setPrice(7.99);
        cloth.setImgSrc("/image/WhiteCloth.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("WhiteHoodie");
        cloth.setPrice(9.99);
        cloth.setImgSrc("/image/WhiteHoodie.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        cloth = new Cloth();
        cloth.setName("WhiteShoes");
        cloth.setPrice(3.99);
        cloth.setImgSrc("/image/WhiteShoes.jpg");
        cloth.setColor("6A7324");
        cloths.add(cloth);

        return cloths;
    }

    public static String getuserName() throws SQLException {
        String username="";
        String query = "SELECT * FROM client WHERE isOnline = 1";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            username = rs.getString("UserName");
        }
        return username;
    }

    public static double balance() throws SQLException {
        double balance =0;
        String query = "SELECT * FROM client WHERE isOnline = 1";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            balance = rs.getDouble("balance");
        }
        return balance;
    }
    public static void setbalance(double amout) throws SQLException {
        double newBalance = balance()-amout;
        String query = "Update client set balance = ? where UserName =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1,newBalance);
        statement.setString(2,getuserName());
        statement.executeUpdate();
    }

    private boolean checkDuplicate(String productName) throws SQLException {
        String query=  " select * from orders where username =?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString (1, getuserName());
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()){
            if(productName.equals(resultSet.getString("ProductName"))) {
                return true;
            }
        }
        return false;
    }
    public void addToCartAction() throws SQLException {
        if(!checkDuplicate(clothNameLabel.getText())) {
            String query = " insert into orders (UserName, ProductName, Quantity, TotalPrice, OrderDate)"
                    + " values (?, ?, ?, ?, ?)";
            String inputPrice = "";
            for (int i = 1; i < Price.getText().length(); i++) {
                inputPrice += Price.getText().charAt(i);
            }
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, getuserName());
            preparedStmt.setString(2, clothNameLabel.getText());
            preparedStmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
            preparedStmt.setInt(3, quantity);
            preparedStmt.setDouble(4, Double.parseDouble(inputPrice));

            preparedStmt.execute();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Duplicate product, please remove this product first in your order!");
            alert.showAndWait();
        }
    }

    public double getCurrentClothPrice(){
        if(clothNameLabel.getText().equals("BlackHoodie")){
            return 7.99;
        }
        else if (clothNameLabel.getText().equals("BlackCloth")){
            return 8.99;
        }
        else if (clothNameLabel.getText().equals("Beanie")){
            return 6.99;
        }
        else if (clothNameLabel.getText().equals("BlackShoes")){
            return 3.99;
        }
        else if (clothNameLabel.getText().equals("BlackTrouser")){
            return 5.99;
        }
        else if (clothNameLabel.getText().equals("GreenHat")){
            return 2.99;
        }
        else if (clothNameLabel.getText().equals("Trouser")){
            return 8.99;
        }
        else if (clothNameLabel.getText().equals("WhiteCloth")){
            return 7.99;
        }
        else if (clothNameLabel.getText().equals("WhiteHoodie")){
            return 9.99;
        }
        else if (clothNameLabel.getText().equals("WhiteShoes")){
            return 3.99;
        }
        return 0;
    }

    private void setChosenCloth(Cloth cloth){
        clothNameLabel.setText(cloth.getName());
        clothPriceLabel.setText(main.CURRENCY + cloth.getPrice());
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cloth.getImgSrc())));
        clothImage.setImage(image);
        chosenClothCard.setStyle("-fx-background-color: #"+cloth.getColor()+";\n" +
                "-fx-background-radius: 30;");
    }

    public void openInventoryAction(MouseEvent e) throws IOException {
        LoadScene.load(e,"inventory.fxml","Your Order");
    }

    public void backButtonOnAction(MouseEvent e) throws IOException{
        LoadScene.load(e,"login.fxml","Your Order");
    }

    public void setPrice(int number, double ClothPrice){
        double price = number* ClothPrice;
        Price.setText("$"+ price);
    }

    public void openUserProfile(MouseEvent e) throws IOException{
        LoadScene.load(e,"userProfile.fxml","My Account");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox1.setValue(1);
        comboBox1.getItems().addAll(1,2,3,4);
        comboBox1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                quantity = (int) t1;
                setPrice(quantity,getCurrentClothPrice());
            }
        });
        cloths.addAll(getdata());
        if(cloths.size() >0 ){
            setChosenCloth(cloths.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Cloth cloth){
                    setChosenCloth(cloth);
                    setPrice(quantity,getCurrentClothPrice());
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < cloths.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(cloths.get(i),myListener);

                if(column ==3){
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

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
