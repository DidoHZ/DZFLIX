package sample;

import Login.GetData;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static sample.Controller.UserID;
import static sample.Controller.connected;

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
        items item = new items();
        try {
            item = json.getSeriesDetails(ID);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if(Controller.getmylist().contains(ID)){
            add_img.setImage(new Image("/images/check.png"));
            add_img.setFitWidth(10);
            add_img.setFitHeight(10);
        }
        Main_show.setId(""+ID);
        System.out.println("https://api.themoviedb.org/3/tv/"+ID+"?api_key=c4ca447c00aab9e96d6f9b202dbdf289");
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
