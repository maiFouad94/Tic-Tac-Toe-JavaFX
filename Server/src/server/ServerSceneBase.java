package server;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public  class ServerSceneBase extends SplitPane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final AnchorPane anchorPane0;
    protected final ScrollPane Scroll;
    public static TextArea taLog;

    public ServerSceneBase() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        anchorPane0 = new AnchorPane();
        Scroll = new ScrollPane();
        taLog = new TextArea();

        setDividerPositions(0.5);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setOrientation(javafx.geometry.Orientation.VERTICAL);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(100.0);
        anchorPane.setPrefWidth(160.0);

        imageView.setFitHeight(196.0);
        imageView.setFitWidth(582.0);
        imageView.setLayoutX(95.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("logo.jpg"));

        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(100.0);
        anchorPane0.setPrefWidth(160.0);

        Scroll.setLayoutX(-1.0);
        Scroll.setPrefHeight(196.0);
        Scroll.setPrefWidth(598.0);

        taLog.setPrefHeight(200.0);
        taLog.setPrefWidth(596.0);
        Scroll.setContent(taLog);

        anchorPane.getChildren().add(imageView);
        getItems().add(anchorPane);
        anchorPane0.getChildren().add(Scroll);
        getItems().add(anchorPane0);

    }
}
