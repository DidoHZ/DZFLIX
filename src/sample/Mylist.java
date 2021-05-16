package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

    public JFXButton delete_btn;

    public JFXButton Moreinfo;

    public void setData(items item){
        this.item = item;
        list_img.setImage(new Image("http://image.tmdb.org/t/p/original/"+item.getImgUrl()));
        rate_lbl.setText(item.getRate());
        list_title.setText(item.getTitle());
        list_descrbtion.setText(item.getDescription());
    }

    @FXML
    void delete() {
        Controller.getmylistGrid_().getChildren().remove(Controller.getmylistGrid_().getChildren().get(Controller.getmylist().indexOf(item.getID())));
        Controller.getmylist().remove((Integer) item.getID());
    }

    public void MoreDetails(ActionEvent event) {
        System.out.println("More Info :");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Details.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Details details = loader.getController();
        details.setData(stage,item.getID());

        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public void Show() {
        Moreinfo.setVisible(true);
        Moreinfo.setDisable(false);
        delete_btn.setVisible(true);
        delete_btn.setDisable(false);
    }

    public void Hide() {
        Moreinfo.setVisible(false);
        Moreinfo.setDisable(true);
        delete_btn.setVisible(false);
        delete_btn.setDisable(true);
    }
}