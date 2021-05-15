package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label Main_describtion;
    public Label Main_duration;
    public Label Main_title;
    public Pane Main_img;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ToggleGroup Trend;

    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane Mylist;

    @FXML
    private AnchorPane Home;

    @FXML
    private GridPane gridList;

    private final JSONreader json = new JSONreader();
    private List<items> items = new ArrayList<>();
    private static ArrayList<Integer>  MyList = new ArrayList<>();

    static ArrayList<Integer>getmylist(){
        return MyList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        items fitem = new items();

        try {
            fitem = json.getMostPopular();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        BackgroundImage myBI= new BackgroundImage(new Image("http://image.tmdb.org/t/p/original/"+fitem.getBackground(),900,300,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Main_img.setBackground(new Background(myBI));
        Main_describtion.setText(fitem.getDescription());
        Main_duration.setText("duration "+(fitem.getDuration()>60?(fitem.getDuration()/60+"h"+ fitem.getDuration()%60+"min"):fitem.getDuration()+"min"));
        Main_title.setText(fitem.getTitle());

        try {
            items.addAll(json.get_popular());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int c = 0,r = 1;
        try {
            for (items item : items) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/items.fxml"));

                AnchorPane ap = loader.load();

                itemsController itemscontroller = loader.getController();
                itemscontroller.setData(item);

                if (c == 4) { c = 0; r++;}

                grid.add(ap, c++, r);
                GridPane.setMargin(ap, new Insets(5,10,50,10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlayTrailer(MouseEvent event) {

    }

    public void Mylist(ActionEvent actionEvent) throws JSONException, IOException {
        Mylist.toFront();
        JSONreader json = new JSONreader();
        System.out.println(MyList);
        int r = 0;
        for(int id:MyList){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Mylist.fxml"));

            AnchorPane ap = loader.load();

            Mylist mylist = loader.getController();
            mylist.setData(json.getDetails(id));

            gridList.add(ap,0,r++);
            GridPane.setMargin(ap, new Insets(40,50,10,10));
            System.out.println("Done");
        }
    }

    public void Home(ActionEvent actionEvent) {
        Home.toFront();
    }

    public void Addlist(ActionEvent actionEvent)  {

    }
}
/*List<items> items = new ArrayList<>();
        items item;

        item = new items();
        item.setImgUrl("https://fr.web.img2.acsta.net/c_310_420/pictures/19/04/16/09/46/5269925.jpg");
        item.setTitle("The Perfiction");
        item.setDate("2018");
        item.setRate("6.1");
        items.add(item);

        item = new items();
        item.setImgUrl("https://upload.wikimedia.org/wikipedia/en/5/5c/Apostle_poster.jpg");
        item.setTitle("Apostle");
        item.setDate("2018");
        item.setRate("6.3");
        items.add(item);

        item = new items();
        item.setImgUrl("https://vignette.wikia.nocookie.net/cinemorgue/images/c/c7/The_Wolf_of_Wall_Street_2013.jpg");
        item.setTitle("The Wolf of Wall Street");
        item.setDate("2013");
        item.setRate("8.2");
        items.add(item);

        item = new items();
        item.setImgUrl("https://upload.wikimedia.org/wikipedia/en/4/47/The_Autopsy_of_Jane_Doe.jpeg");
        item.setTitle("The Autopsy of Jane Doe");
        item.setDate("2016");
        item.setRate("6.8   ");
        items.add(item);

        item = new items();
        item.setImgUrl("https://upload.wikimedia.org/wikipedia/en/4/4d/Catch_Me_If_You_Can_2002_movie.jpg");
        item.setTitle("Catch Me If You Ca");
        item.setDate("2002");
        item.setRate("8.1");
        items.add(item);
        */