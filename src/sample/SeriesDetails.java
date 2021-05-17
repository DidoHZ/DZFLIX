package sample;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SeriesDetails {
    public ImageView add_img;
    public ChoiceBox<String> Seasons;
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
    private GridPane Episodes;

    @FXML
    private JFXButton exit;


    private items item = new items();

    private void FillList(ArrayList<items> items) throws IOException {
        int r = 0;
        if(!Episodes.getChildren().isEmpty())
            Episodes.getChildren().removeAll(Episodes.getChildren());
        for(items item:items){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Episode.fxml"));

            AnchorPane ap = loader.load();

            Episode episode = loader.getController();
            episode.SetData(item.getBackground(),(r+1)+" - "+item.getTitle(),item.getDescription());

           Episodes.add(ap,0,r++);
        }
    }

    public void setData(Stage stage,int ID) throws JSONException, IOException {

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,e->stage.close());

        JSONreader json = new JSONreader();
        try {
            item = json.getSeriesDetails(ID);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println("http://image.tmdb.org/t/p/w780/"+item.getBackground()+"\nID : "+ ID);
        if(Controller.getmylist().contains(ID)){
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(10);
            add_img.setFitHeight(10);
        }
        Main_show.setId(""+ID);
        Main_show.setStyle("-fx-background-image: url(http://image.tmdb.org/t/p/w780"+item.getBackground()+"); -fx-background-repeat: no-repeat; -fx-background-size: 450px 210px;");
        img.setImage(new Image("http://image.tmdb.org/t/p/original"+item.getImgUrl()));
        title.setText(item.getTitle());
        ryd.setText("IMDb "+item.getRate()+" | "+item.getDate()+" | "+(item.getDuration()==1?(item.getDuration()+" Season"):item.getDuration()+" Seasons"));
        Describtion.setText("Description :\n"+item.getDescription());
        language.setText("Language : "+item.getLanguage());
        Genres.setText("Genres : "+ String.join(",",item.getGenres()).replace(",null",""));
        Tagline.setText("Tagline : "+item.getTagline());
        for(int i=1;i<=item.getDuration();i++)
            Seasons.getItems().add("Season "+i+" ("+json.getSeasonEp(ID,i)+" Episodes)");
        Seasons.getSelectionModel().selectFirst();
        FillList(json.getEpisodes(ID,Integer.parseInt(Seasons.getValue().split(" ")[1])));
        Seasons.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    try {
                        FillList(json.getEpisodes(ID, Integer.parseInt(newValue.split(" ")[1])));
                    } catch (IOException | JSONException ioException) {
                        ioException.printStackTrace();
                    }
                } );
    }

    public void Addmylist(ActionEvent ae) {
        Pane pane = (Pane) ((JFXButton) ae.getSource()).getParent();
        System.out.println("Add to list ID : "+pane);
        if(!Controller.getmylist().contains(Integer.valueOf(pane.getId().replaceAll("[^0-9]","")))) {
            Controller.getmylist().add(Integer.valueOf(pane.getId().replaceAll("[^0-9]", "")));
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(10);
            add_img.setFitHeight(10);
        }else{
            Controller.getmylist().remove((Integer) item.getID());
            add_img.setImage(new Image("/images/plus.png"));
            add_img.setFitWidth(8);
            add_img.setFitHeight(8);
        }
    }
}
