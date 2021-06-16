package sample;

import Login.GetData;
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
import org.json.JSONException;

import java.io.IOException;

import static sample.Controller.UserID;
import static sample.Controller.connected;

public class Mylist {
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

    private String Type;
    private int ID;

    public void setData(items item){
        ID = item.getID(); Type = item.getType();
        list_img.setImage(new Image("http://image.tmdb.org/t/p/w154/"+item.getImgUrl()));
        rate_lbl.setText(item.getRate());
        list_title.setText(item.getTitle());
        list_descrbtion.setText(item.getDescription());
    }

    @FXML
    void delete() {
        Controller.getmylistGrid_().getChildren().remove(Controller.getmylistGrid_().getChildren().get(Controller.getmylist().indexOf(ID)));
        try {
            if(connected){
                GetData.DeleteFromUserList(UserID,ID);
            }
            Controller.getmylist().remove((Integer) ID);
        } catch (Exception e) {
        }
    }

    public void MoreDetails(ActionEvent event) throws JSONException, IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        if(Type.equals("movie")) {
            loader.setLocation(getClass().getResource("/sample/Details.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Details details = loader.getController();
            details.setData(stage, ID);

            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
        }else{
            loader.setLocation(getClass().getResource("/sample/SeriesDetails.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            SeriesDetails details = loader.getController();
            details.setData(stage, ID);

            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
        }
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