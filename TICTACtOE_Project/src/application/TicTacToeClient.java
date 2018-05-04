
package application;

/**
 *
 * @author NaDa DaHaB
 */
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import core.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import application.HomeSceneController;

public class TicTacToeClient extends Application {

    Stage stage;
    Game game;
    HomeSceneController HSC;
    Database myDB = new Database();
    private ImageSet[] imageSets = new ImageSet[]{
        new ImageSet("o1.png", "x1.png"),
        new ImageSet("o2.png", "x2.png"),
        new ImageSet("o3.png", "x3.png")};
    private ImageSet currentImageSet = imageSets[0];

    public TicTacToeClient(Stage s) {

        stage = s;
        rets(s);
    }
/**
 * get stage 
 */
    public Stage rets(Stage s) {
        return s;
    }

//check turn
    private boolean myMove = false;
    public static int mNorder;
    private String symbal = " ";
    private String symbal2 = " ";
    private Cell[][] space = new Cell[3][3];
    private Label label1 = new Label();
    private Label label2 = new Label();
    private int rowSelected;
    private int columnSelected;
    private DataInputStream fServer;
    private DataOutputStream tServer;

/**
 * check continue
 */
    private boolean continuePlay = true;
    private boolean wait = true;
    private String ipserver = "localhost";

    @Override 
    public void start(Stage primaryStage) {

    }
/**
 * draw grid
 */
    public Scene myNet() {
       
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pane.add(space[i][j] = new Cell(i, j), j, i);
            }
        }
        AnchorPane anchorPane = new AnchorPane();
        Button newGameBtn;
        Label player1;
        Button HomeScreenBtn;
        Label player2;
        ImageView player1Img;
        ImageView player2Img;

        newGameBtn = new Button();
        player1 = new Label();
        HomeScreenBtn = new Button();
        player2 = new Label();
        player1Img = new ImageView();
        player2Img = new ImageView();

        anchorPane.setId("AnchorPane");
        anchorPane.setPrefHeight(423.0);
        anchorPane.setPrefWidth(200.0);

        newGameBtn.setLayoutX(66.0);
        newGameBtn.setLayoutY(265.0);
        newGameBtn.setOnAction((event) -> {

            Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
            TicTacToeClient root  = new TicTacToeClient(st);
            Scene scene =  root.myNet();
            st.setScene(scene);
            st.show();
           HomeSceneController.mainController.pVpN.selectedProperty().set(true);
        });
        newGameBtn.setText("New Game");

        player1.setLayoutX(20.0);
        player1.setLayoutY(67.0);
        player1.setMinHeight(16);
        player1.setMinWidth(69);
        player1.setPrefHeight(25.0);
        player1.setPrefWidth(69.0);
        player1.setText("Player X");
        player1.setFont(new Font("System Bold", 14.0));

        HomeScreenBtn.setLayoutX(60.0);
        HomeScreenBtn.setLayoutY(315.0);
        HomeScreenBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("homeScene.fxml"));
                Scene scene = new Scene(root);
                Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();

                st.setScene(scene);
                st.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        HomeScreenBtn.setText("Home Screen");

        player2.setLayoutX(138.0);
        player2.setLayoutY(72.0);
        player2.setMinHeight(16);
        player2.setMinWidth(69);
        player2.setText("Player O");
        player2.setFont(new Font("System Bold", 13.0));

        player1Img.setFitHeight(81.0);
        player1Img.setFitWidth(75.0);
        player1Img.setLayoutX(20.0);
        player1Img.setLayoutY(105.0);
        player1Img.setPickOnBounds(true);
        player1Img.setPreserveRatio(true);
        player1Img.setImage(currentImageSet.getXImage());

        player2Img.setFitHeight(81.0);
        player2Img.setFitWidth(75.0);
        player2Img.setLayoutX(116.0);
        player2Img.setLayoutY(105.0);
        player2Img.setPickOnBounds(true);
        player2Img.setPreserveRatio(true);
        player2Img.setImage(currentImageSet.getOImage());

        anchorPane.getChildren().add(newGameBtn);
        anchorPane.getChildren().add(player1);
        anchorPane.getChildren().add(HomeScreenBtn);
        anchorPane.getChildren().add(player2);
        anchorPane.getChildren().add(player1Img);
        anchorPane.getChildren().add(player2Img);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(label1);
        borderPane.setCenter(pane);
        borderPane.setRight(anchorPane);
        borderPane.setBottom(label2);
        Stage newStage = this.stage;
        Scene scene = new Scene(borderPane, 600, 400);
        newStage.setScene(scene);
        newStage.show();
        connectToServer();
        return scene;
    }
