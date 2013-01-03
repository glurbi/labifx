package glurbi.labifx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LabiEditorPane extends StackPane {

    private final LabiModel model;
    private final Menu from;
    private final Pane parent;
    
    public LabiEditorPane(Pane parent, Menu from, LabiModel model) {
        this.model = model;
        this.from = from;
        this.parent = parent;
        init();
    }
    
    private void init() {
        LabiPane labiPane = new LabiPane(parent, model);
        AnchorPane controlPane = new AnchorPane();
        VBox buttonsBox = new VBox();
        buttonsBox.setSpacing(10);
        Button createRandomLabyrinthButton = new Button("Random");
        createRandomLabyrinthButton.setMaxWidth(Double.MAX_VALUE);
        createRandomLabyrinthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                model.createRandomLabyrinth();
            }
        });
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
        getChildren().addAll(labiPane, controlPane);
    }
}
