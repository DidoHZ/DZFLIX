package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Details implements Initializable {
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
    private ImageView exit;


    private items item = new items();

    public void setData(Stage stage,int ID){

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,e->stage.close());

        JSONreader json = new JSONreader();
        try {
            item = json.getDetails(ID);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println("http://image.tmdb.org/t/p/original/"+item.getBackground());
        Main_show.setStyle("-fx-background-image: url(http://image.tmdb.org/t/p/original"+item.getBackground()+"); -fx-background-repeat: no-repeat; -fx-background-size: 450px 210px;");
        img.setImage(new Image("http://image.tmdb.org/t/p/original"+item.getImgUrl()));
        title.setText(item.getTitle());
        ryd.setText("IMDb "+item.getRate()+" | "+item.getDate()+" | "+(item.getDuration()>60?(item.getDuration()/60+"h"+ item.getDuration()%60+"min"):item.getDuration()+"min"));
        Describtion.setText(item.getDescription());
        language.setText("Language : "+item.getLanguage());
        Genres.setText("Genres : "+ String.join(",",item.getGenres()));
        Tagline.setText("Tagline : "+item.getTagline());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
