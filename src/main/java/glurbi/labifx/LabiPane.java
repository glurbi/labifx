package glurbi.labifx;

import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LabiPane extends Pane {

    public enum Mode {
        EMPTIFY,
        WALLIFY
    }
    
    private Mode mode = Mode.EMPTIFY;
    private boolean dragging = false;
    private final Map<CellPos, Cell> cells;

    public LabiPane(Map<CellPos, Cell> cells) {
        this.cells = cells;
        getChildren().addAll(cells.values());
        setOnMouseClicked(mouseButtonEventHandler);
        setOnMouseDragged(mouseButtonEventHandler);
        setOnMouseClicked(mouseButtonEventHandler);
    }
    
    public Mode getMode() {
        return mode;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
    
    public final ObjectProperty<Mode> modeProperty = new ObjectPropertyBase<Mode>() {
        @Override
        public Object getBean() {
            return mode;
        }
        @Override
        public String getName() {
            return "mode";
        }
    };
    
    private EventHandler<MouseEvent> mouseButtonEventHandler = new  EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            CellPos pos = new CellPos((int)(e.getX()/Cell.WIDTH), (int)(e.getY()/Cell.HEIGHT));
            Cell cell = cells.get(pos);
            switch (mode) {
                case EMPTIFY:
                    cell.setStroke(Color.WHITE);
                    cell.setFill(Color.WHITE);
                    cell.setState(Cell.State.EMPTY);
                    break;
                case WALLIFY:
                    cell.setState(Cell.State.WALL);
                    break;
            }
        }
    };
    
    
    
}
