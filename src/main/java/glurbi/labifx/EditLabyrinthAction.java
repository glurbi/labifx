package glurbi.labifx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        final LabiPane labiPane = new LabiPane(labiModel);
        final ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if (parent.getWidth() > parent.getHeight()) {
                    labiPane.setScaleX(parent.getHeight() / height);
                    labiPane.setScaleY(parent.getHeight() / height);
                } else {
                    labiPane.setScaleX(parent.getWidth() / width);
                    labiPane.setScaleY(parent.getWidth() / width);
                }
            }
        };
        sizeListener.changed(null, null, null);
        parent.widthProperty().addListener(sizeListener);
        parent.heightProperty().addListener(sizeListener);
        parent.getChildren().add(labiPane);
    }
    
};

