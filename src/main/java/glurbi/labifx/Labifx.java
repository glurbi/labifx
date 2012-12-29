package glurbi.labifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Labifx extends Application {

    private static final String SMALL_SIZE_MENU_ITEM = "Small size";
    private static final String MEDIUM_SIZE_MENU_ITEM = "Medium size";
    private static final String LARGE_SIZE_MENU_ITEM = "Large size";
    private static final String BACK_TO_EDIT_MENU_MENU_ITEM = "Back to edit menu...";
    private static final String NEW_LABYRINTH_MENU_ITEM = "New Labyrinth...";
    private static final String BACK_TO_MAIN_MENU_MENU_ITEM = "Back to main menu...";
    private static final String EXIT_MENU_ITEM = "Exit";
    private static final String OPTIONS_MENU_ITEM = "Options";
    private static final String EDIT_MENU_ITEM = "Edit";
    private static final String PLAY_MENU_ITEM = "Play";
    private static final String WINDOW_MODE_MENU_ITEM = "Window mode";
    private static final String FULL_SCREEN_MODE_MENU_ITEM = "Full Screen Mode";

    @Override
    public void start(final Stage primaryStage) {
        
        final StackPane root = new StackPane();
        final Scene scene = new Scene(root, 800, 600);
        final Menu mainMenu = new Menu();
        final Menu playMenu = new Menu();
        final Menu editMenu = new Menu();
        final Menu optionsMenu = new Menu();
        final Menu newLabyrinthMenu = new Menu();
        
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
        
        root.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), linear-gradient(#020b02, #3a3a3a)");
        mainMenu.install(root);
        primaryStage.setScene(scene);
        
        mainMenu.addEntry(PLAY_MENU_ITEM, new SwapMenuAction(mainMenu, playMenu));
        mainMenu.addEntry(EDIT_MENU_ITEM, new SwapMenuAction(mainMenu, editMenu));
        mainMenu.addEntry(OPTIONS_MENU_ITEM, new SwapMenuAction(mainMenu, optionsMenu));
        mainMenu.addEntry(EXIT_MENU_ITEM, exitAction);

        playMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(playMenu, mainMenu));
        
        editMenu.addEntry(NEW_LABYRINTH_MENU_ITEM, new SwapMenuAction(editMenu, newLabyrinthMenu));
        editMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(editMenu, mainMenu));
        
        optionsMenu.addEntry(FULL_SCREEN_MODE_MENU_ITEM, fullScreenAction);
        optionsMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(optionsMenu, mainMenu));
        
        newLabyrinthMenu.addEntry(SMALL_SIZE_MENU_ITEM, new EditLabyrinthAction(root, newLabyrinthMenu, 20, 20));
        newLabyrinthMenu.addEntry(MEDIUM_SIZE_MENU_ITEM, new EditLabyrinthAction(root, newLabyrinthMenu, 40, 40));
        newLabyrinthMenu.addEntry(LARGE_SIZE_MENU_ITEM, new EditLabyrinthAction(root, newLabyrinthMenu, 80, 80));
        newLabyrinthMenu.addEntry(BACK_TO_EDIT_MENU_MENU_ITEM, new SwapMenuAction(newLabyrinthMenu, editMenu));
        
        // TODO: report bug?
        //final Text fullScreenText = menuItemBuilder.onMouseClicked(fullScreenHandler).text("Full screen mode").build();
        //final Text fullScreenText = menuItemBuilder.text("Full screen mode").build();
        //fullScreenText.setOnMouseClicked(fullScreenHandler);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

