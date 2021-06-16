package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Creat {

    public PasswordField Pass_fld,Pass_fld1;
    public TextField User_fld;
    public Label User_lbl,Pass_lbl;

    //Singup Alert
    private void Singup(Boolean status){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Signup");
        alert.setContentText("You "+(status?"Succeed":"Failed")+" to Signup.");
        ButtonType okButton = new ButtonType("Ok");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    public void SingUp(MouseEvent event) throws IOException {
        if(User_lbl.getText().equals("") && Pass_lbl.getText().equals("") && !User_fld.getText().isEmpty() && !Pass_fld.getText().isEmpty()) {
            //Database.Add(User_fld.getText(), MD5.getMd5(Pass_fld.getText()));
            try {
                GetData.signup(User_fld.getText(), MD5.getMd5(Pass_fld.getText()));
            }catch (Exception e) {
                Singup(false); return;
            }
            Singup(true);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }else
            Singup(false);
    }

    public void BackAction(MouseEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void UserAction() {
        if(GetData.CheckUser(User_fld.getText())) {
            if (!User_lbl.getText().contains("*Already Used"))
                User_lbl.setText("*Already Used");
        }else
            User_lbl.setText(User_lbl.getText().replace("*Already Used",""));
    }

    public void ConfirmeAction() {
        if(!Pass_fld.getText().equals(Pass_fld1.getText())){
            if (!Pass_lbl.getText().contains("*Don't Match"))
                Pass_lbl.setText("*Don't Match");
        }else if(Pass_fld.getText().equals(Pass_fld1.getText()) || Pass_fld.getText().isEmpty())
            Pass_lbl.setText(Pass_lbl.getText().replace("*Don't Match",""));
    }

    public void exit(ActionEvent event) {
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }
}