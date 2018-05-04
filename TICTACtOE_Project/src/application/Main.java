package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static MainController mainController;
	public static TicTacToeClient tttc;
	@Override
	public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScene.fxml"));
		Parent root;
		try {
			root = loader.load();
                        tttc = new TicTacToeClient(stage);
			Scene s = new Scene (root);
			stage.setScene(s);
                        stage.setTitle("Tic -Tac -Toe");
			stage.setResizable(false);
			stage.show();
                        
                        stage.setOnCloseRequest(event -> System.exit(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop () {
		mainController.onClose();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
