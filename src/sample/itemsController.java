package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class itemsController implements Initializable {
    @FXML
    private ImageView imgview;

    /*@FXML
    private Pane Pan_img;
     */

    @FXML
    private VBox touched;

    @FXML
    private JFXButton Moreinfo;

    @FXML
    private Label title_lbl;

    @FXML
    private Label date_lbl;

    @FXML
    private Label rate_lbl;

    private items item;

    public void setData(items item){
        this.item = item;
        title_lbl.setText(item.getTitle());
        date_lbl.setText(item.getDate());
        //Pan_img.setStyle("-fx-background-image: url(\""+item.getImgUrl()+"\"); -fx-background-repeat: no-repeat; -fx-background-size: 200 232;");
        rate_lbl.setText(item.getRate());
        imgview.setImage(new Image(item.getImgUrl()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        touched.addEventHandler(MouseEvent.MOUSE_ENTERED,e->{
            Moreinfo.setDisable(false);
            Moreinfo.setVisible(true);
        });
        touched.addEventHandler(MouseEvent.MOUSE_EXITED,event-> {
            Moreinfo.setVisible(false);
            Moreinfo.setDisable(true);
        });
        Moreinfo.addEventHandler(MouseEvent.MOUSE_CLICKED,event-> {
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

            scene.setFill(Color.TRANSPARENT);
            Details details = loader.getController();
            details.setData(stage,item.getID());

            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        });
    }
}
