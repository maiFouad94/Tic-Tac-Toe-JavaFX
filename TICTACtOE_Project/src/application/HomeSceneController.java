
package application;

import static application.Main.mainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import core.Board;
import core.*;
import core.Game;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author Mahmo
 */
public class HomeSceneController implements Initializable {

    public static MainController mainController;
    public static Game game;
    public static String temp1, temp2, temp3, tempN;
    public static TextInputDialog dialog, dialogP1, dialogP2, dialogN;
    private Database myDB;
    public Optional<String> PlayerTwoName;
    public Optional<String> PlayerOneName;
    public Optional<String> hostFromClient;
    @FXML
    private Label singlePlayer;
    @FXML
    private Label multiPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    /**
     * handle selection single player
     */
    private void singlePlayerHandle(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        try {
            Parent root = loader.load();
            mainController = loader.getController();
            myDB = new Database();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            dialog = new TextInputDialog("walter");
            dialog.setTitle("Tic Tac Toe");
            dialog.setHeaderText("Look, We Need To know your Name");
            dialog.setContentText("Please enter your name:");
            PlayerOneName = dialog.showAndWait();
            if (PlayerOneName.isPresent()) {
                temp3 = PlayerOneName.get();
                myDB.playerName(PlayerOneName.get());
                myDB.setGameType("single");
               
            }

        } catch (NullPointerException e) {

        }
        mainController.pVc.selectedProperty().set(true);
        mainController.newGame();

    }

    @FXML
   /**
     *  handle selection multiPlayer
     */
    private void multiPlayerHandle(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        try {
            Parent root = loader.load();
            mainController = loader.getController();
            myDB = new Database();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            dialogP1 = new TextInputDialog();
            dialogP1.setTitle("Tic Tac Toe");
            dialogP1.setHeaderText("Look, We Need To know your Name");
            dialogP1.setContentText("Please Enter Player 1 Name::");
            PlayerOneName = dialogP1.showAndWait();
            if (PlayerOneName.isPresent()) {
                temp1 = PlayerOneName.get();
                myDB.playerName(PlayerOneName.get());
            }
            dialogP2 = new TextInputDialog();
            dialogP2.setTitle("Tic Tac Toe");
            dialogP2.setHeaderText("Hey!, We Wish You Enjoying Our Game ");
            dialogP2.setContentText("Please Enter Player 2 Name:");
            PlayerTwoName = dialogP2.showAndWait();
            if (PlayerTwoName.isPresent()) {
                temp2 = PlayerTwoName.get();
                myDB.playerName(PlayerTwoName.get());
                myDB.setGameType("multi");
            } //   
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeSceneController.mainController.pVp.selectedProperty().set(true);
        HomeSceneController.mainController.newGame();

    }

    @FXML
     /**
     * handle selection networked game
     */
    private void throughNetHandle(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        try {
            Parent root = loader.load();
            mainController = loader.getController();
            myDB = new Database();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            dialogN = new TextInputDialog();
            dialogN.setTitle("Tic Tac Toe");
            dialogN.setHeaderText("Look, We Need To know your Name");
            dialogN.setContentText("Please Enter Player 1 Name::");
            PlayerOneName = dialogN.showAndWait();
            if (PlayerOneName.isPresent()) {
                tempN = PlayerOneName.get();
                myDB.playerName(PlayerOneName.get());
                myDB.setGameType("networked");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.pVpN.selectedProperty().set(true);
        mainController.newGame();

    }

    @FXML
    /**
     * handle score
     */
    private void recentScoreHandle(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("highScores.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();

    }

    @FXML
   /**
     * about game
     */
    private void aboutHandle(MouseEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setContentText("Welcome Player to our humble game TIC TAC TOE !");
        about.setTitle("TIC TAC TOE!");
        about.show();
    }

    @FXML
    /**
     * exit
     */
    private void quitHandle(MouseEvent event) {
        System.exit(0);
    }

}
