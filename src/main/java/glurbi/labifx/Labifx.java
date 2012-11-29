package glurbi.labifx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Labifx extends Application {

    @Override
    public void start(final Stage primaryStage) {
        
        /*
        Runnable fullScreenAction = new Runnable() {
            private boolean fullScreen = false;
            @Override
            public void handle(MouseEvent t) {
                fullScreen = !fullScreen;
                primaryStage.setFullScreen(fullScreen);
                if (fullScreen) {
                    ((Text) t.getSource()).setText("Window mode");
                } else {
                    ((Text) t.getSource()).setText("Full screen mode");
                }
            }
        };
        */

        Runnable exitAction = new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        };

        Menu mainMenu = new Menu();
        mainMenu.addEntry("Play", new Runnable() { public void run() {}});
        mainMenu.addEntry("Edit", new Runnable() { public void run() {}});
        mainMenu.addEntry("Options", new Runnable() { public void run() {}});
        mainMenu.addEntry("Exit", exitAction);
        
        // TODO: report bug?
        //final Text fullScreenText = menuItemBuilder.onMouseClicked(fullScreenHandler).text("Full screen mode").build();
        //final Text fullScreenText = menuItemBuilder.text("Full screen mode").build();
        //fullScreenText.setOnMouseClicked(fullScreenHandler);

        final Image sand = new Image(this.getClass().getResourceAsStream("sand.jpg"));
        final ImageView background = ImageViewBuilder.create().image(sand).fitHeight(600).build();
        final StackPane root = new StackPane();
        root.getChildren().addAll(background, mainMenu);
        final Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        
        ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                double width = primaryStage.widthProperty().get();
                double height = primaryStage.heightProperty().get();
                background.fitWidthProperty().set(width);
                background.fitHeightProperty().set(height);
            }
        };
        
        primaryStage.widthProperty().addListener(sizeListener);
        primaryStage.heightProperty().addListener(sizeListener);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
