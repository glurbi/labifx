package glurbi.labifx;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Testfx extends Application {
    
    private final int X_CELL_COUNT = 100;
    private final int Y_CELL_COUNT = 100;
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        
        Map<CellPos, Cell> cells = new HashMap<CellPos, Cell>();
        for (int x = 0; x < X_CELL_COUNT; x++) {
            for (int y = 0; y < Y_CELL_COUNT; y++) {
                Cell cell = new Cell(x, y);
                cells.put(cell.getPos(), cell);
            }
        }
        
        LabiPane labi = new LabiPane(cells);
        labi.setPrefSize(Cell.WIDTH*X_CELL_COUNT, Cell.HEIGHT*Y_CELL_COUNT);
        labi.setMinSize(Cell.WIDTH*X_CELL_COUNT, Cell.HEIGHT*Y_CELL_COUNT);
        labi.setMaxSize(Cell.WIDTH*X_CELL_COUNT, Cell.HEIGHT*Y_CELL_COUNT);
        
        StackPane.setAlignment(root, Pos.CENTER);
        root.getChildren().add(labi);

        Button b = new Button("QWE");
        StackPane.setAlignment(b, Pos.TOP_LEFT);
        root.getChildren().add(b);
        
//String image = JavaFXApplication9.class.getResource("splash.jpg").toExternalForm();
//root.setStyle("-fx-background-image: url('" + image + "'); 
//           -fx-background-position: center center; 
//           -fx-background-repeat: stretch;");        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
