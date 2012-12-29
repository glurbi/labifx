package glurbi.labifx;

import glurbi.labifx.LabiModel.Cell;
import glurbi.labifx.LabiModel.Dir;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

public class LabiPane extends Pane {

    private final ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            if (parent.getWidth() > parent.getHeight()) {
                setScaleX(parent.getHeight() / model.getHeight());
                setScaleY(parent.getHeight() / model.getHeight());
            } else {
                setScaleX(parent.getWidth() / model.getWidth());
                setScaleY(parent.getWidth() / model.getWidth());
            }
            walls.setStrokeWidth(1.0 / getScaleX());
        }
    };
    
    private final LabiModel model;
    private final Pane parent;
    
    private Path walls;
    
    public LabiPane(Pane parent, LabiModel model) {
        this.model = model;
        this.parent = parent;
        setMinSize(model.getWidth(), model.getHeight());
        setPrefSize(model.getWidth(), model.getHeight());
        setMaxSize(model.getWidth(), model.getHeight());
        sizeListener.changed(null, null, null);
        parent.widthProperty().addListener(sizeListener);
        parent.heightProperty().addListener(sizeListener);
        getChildren().add(walls);
    }
    
    public void updatePath() {
        this.walls = new Path();
        walls.setFill(Color.WHITE);
        walls.setStroke(Color.WHITE);
        for (int x = 0 ; x < model.getWidth(); x++) {
            for (int y = 0; y < model.getHeight(); y++) {
                Cell cell = model.getCell(x, y);
                if (cell.walls.contains(Dir.NORTH)) {
                    walls.getElements().add(new MoveTo(x, y));
                    walls.getElements().add(new HLineTo(x+1));
                }
                if (cell.walls.contains(Dir.SOUTH)) {
                    walls.getElements().add(new MoveTo(x, y+1));
                    walls.getElements().add(new HLineTo(x+1));
                }
                if (cell.walls.contains(Dir.EAST)) {
                    walls.getElements().add(new MoveTo(x, y));
                    walls.getElements().add(new VLineTo(y+1));
                }
                if (cell.walls.contains(Dir.WEST)) {
                    walls.getElements().add(new MoveTo(x+1, y));
                    walls.getElements().add(new VLineTo(y+1));
                }
            }
        }
    }    

}
