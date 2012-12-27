package glurbi.labifx;

import javafx.scene.layout.Pane;

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
        parent.getChildren().add(labiPane);
    }
    
};

