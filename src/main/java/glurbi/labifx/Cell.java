package glurbi.labifx;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Cell extends Rectangle {

    public static final double WIDTH = 10;
    public static final double HEIGHT = 10;

    public enum State {
        WALL,
        EMPTY
    };

    private final CellPos pos;
    
    private State state;
    
    public Cell(int x, int y) {
        this.pos = new CellPos(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setStroke(Color.BLACK);
        setFill(Color.BLACK);
        setState(State.WALL);
        setOnMouseEntered(mouseEnteredEventHandler);
        setOnMouseExited(mouseExitedEventHandler);
        relocate(pos.getX()*WIDTH, pos.getY()*HEIGHT);
    }

    public CellPos getPos() {
        return pos;
    }
    
    public final void setState(State state) {
        this.state = state;
    }

    private FadeTransition blink = FadeTransitionBuilder.create()
                               .duration(Duration.millis(100))
                               .fromValue(1.0)
                               .toValue(0.0)
                               .autoReverse(true)
                               .cycleCount(Transition.INDEFINITE)
                               .node(this)
                               .build();
    
    private EventHandler<MouseEvent> mouseEnteredEventHandler = new  EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            blink.play();
        }
    };
    
    private EventHandler<MouseEvent> mouseExitedEventHandler = new  EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            blink.stop();
            setOpacity(1.0);
        }
    };
    
}
