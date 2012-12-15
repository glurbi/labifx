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

    private static final String BACK_TO_MAIN_MENU_MENU_ITEM = "Back to main menu...";
	private static final String EXIT_MENU_ITEM = "Exit";
	private static final String OPTIONS_MENU_ITEM = "Options";
	private static final String EDIT_MENU_ITEM = "Edit";
	private static final String PLAY_MENU_ITEM = "Play";
	private static final String WINDOW_MODE_MENU_ITEM = "Window mode";
    private static final String FULL_SCREEN_MODE_MENU_ITEM = "Full Screen Mode";

    @Override
    public void start(final Stage primaryStage) {
        
        final Image sand = new Image(this.getClass().getResourceAsStream("sand.jpg"));
        final ImageView background = ImageViewBuilder.create().image(sand).fitHeight(600).build();
        final StackPane root = new StackPane();
        final Scene scene = new Scene(root, 800, 600);
        final Menu mainMenu = new Menu();
        final Menu playMenu = new Menu();
        final Menu editMenu = new Menu();
        final Menu optionsMenu = new Menu();
        
        final Runnable fullScreenAction = new Runnable() {
            private boolean fullScreen = false;
            @Override
            public void run() {
                fullScreen = !fullScreen;
                primaryStage.setFullScreen(fullScreen);
                if (fullScreen) {
                    optionsMenu.renameEntry(FULL_SCREEN_MODE_MENU_ITEM, WINDOW_MODE_MENU_ITEM);
                } else {
                    optionsMenu.renameEntry(WINDOW_MODE_MENU_ITEM, FULL_SCREEN_MODE_MENU_ITEM);
                }
            }
        };

        final Runnable exitAction = new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        
        class SwapMenuAction implements Runnable {
        	private final Menu from;
        	private final Menu to;
        	public SwapMenuAction(Menu from, Menu to) {
        		this.from = from;
        		this.to = to;
        	}
            @Override
            public void run() {
                from.uninstall(root);
                to.install(root);
            }
        };
        
        final ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                double width = primaryStage.widthProperty().get();
                double height = primaryStage.heightProperty().get();
                background.fitWidthProperty().set(width);
                background.fitHeightProperty().set(height);
            }
        };
        
        root.getChildren().addAll(background);
        mainMenu.install(root);
        primaryStage.setScene(scene);
        
        mainMenu.addEntry(PLAY_MENU_ITEM, new SwapMenuAction(mainMenu, playMenu));
        mainMenu.addEntry(EDIT_MENU_ITEM, new SwapMenuAction(mainMenu, editMenu));
        mainMenu.addEntry(OPTIONS_MENU_ITEM, new SwapMenuAction(mainMenu, optionsMenu));
        mainMenu.addEntry(EXIT_MENU_ITEM, exitAction);

        playMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(playMenu, mainMenu));
        
        editMenu.addEntry("New Labyrinth...", new Runnable() { public void run() {}});
        editMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(editMenu, mainMenu));
        
        optionsMenu.addEntry(FULL_SCREEN_MODE_MENU_ITEM, fullScreenAction);
        optionsMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(optionsMenu, mainMenu));
        
        // TODO: report bug?
        //final Text fullScreenText = menuItemBuilder.onMouseClicked(fullScreenHandler).text("Full screen mode").build();
        //final Text fullScreenText = menuItemBuilder.text("Full screen mode").build();
        //fullScreenText.setOnMouseClicked(fullScreenHandler);

        primaryStage.widthProperty().addListener(sizeListener);
        primaryStage.heightProperty().addListener(sizeListener);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

