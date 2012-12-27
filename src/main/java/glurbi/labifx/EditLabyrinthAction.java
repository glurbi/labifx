package glurbi.labifx;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
        final LabiModel labiModel = new LabiModel(width, height);
        final LabiPane labiPane = new LabiPane(parent, labiModel);
        final AnchorPane controlPane = new AnchorPane();
        final VBox buttonsBox = new VBox();
        final ToggleGroup editModeGroup = new ToggleGroup();
        final ToggleButton tb1 = new ToggleButton("B1");
        tb1.setToggleGroup(editModeGroup);
        tb1.setSelected(true);
        final ToggleButton tb2 = new ToggleButton("B2");
        tb1.setToggleGroup(editModeGroup);
        buttonsBox.getChildren().addAll(tb1, tb2);
        tb1.setSelected(false);
        controlPane.getChildren().add(buttonsBox);
        AnchorPane.setLeftAnchor(buttonsBox, 10.0);
        AnchorPane.setTopAnchor(buttonsBox, 10.0);
        parent.getChildren().addAll(labiPane, controlPane);
    }
    
};

