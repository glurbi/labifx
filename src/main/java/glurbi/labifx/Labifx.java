package glurbi.labifx;

import glurbi.labifx.Cell.State;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Labifx extends Application {

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
        root.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), linear-gradient(#020b02, #3a3a3a)");
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
        
        final Runnable newLabyrinthAction = new Runnable() {
            @Override
            public void run() {
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                GridPane grid = new GridPane();
                Label widthLabel = new Label("Width:");
                Label heightLabel = new Label("Height:");
                TextField widthTextField = new TextField();
                TextField heightTextField = new TextField();
                grid.add(widthLabel, 0, 0);
                grid.add(widthTextField, 1, 0);
                grid.add(heightLabel, 0, 1);
                grid.add(heightTextField, 1, 1);
                HBox buttonsBox = new HBox();
                Button okButton = new Button("OK");
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        final int X_SIZE = 100;
                        final int Y_SIZE = 100;
                        Map<CellPos, Cell> cells = new HashMap<>();
                        for (int x = 0; x < X_SIZE; x++) {
                            for (int y = 0; y < Y_SIZE; y++) {
                                Cell cell = new Cell(x, y, (x % 2 == 0 || y % 2 == 0) ? State.WALL : State.EMPTY);
                                cells.put(cell.getPos(), cell);
                            }
                        }
                        final Labi labi = new Labi(cells, X_SIZE, Y_SIZE);
                        labi.setScaleX(root.getWidth() / X_SIZE);
                        labi.setScaleY(root.getHeight() / Y_SIZE);
                        final ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                                labi.setScaleX(root.getWidth() / X_SIZE);
                                labi.setScaleY(root.getHeight() / Y_SIZE);
                            }
                        };
                        root.widthProperty().addListener(sizeListener);
                        root.heightProperty().addListener(sizeListener);
                        root.getChildren().add(labi);
                        dialogStage.hide();
                    }
                });
                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.hide();
                    }
                });
                buttonsBox.getChildren().addAll(okButton, cancelButton);
                grid.add(buttonsBox, 0, 2, 2, 1);
                dialogStage.setScene(new Scene(grid));
                dialogStage.show();
            }
        };
        
        mainMenu.install(root);
        primaryStage.setScene(scene);
        
        mainMenu.addEntry(PLAY_MENU_ITEM, new SwapMenuAction(mainMenu, playMenu));
        mainMenu.addEntry(EDIT_MENU_ITEM, new SwapMenuAction(mainMenu, editMenu));
        mainMenu.addEntry(OPTIONS_MENU_ITEM, new SwapMenuAction(mainMenu, optionsMenu));
        mainMenu.addEntry(EXIT_MENU_ITEM, exitAction);

        playMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(playMenu, mainMenu));
        
        editMenu.addEntry(NEW_LABYRINTH_MENU_ITEM, newLabyrinthAction);
        editMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(editMenu, mainMenu));
        
        optionsMenu.addEntry(FULL_SCREEN_MODE_MENU_ITEM, fullScreenAction);
        optionsMenu.addEntry(BACK_TO_MAIN_MENU_MENU_ITEM, new SwapMenuAction(optionsMenu, mainMenu));
        
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

