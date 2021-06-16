package sample;

import Login.GetData;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

import static sample.Controller.UserID;
import static sample.Controller.connected;

public class Details {
    public ImageView add_img;
    @FXML
    private Pane Main_show;

    @FXML
    private Label title;

    @FXML
    private Label ryd;

    @FXML
    private Label Describtion;

    @FXML
    private ImageView img;

    @FXML
    private Label language;

    @FXML
    private Label Genres;

    @FXML
    private Label Tagline;

    @FXML
    private GridPane Similar;

    @FXML
    private JFXButton exit;




    public void setData(Stage stage,int ID){

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,e->stage.close());

        JSONreader json = new JSONreader();
        items item = new items();
        try {
            item = json.getMovieDetails(ID);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if(Controller.getmylist().contains(ID)){
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(10);
            add_img.setFitHeight(10);
        }
        Main_show.setId(""+ID);
        Main_show.setStyle("-fx-background-image: url(http://image.tmdb.org/t/p/w780"+item.getBackground()+"); -fx-background-repeat: no-repeat; -fx-background-size: 450px 210px;");
        img.setImage(new Image("http://image.tmdb.org/t/p/w154"+item.getImgUrl()));
        title.setText(item.getTitle());
        ryd.setText("IMDb "+item.getRate()+" | "+item.getDate()+" | "+(item.getDuration()>60?(item.getDuration()/60+"h"+ item.getDuration()%60+"min"):item.getDuration()+"min"));
        Describtion.setText("Description :\n"+item.getDescription());
        language.setText("Language : "+item.getLanguage());
        Genres.setText("Genres : "+ String.join(",",item.getGenres()).replace(",null",""));
        Tagline.setText("Tagline : "+item.getTagline());
    }

    public void Addmylist(ActionEvent ae) {
        Pane pane = (Pane) ((JFXButton) ae.getSource()).getParent();
        int ItemID = Integer.parseInt(pane.getId().replaceAll("[^0-9]",""));
        if(!Controller.getmylist().contains(ItemID)) {
            System.out.println("UserID : "+(UserID!=null?UserID:"null"));
            if(connected){
                GetData.AddToUserList(UserID,ItemID,(new JSONreader()).CheckType(ItemID));
            }
            Controller.getmylist().add(ItemID);
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(12);
            add_img.setFitHeight(12);
        }else{
            if(connected){
                GetData.DeleteFromUserList(UserID,ItemID);
            }
            Controller.getmylist().remove((Integer) ItemID);
            add_img.setImage(new Image("/images/plus.png"));
            add_img.setFitWidth(8);
            add_img.setFitHeight(8);
        }
    }
}
