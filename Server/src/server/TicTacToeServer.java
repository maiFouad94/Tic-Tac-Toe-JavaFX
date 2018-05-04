package server;

/**
 *
 * @author Mahmoud
 */
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.stage.Stage;
import server.ServerSceneBase;
public class TicTacToeServer extends Application {
    
    ServerSceneBase server;
    private int sessionNo = 1; 

    @Override
    public void start(Stage primaryStage) {
     
      
        
     ServerSceneBase serverSceneBase = new ServerSceneBase();
     Scene scene = new Scene(serverSceneBase);
     primaryStage.setTitle("Tic-Tac-Toe Server");
     primaryStage.setScene(scene);
     primaryStage.show();
     primaryStage.setOnCloseRequest(event ->System.exit(0));
        new Thread(() -> {
            try {
                /**
                 * Create a server socket
                 */
                ServerSocket serverSocket = new ServerSocket(5005);
                Platform.runLater(() -> server.taLog.appendText(new Date()
                        + ": Server started at socket 5005\n"));
                while (true) {
                    Platform.runLater(() -> server.taLog.appendText(new Date()
                            + ": Wait for players to join session " + sessionNo + '\n'));

                    Socket player1 = serverSocket.accept();

                    Platform.runLater(() -> {
                        server.taLog.appendText(new Date() + ": Player 1 joined session "
                                + sessionNo + '\n');
                        server.taLog.appendText("Player 1's IP address"
                                + player1.getInetAddress().getHostAddress() + '\n');
                    });

                    new DataOutputStream(player1.getOutputStream()).writeInt(1);

                    Socket player2 = serverSocket.accept();

                    Platform.runLater(() -> {
                        server.taLog.appendText(new Date()
                                + ": Player 2 joined session " + sessionNo + '\n');
                        server.taLog.appendText("Player 2's IP address"
                                + player2.getInetAddress().getHostAddress() + '\n');
                    });

                    new DataOutputStream(player2.getOutputStream()).writeInt(2);
                    Platform.runLater(()
                            -> server.taLog.appendText(new Date()
                                    + ": Start a thread for session " + sessionNo++ + '\n'));

                    new Thread(new HandleASession(player1, player2)).start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();

        
    }

    
    /**
     * handling a new session
     */
    class HandleASession implements Runnable {

        private Socket player1;
        private Socket player2;

        private char[][] space = new char[3][3];

        private DataInputStream fPlayer1;
        private DataOutputStream tPlayer1;
        private DataInputStream fPlayer2;
        private DataOutputStream tPlayer2;

        private boolean continuePlay = true;

        /**
         * Construct a thread
         */
        public HandleASession(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    space[i][j] = ' ';
                }
            }
        }

        /**
         * Implement the run()
         */
        public void run() {
            try {

                DataInputStream fPlayer1 = new DataInputStream(player1.getInputStream());
                DataOutputStream tPlayer1 = new DataOutputStream(player1.getOutputStream());
                DataInputStream fPlayer2 = new DataInputStream(player2.getInputStream());
                DataOutputStream tPlayer2 = new DataOutputStream(player2.getOutputStream());
                tPlayer1.writeInt(1);
                while (true) {

                    int row = fPlayer1.readInt();
                    int column = fPlayer1.readInt();
                    space[row][column] = 'X';
                    if (Won('X')) {
                        tPlayer1.writeInt(1);
                        tPlayer2.writeInt(1);
                        sendMove(tPlayer2, row, column);
                        break;
                    } else if (noMoreSpace()) {
                        tPlayer1.writeInt(3);
                        tPlayer2.writeInt(3);
                        sendMove(tPlayer2, row, column);
                        break;
                    } else {

                        tPlayer2.writeInt(4);

                        sendMove(tPlayer2, row, column);
                    }
                    row = fPlayer2.readInt();
                    column = fPlayer2.readInt();
                    space[row][column] = 'O';

                    if (Won('O')) {
                        tPlayer1.writeInt(2);
                        tPlayer2.writeInt(2);
                        sendMove(tPlayer1, row, column);
                        break;
                    } else {

                        tPlayer1.writeInt(4);

                        sendMove(tPlayer1, row, column);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Send the move
         */
        private void sendMove(DataOutputStream out, int row, int column) throws IOException {
            out.writeInt(row); 
            out.writeInt(column); 
        }

        /**
         * check spaces
         */
        private boolean noMoreSpace() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (space[i][j] == ' ') {
                        return false; 
                    }
                }
            }

            return true;
        }

        /**
         * check won
         */
        private boolean Won(char token) {

            for (int i = 0; i < 3; i++) {
                if ((space[i][0] == token) && (space[i][1] == token) && (space[i][2] == token)) {
                    return true;
                }
            }

            /**
             * Check columns
             */
            for (int j = 0; j < 3; j++) {
                if ((space[0][j] == token) && (space[1][j] == token) && (space[2][j] == token)) {
                    return true;
                }
            }

            /**
             * Check diagonal
             */
            if ((space[0][0] == token) && (space[1][1] == token) && (space[2][2] == token)) {
                return true;
            }

            /**
             * Check sub diagonal
             */
            if ((space[0][2] == token) && (space[1][1] == token) && (space[2][0] == token)) {
                return true;
            }

            return false;
        }
    }


public static void  main (String [] args)
{
  launch(args);
}
}