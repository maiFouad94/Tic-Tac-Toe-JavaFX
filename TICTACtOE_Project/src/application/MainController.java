package application;

import java.net.URL;
import java.util.ResourceBundle;

import core.Game;
import core.Space;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController extends TabPane implements Initializable {

    
    @FXML
    private Label winText;
    @FXML
    private ImageView playerOneImage;
    @FXML
    private ImageView playerTwoImage;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Tab test;

    @FXML
    private ToggleGroup pawnSets;

    // variables
    private Game game;
    private Thread gameThread;
    private int rows = 3;
    private int gridSize;
    public static int morder;
    private Database myDB;

    public ImageSet[] imageSets = new ImageSet[]{
        new ImageSet("o1.png", "x1.png"),
        new ImageSet("o2.png", "x2.png"),
        new ImageSet("o3.png", "x3.png")
    };
    public ImageSet currentImageSet = imageSets[0];
    @FXML
    public RadioButton pVc;
    @FXML
    private ToggleGroup gameMode;
    @FXML
    public RadioButton pVpN;
    @FXML
    public RadioButton pVp;
    @FXML
    private Tab tabPane;
    @FXML
    private Button goToHomeBtn;

    @FXML
    private void mouseClicked(MouseEvent event) {
       int temp = gridSize / rows;
		game.tryToPutSomething((int) event.getX() / temp, (int) event.getY() / temp);
    }
/**
 * put symbol on grid
 */
    public void putSymbol(Space mark, int x, int y) {
        int temp = gridSize / rows;
        myDB = new Database();
        if (mark == Space.X) {
            myDB.movesGame("x", x, y);
        }
        if (mark == Space.O) {
            myDB.movesGame("o", x, y);
        }
        x *= temp;
        y *= temp;

        ImageView imageView = new ImageView(mark == Space.X ? currentImageSet.getXImage() : currentImageSet.getOImage());
        imageView.setScaleX((double) (3 * (temp) / 4) / imageView.getImage().getWidth());
        imageView.setScaleY((double) (3 * (temp) / 4) / imageView.getImage().getHeight());
        imageView.relocate(x + temp / 2 - imageView.getImage().getWidth() / 2,
                y + temp / 2 - imageView.getImage().getHeight() / 2);

        ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(300), imageView);
        scaleTrans.setByX(0.5);
        scaleTrans.setByY(0.5);
        scaleTrans.setAutoReverse(true);
        scaleTrans.setCycleCount(2);
        scaleTrans.play();

        FadeTransition ft = new FadeTransition(Duration.millis(300), imageView);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();

        gamePane.getChildren().add(imageView);
        if (game.isEnd()) {
            gameEnd();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        goToHomeBtn.setText("Back To Home");
        gridSize = (int) gamePane.getPrefHeight();
        newGame();
    }

    @SuppressWarnings("deprecation")
    /**
     * close
     */
    public void onClose() {
        gameThread.stop();
    }
/**
 * draw grid
 */
    private void drawGrid() {
        Line line;
        int temp = 10;
        for (int i = 1; i < rows; i++) {
            line = new Line(gridSize / rows * i, temp, gridSize / rows * i, gridSize - temp);
            gamePane.getChildren().add(line);
        }
        for (int i = 1; i < rows; i++) {
            line = new Line(temp, gridSize / rows * i, gridSize - temp, gridSize / rows * i);
            gamePane.getChildren().add(line);
        }
    }
/**
 * select game type
 */
    public String getGameType() {
        String gtype = new String();
        if (pVc.isSelected()) {
            gtype = "single";

        }
        if (pVp.isSelected()) {
            gtype = "multi";

        }
        if (pVpN.isSelected()) {
            gtype = "networked";
        }
        return gtype;
    }

    @SuppressWarnings("deprecation")
            /**
             * start new game
             */
    void newGame() {

        if (gameThread != null) {
            gameThread.stop();
            gamePane.getChildren().clear();
        }
        winText.setText("");
        rows = 3;
        currentImageSet = imageSets[pawnSets.getToggles().indexOf((pawnSets.getSelectedToggle()))];
        playerOneImage.setImage(currentImageSet.getXImage());
        playerTwoImage.setImage(currentImageSet.getOImage());

        game = new Game(rows, pVc.isSelected(), pVp.isSelected(), pVpN.isSelected());
        gameThread = new Thread(game);
        gameThread.start();
        test.getTabPane().getSelectionModel().select(test);
        drawGrid();
        
    }

    @SuppressWarnings("deprecation")
    /**
     * check cases for endgame
     */
    public void gameEnd() {
        if (game.getWinSpaceBeg() != null) {
            int temp = gridSize / rows;

            Line line = new Line(game.getWinSpaceBeg().getX() * temp + temp / 2, game.getWinSpaceBeg().getY() * temp + temp / 2,
                    game.getWinSpaceBeg().getX() * temp + temp / 2, game.getWinSpaceBeg().getY() * temp + temp / 2);
            line.setStroke(new Color(1, 0, 1, 1));
            line.setStrokeWidth(10);

            gamePane.getChildren().add(line);
                              Timeline timeline = new Timeline (new KeyFrame(Duration.seconds(1),
		             new KeyValue (line.endXProperty(), game.getWinSpaceEnd().getX() * temp + temp / 2),
			new KeyValue (line.endYProperty(), game.getWinSpaceEnd().getY() * temp + temp / 2)));
			timeline.play();
        }
        if (game.getEndReason() != null) {
            switch (game.getEndReason()) {
                case NO__MORE_SPACE:
                    winTextAnimation("No more space");
                    break;
                case X_WINS:
                    myDB.setXScore();
                    winTextAnimation("X wins");
                    break;
                case O_WINS:
                    winTextAnimation("O wins");
                    break;
                
            }
        } else {
            gameThread.stop();
        }
      
    }
/**
 * start order of moves by zero
 */
    public void setMOrder() {
        morder = 0;
    }
/**
 * animation for symbal 
 */
    void winTextAnimation(String text) {
        winText.setText(text);

        ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(300), winText);
        scaleTrans.setByX(0.5);
        scaleTrans.setByY(0.5);
        scaleTrans.setAutoReverse(true);
        scaleTrans.setCycleCount(2);
        scaleTrans.play();

        FadeTransition ft = new FadeTransition(Duration.millis(300), winText);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML
  /**
   * start new game
   */
    void createNewGame(ActionEvent event) {
        newGame();
        setMOrder();
    }

    @FXML
   /**
    * normal case for exit game (0)
    */
    void exitGame(ActionEvent event) {
        onClose();
        System.exit(0);
    }

    @FXML
    /**
     * go home page
     */
    private void goToHome(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("homeScene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

}
