package sample;

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


    private items item = new items();

    public void setData(Stage stage,int ID){

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,e->stage.close());

        JSONreader json = new JSONreader();
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
        if(!Controller.getmylist().contains(Integer.valueOf(pane.getId().replaceAll("[^0-9]","")))) {
            Controller.getmylist().add(Integer.valueOf(pane.getId().replaceAll("[^0-9]", "")));
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(12);
            add_img.setFitHeight(12);
        }else{
            Controller.getmylist().remove((Integer) item.getID());
            add_img.setImage(new Image("/images/plus.png"));
            add_img.setFitWidth(8);
            add_img.setFitHeight(8);
        }
    }
}
