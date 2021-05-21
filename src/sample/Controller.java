package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
    public ImageView MainAdd_img;
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
    @FXML
    private JFXButton Profile;
    @FXML
    private AnchorPane Menu;
    @FXML
    private JFXButton Login;
    @FXML
    private JFXButton Signup;
    @FXML
    private JFXButton Exit;
    private static GridPane GridlistPane;
    private final JSONreader json = new JSONreader();
    private static final ArrayList<Integer>  MyList = new ArrayList<>();
    private boolean connected;
    private String UserID;

    static GridPane getmylistGrid_(){ return GridlistPane; }
    static ArrayList<Integer>getmylist(){
        return MyList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            items fitem = json.getMostTrends();
            Main_img.setBackground(new Background(new BackgroundImage(new Image("http://image.tmdb.org/t/p/w780/"+fitem.getBackground(),900,300,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
            Main_img.setId(""+fitem.getID());
            Main_describtion.setText(fitem.getDescription());
            Main_duration.setText("duration "+(fitem.getDuration()>60?(fitem.getDuration()/60+"h"+ fitem.getDuration()%60+"min"):fitem.getDuration()+"min"));
            Main_title.setText(fitem.getTitle());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        List<items> items = new ArrayList<items>(){
            {
                try {
                    this.addAll(json.get_Trends());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

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
        GridlistPane = gridList;
        Profile.addEventHandler(MouseEvent.MOUSE_ENTERED,e->Menu.toFront());
        Menu.addEventHandler(MouseEvent.MOUSE_EXITED,e->Menu.toBack());
    }

    public void PlayTrailer() {

    }

    public void Mylist() throws JSONException, IOException {
        Mylist.toFront();
        JSONreader json = new JSONreader();
        if(!gridList.getChildren().isEmpty())
            gridList.getChildren().removeAll(gridList.getChildren());
        int r = 0;
        for(int id:MyList){
            if(json.CheckType(id).equals("movie")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/Mylist.fxml"));

                AnchorPane ap = loader.load();

                Mylist mylist = loader.getController();
                mylist.setData(json.getMovieDetails(id));

                gridList.add(ap, 0, r++);
                GridPane.setMargin(ap, new Insets(40, 50, 10, 10));
            }else{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/Mylist.fxml"));

                AnchorPane ap = loader.load();

                Mylist mylist = loader.getController();
                mylist.setData(json.getSeriesDetails(id));

                gridList.add(ap, 0, r++);
                GridPane.setMargin(ap, new Insets(40, 50, 10, 10));
            }
        }
    }

    public void Home() {
        Home.toFront();
    }

    public void Addlist(ActionEvent event)  {
    Pane pane = (Pane) ((JFXButton) event.getSource()).getParent().getParent();

    if(!MyList.contains(Integer.valueOf(pane.getId().replaceAll("[^0-9]","")))) {
        MyList.add(Integer.valueOf(pane.getId().replaceAll("[^0-9]", "")));
        ((JFXButton) event.getSource()).setText("Added");
        MainAdd_img.setImage(new Image("/images/check.png"));
        MainAdd_img.setFitWidth(12);
        MainAdd_img.setFitHeight(12);
    }else{
        MyList.remove((Integer) Integer.parseInt(pane.getId()));
        ((JFXButton) event.getSource()).setText("Add list");
        MainAdd_img.setImage(new Image("/images/plus.png"));
        MainAdd_img.setFitWidth(8);
        MainAdd_img.setFitHeight(8);
        }
    }

    public void Profile(ActionEvent event) throws IOException {
        JFXButton button = (JFXButton) event.getSource();
        if(button.getText().equals("Login")) {
            UserID = null;
            connected = false;
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Login/login.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 275, 360));
            primaryStage.show();
            primaryStage.addEventHandler(ActionEvent.ANY,e->{
                UserID = ((Login.Controller) loader.getController()).GetID();
                connected = UserID!=null;});
        }else if(button.getText().equals("Signup")){
            System.out.println("ID : "+UserID+"\nConnected : "+connected);
        }else
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }
}