package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mylist {
     private items item;

    @FXML
    private Label rate_lbl;

    @FXML
    private ImageView list_img;

    @FXML
    private Label list_title;

    @FXML
    private Label list_descrbtion;

    public void setData(items item){
        this.item = item;
        list_img.setImage(new Image("http://image.tmdb.org/t/p/original/"+item.getImgUrl()));
        rate_lbl.setText(item.getRate());
        list_title.setText(item.getTitle());
        list_descrbtion.setText(item.getDescription());
    }
}
