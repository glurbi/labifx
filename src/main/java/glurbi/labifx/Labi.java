package glurbi.labifx;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Labi extends Pane {

    public enum Mode {
        EMPTIFY,
        WALLIFY
    }
    
    private Mode mode = Mode.EMPTIFY;
    private boolean dragging = false;
    private final Map<CellPos, Cell> cells;

    public Labi(Map<CellPos, Cell> cells, int xSize, int ySize) {
        this.cells = cells;
        getChildren().addAll(cells.values());
        setOnMouseClicked(mouseButtonEventHandler);
        setOnMouseDragged(mouseButtonEventHandler);
        setOnMouseClicked(mouseButtonEventHandler);
        setPrefSize(Cell.WIDTH*xSize, Cell.HEIGHT*ySize);
        setMinSize(Cell.WIDTH*xSize, Cell.HEIGHT*ySize);
        setMaxSize(Cell.WIDTH*xSize, Cell.HEIGHT*ySize);
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
    
    private EventHandler<MouseEvent> mouseButtonEventHandler = new  EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            CellPos pos = new CellPos((int)(e.getX()), (int)(e.getY()));
            Cell cell = cells.get(pos);
            switch (mode) {
                case EMPTIFY:
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
