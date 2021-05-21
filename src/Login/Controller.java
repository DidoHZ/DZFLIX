package Login;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

    public TextField User_fld;
    public PasswordField Pass_fld;
    public Label Message_lbl;
    public static String ID;

    static void setID(String str){
        ID = str;
    }
    public String GetID(){
        return ID;
    }

    //login Alert
    private void Alert_(Boolean status){
        Message_lbl.setTextFill(status?Color.GREEN:Color.TOMATO);
        Message_lbl.setText(status?"Login Successfully":"Incorrect Mail/Password.");
    }

    //Login Mouse Pressed Action
    public void Login() {
        //Alert_(Database.Contains(User_fld.getText()) && Database.Check(User_fld.getText(),MD5.getMd5(Pass_fld.getText())));
        Alert_(GetData.Login(User_fld.getText(),MD5.getMd5(Pass_fld.getText())));
    }

    public void NewAccount(MouseEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Creat.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Connection : "+GetData.GetConnection().getMetaData().getURL());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}