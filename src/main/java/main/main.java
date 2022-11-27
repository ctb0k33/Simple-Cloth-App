package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static main.ConnectDB.connection;

public class main extends Application {
    public static Stage stage;
    public static final String CURRENCY = "$";
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage=primaryStage;
        stage.setScene(scene);
        stage.setTitle("Cloth shop");
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        launch();
    }
}



