
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mahmo
 */
public class HighScoresController implements Initializable {

    @FXML
    private ImageView backIcon;
    @FXML
    private Button ReplayGame;
    @FXML
    private Label scoreLabel;
    Database myDb;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myDb = new Database();
        scoreLabel.setText(Integer.toString(myDb.getScore()));
        
    }

    @FXML
    /**
     * back to homepage
     */
    private void backHandle(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("homeScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    /**
     * show saved game
     */
    private void showUpReplay(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ReplayScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.show();

    }

}
