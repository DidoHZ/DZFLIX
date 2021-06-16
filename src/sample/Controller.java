package sample;

import Login.GetData;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private static ArrayList<Integer>  MyList = new ArrayList<>();
    public static boolean connected;
    public static String UserID;

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

        try {
            FillGridList(json.get_Trends("all"));
        } catch (Exception e) { e.printStackTrace();}

        GridlistPane = gridList;
        Profile.addEventHandler(MouseEvent.MOUSE_ENTERED,e->Menu.toFront());
        Menu.addEventHandler(MouseEvent.MOUSE_EXITED,e->Menu.toBack());
    }

    public void FillGridList(List<items> items){
        int c = 0,r = 1;
        if(!grid.getChildren().isEmpty())
            grid.getChildren().removeAll(grid.getChildren());
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

    public void PlayTrailer() {

    }

    public void Mylist() throws JSONException, IOException {
        Mylist.toFront();
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
    int ItemID = Integer.parseInt(pane.getId().replaceAll("[^0-9]",""));
    if(!MyList.contains(ItemID)){
        System.out.println("UserID : "+(UserID!=null?UserID:"null"));
        if(connected){
            GetData.AddToUserList(UserID,ItemID,json.CheckType(ItemID));
        }
        MyList.add(Integer.valueOf(pane.getId().replaceAll("[^0-9]", "")));
        ((JFXButton) event.getSource()).setText("Added");
        MainAdd_img.setImage(new Image("/images/check.png"));
        MainAdd_img.setFitWidth(12);
        MainAdd_img.setFitHeight(12);
    }else{
        if(connected){
            GetData.DeleteFromUserList(UserID,ItemID);
        }
        MyList.remove((Integer) ItemID);
        ((JFXButton) event.getSource()).setText("Add list");
        MainAdd_img.setImage(new Image("/images/plus.png"));
        MainAdd_img.setFitWidth(8);
        MainAdd_img.setFitHeight(8);
        }
    }

    public void Profile(ActionEvent event) throws IOException {
        JFXButton button = (JFXButton) event.getSource();
        if(button.getText().equals("Login")) {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Login/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 275, 370);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.initOwner(((Node)event.getSource()).getScene().getWindow());
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.addEventHandler(ActionEvent.ANY,e->{
                UserID = ((Login.Controller) loader.getController()).GetID();
                if(connected = UserID!=null){
                    GridlistPane.getChildren().removeAll(GridlistPane.getChildren());
                    MyList = new ArrayList<>();
                    MyList.addAll(GetData.getUserList(UserID));
                }
            });
        }else if(button.getText().equals("Signup")){
            System.out.println("ID : "+UserID+"\nConnected : "+connected);
        }else
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }

    public void Trend(MouseEvent event) throws Exception {
        RadioButton rb = (RadioButton) event.getSource();
        if((rb.getText().equals("Trends Now") || rb.getText().equals("Popular")) && rb.isSelected())
            FillGridList(json.get_Trends("all"));
        if(rb.getText().equals("Movies") && rb.isSelected())
            FillGridList(json.get_Trends("Movies"));
        if(rb.getText().equals("Series") && rb.isSelected())
            FillGridList(json.get_Trends("Series"));
    }
}