package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Episode {
    @FXML
    private ImageView Episode_img;

    @FXML
    private Label Episode_Title;

    @FXML
    private Label Episode_Description;

    public void SetData(String img,String title,String Description){
        Episode_img.setImage(new Image(img));
        Episode_Title.setText(title);
        Episode_Description.setText(Description);
    }
}
