package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import main.main;
import model.Cloth;

import java.util.Objects;

public class ItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label nameLabel;

    @FXML
    private void click(MouseEvent mouseEvent){
        myListener.onClickListener(cloth);
    }

    @FXML
    private Label priceLabel;

    private Cloth cloth;

    private MyListener myListener;


    public void setData(Cloth cloth, MyListener myListener) {
        this.cloth=cloth;
        this.myListener=myListener;
        nameLabel.setText(cloth.getName());
        priceLabel.setText(main.CURRENCY + cloth.getPrice());
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cloth.getImgSrc())));
        image.setImage(image1);
    }
}
