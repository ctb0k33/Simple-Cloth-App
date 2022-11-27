package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.LoadScene;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static main.ConnectDB.connection;

public class manageClientController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void setActionBackButton(MouseEvent event) throws IOException {
        LoadScene.load(event,"admin.fxml","Admin");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> UserName = new ArrayList<>();
        List<String> fullname = new ArrayList<>();
        List<Double> balance = new ArrayList<>();
        String query = "SELECT * FROM client";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserName.add(rs.getString("UserName"));
                fullname.add(rs.getString("FirstName")+rs.getString("LastName"));
                balance.add(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < UserName.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/clientsInformation.fxml"));
                VBox anchorPane = fxmlLoader.load();

                clientInformationController clientInformationController = fxmlLoader.getController();
                clientInformationController.setData(UserName.get(i),fullname.get(i),balance.get(i));
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
