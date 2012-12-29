package glurbi.labifx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class EditLabyrinthAction implements Runnable {
    
    private final Pane parent;
    private final Menu from;
    private final int width;
    private final int height;
    
    public EditLabyrinthAction(Pane parent, Menu from, int width, int height) {
        this.parent = parent;
        this.from = from;
        this.width = width;
        this.height = height;
    }
    
    
    
    @Override
    public void run() {
        from.uninstall(parent);
        LabiModel labiModel = new LabiModel(width, height);
        LabiPane labiPane = new LabiPane(parent, labiModel);
        AnchorPane controlPane = new AnchorPane();
        VBox buttonsBox = new VBox();
        buttonsBox.setSpacing(10);
        Button createRandomLabyrinthButton = new Button("Random");
        createRandomLabyrinthButton.setMaxWidth(Double.MAX_VALUE);
        Button backToNewLabyrinthMenu = new Button("Back...");
        backToNewLabyrinthMenu.setMaxWidth(Double.MAX_VALUE);
        backToNewLabyrinthMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                parent.getChildren().clear();
                from.install(parent);
            }
        });
        buttonsBox.getChildren().addAll(createRandomLabyrinthButton, backToNewLabyrinthMenu);
        controlPane.getChildren().add(buttonsBox);
        AnchorPane.setLeftAnchor(buttonsBox, 10.0);
        AnchorPane.setTopAnchor(buttonsBox, 10.0);
        parent.getChildren().addAll(labiPane, controlPane);
    }
    
};