/**
 * connect to server
 */
    private void connectToServer() {
        try {
            Socket socket = new Socket(ipserver, 5005);
            fServer = new DataInputStream(socket.getInputStream());
            tServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        new Thread(() -> {
            try {
                int player = fServer.readInt();
/**
 * check player
 */
                if (player == 1) {
                    symbal = "x";
                    symbal2 = "o";
                    Platform.runLater(() -> {
                        label1.setText("Player 1 with token 'x'");
                        label2.setText("Waiting for player 2 to join");
                    });

                    fServer.readInt();
                    Platform.runLater(()
                            -> label2.setText("Player 2 has joined. I start first"));
                    myMove = true;
                } else if (player == 2) {
                    symbal = "o";
                    symbal2 = "x";
                    Platform.runLater(() -> {
                        label1.setText("Player 2 with token 'o'");
                        label2.setText("Waiting for player 1 to move");
                    });
                }

                while (continuePlay) {
                    if (player == 1) {
                        waitForPlayerAction();
                        sendMove();
                        receiveInfoFromServer(); 
                    } else if (player == 2) {
                        receiveInfoFromServer(); 
                        waitForPlayerAction(); 
                        sendMove(); 
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Waiting player 
     */
    private void waitForPlayerAction() throws InterruptedException {
        while (wait) {
            Thread.sleep(100);
        }

        wait = true;
    }

    public void setMNOrder() {
        mNorder = 0;
    }

    /**
     * Send move to server
     */
    private void sendMove() throws IOException {
        tServer.writeInt(rowSelected); // Send the selected row
        tServer.writeInt(columnSelected); // Send the selected column
        myDB.movesNGame(symbal, rowSelected, columnSelected);
    }

    /**
     * receive from the server
     */
    private void receiveInfoFromServer() throws IOException {

        int status = fServer.readInt();

        if (status == 1) {
            myDB.setXScore();
            continuePlay = false;
            if (symbal == "x") {
                Platform.runLater(() -> label2.setText("I won! (X)"));
            } else if (symbal == "o") {
                Platform.runLater(()
                        -> label2.setText("Player 1 (X) has won!"));
                receiveMove();
            }
        } else if (status == 2) {
            continuePlay = false;
            if (symbal == "o") {
                Platform.runLater(() -> label2.setText("I won! (O)"));
            } else if (symbal == "x") {
                Platform.runLater(()
                        -> label2.setText("Player 2 (O) has won!"));
                receiveMove();
            }
        } else if (status == 3) {
            continuePlay = false;
            Platform.runLater(()
                    -> label2.setText("Game is over, no winner!"));

            if (symbal == "o") {
                receiveMove();
            }
        } else {
            receiveMove();
            Platform.runLater(() -> label2.setText("My turn"));
            myMove = true; 
        }
    }

    private void receiveMove() throws IOException {
        int row = fServer.readInt();
        int column = fServer.readInt();
        Platform.runLater(() -> space[row][column].setToken(symbal2));
        myDB.movesNGame(symbal2, row, column);
    }

/**
 * class cell
 */
    public class Cell extends Pane {
        private int row;
        private int column;
        private String token = " ";

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            this.setMinSize(120, 120);// What happens without this?
            setStyle("-fx-border-color: black"); // Set space's border
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        /**
         * Return token
         */
        public String getToken() {
            return token;
        }

        /**
         * set new token
         */
        public void setToken(String c) {
            token = c;
            repaint();
        }

        protected void repaint() {
            if (token == "x") {
                ImageView ImgXview = new ImageView(currentImageSet.getXImage());
                ImgXview.setFitHeight(120);
                ImgXview.setFitWidth(120);
                ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(300), ImgXview);
                scaleTrans.setByX(0.5);
                scaleTrans.setByY(0.5);
                scaleTrans.setAutoReverse(true);
                scaleTrans.setCycleCount(2);
                scaleTrans.play();

                FadeTransition ft = new FadeTransition(Duration.millis(300), ImgXview);
                ft.setFromValue(0.1);
                ft.setToValue(1.0);
                ft.play();
                this.getChildren().addAll(ImgXview);
            } else if (token == "o") {
                ImageView imgView = new ImageView(currentImageSet.getOImage());
                imgView.setFitHeight(120);
                imgView.setFitWidth(120);
                ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(300), imgView);
                scaleTrans.setByX(0.5);
                scaleTrans.setByY(0.5);
                scaleTrans.setAutoReverse(true);
                scaleTrans.setCycleCount(2);
                scaleTrans.play();

                FadeTransition ft = new FadeTransition(Duration.millis(300), imgView);
                ft.setFromValue(0.1);
                ft.setToValue(1.0);
                ft.play();

                this.getChildren().add(imgView); 
            }
        }

        /**
        * handle mouse click
        */
        private void handleMouseClick() {

            if (token == " " && myMove) {
                setToken(symbal); 
                myMove = false;
                rowSelected = row;
                columnSelected = column;
                label2.setText("Waiting for the other player to move");
                wait = false; 
            }
        }
    }

}
