package glurbi.labifx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;

public class LabiPane extends Pane {

    private final LabiModel model;
    
    public LabiPane(LabiModel model) {
        this.model = model;
        setPrefSize(model.getWidth(), model.getHeight());
        setMinSize(model.getWidth(), model.getHeight());
        setMaxSize(model.getWidth(), model.getHeight());
        init();
    }
    
    private void init() {
        LineBuilder<?> b = LineBuilder.create();
        b.fill(Color.WHITE);
        b.stroke(Color.WHITE);
        for (int x = 0 ; x < model.getWidth(); x++) {
            for (int y = 0; y < model.getHeight(); y++) {
                getChildren().add(b.startX(x).endX(x+1).startY(y).endY(y).build());
                getChildren().add(b.startX(x).endX(x).startY(y).endY(y+1).build());
            }
        }
        scaleXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                for (Node n : getChildren()) {
                    Line l = (Line) n;
                    l.setStrokeWidth(1.0 / getScaleX());
                }
            }
        });
    }
    

}
