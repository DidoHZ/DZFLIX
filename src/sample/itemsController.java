package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.JSONException;

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
            JSONreader json = new JSONreader();
            try {
                items i = json.getDetails(item.getID());
                System.out.println("ID : "+item.getID());
                System.out.println("Title : "+i.getTitle());
                System.out.println("Duration : "+i.getDuration());
                System.out.println("Date : "+i.getDate());
                System.out.println("Description : "+i.getDescription()+"\n");
            } catch (IOException | JSONException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
