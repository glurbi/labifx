package glurbi.labifx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

public class LabiPane extends Pane {

    private final LabiModel model;
    private Path walls;
    
    public LabiPane(LabiModel model) {
        this.model = model;
        setPrefSize(model.getWidth(), model.getHeight());
        setMinSize(model.getWidth(), model.getHeight());
        setMaxSize(model.getWidth(), model.getHeight());
        init();
    }
    
    private void init() {
        walls = new Path();
        walls.setFill(Color.WHITE);
        walls.setStroke(Color.WHITE);
        for (int x = 0 ; x < model.getWidth(); x++) {
            for (int y = 0; y < model.getHeight(); y++) {
                walls.getElements().add(new MoveTo(x, y));
                walls.getElements().add(new HLineTo(x+1));
                walls.getElements().add(new MoveTo(x, y));
                walls.getElements().add(new VLineTo(y+1));
            }
        }
        getChildren().add(walls);
        scaleXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                walls.setStrokeWidth(1.0 / getScaleX());
            }
        });
    }
    

}
