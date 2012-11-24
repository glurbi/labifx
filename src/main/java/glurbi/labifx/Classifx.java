package glurbi.labifx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

public class Classifx extends Application {

    @Override
    public void start(final Stage primaryStage) {
        
        final GaussianBlur gaussianBlur = new GaussianBlur(5.0);
        final Color menuColor = Color.BROWN;
        final DropShadow dropShadow = new DropShadow(20.0, menuColor);
        final Font font = Font.font("Verdana", 60);

        final EventHandler<MouseEvent> blurHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                ((Text) t.getSource()).setEffect(gaussianBlur);
            }
        };

        final EventHandler<MouseEvent> fullScreenHandler = new EventHandler<MouseEvent>() {
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

        final EventHandler<MouseEvent> deblurHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                ((Text) t.getSource()).setEffect(dropShadow);
            }
        };

        final EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                System.exit(0);
            }
        };

        final TextBuilder menuItemBuilder = TextBuilder.create().font(font)
                .effect(gaussianBlur).onMouseEntered(deblurHandler)
                .onMouseExited(blurHandler).fill(menuColor);

        final Text startText = menuItemBuilder.text("Start").build();
        // TODO: report bug?
        //final Text fullScreenText = menuItemBuilder.onMouseClicked(fullScreenHandler).text("Full screen mode").build();
        final Text fullScreenText = menuItemBuilder.text("Full screen mode").build();
        fullScreenText.setOnMouseClicked(fullScreenHandler);
        final Text exitText = menuItemBuilder.text("Exit").build();
        exitText.setOnMouseClicked(exitHandler);

        final VBox menuBox = VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).build();
        final BorderPaneBuilder bpb = BorderPaneBuilder.create();
        menuBox.getChildren().addAll(
                bpb.center(startText).build(),
                bpb.center(fullScreenText).build(),
                bpb.center(exitText).build());

        final Image sand = new Image(this.getClass().getResourceAsStream("sand.jpg"));
        final ImageView background = ImageViewBuilder.create().image(sand).fitHeight(600).build();
        final StackPane root = new StackPane();
        root.getChildren().addAll(background, menuBox);
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
