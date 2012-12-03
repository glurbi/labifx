package glurbi.labifx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

public class Menu extends VBox {

    private final GaussianBlur gaussianBlur = new GaussianBlur(5.0);
    private final Color menuColor = Color.BROWN;
    private final DropShadow dropShadow = new DropShadow(20.0, menuColor);
    private final Font font = Font.font("Verdana", 60);
    
    private final EventHandler<MouseEvent> blurHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            ((Text) t.getSource()).setEffect(gaussianBlur);
        }
    };

    private final EventHandler<MouseEvent> deblurHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            ((Text) t.getSource()).setEffect(dropShadow);
        }
    };
    
    private static class ActionHandler implements EventHandler<MouseEvent> {
        private Runnable action;
        public ActionHandler(Runnable action) {
            this.action = action;
        }
        @Override
        public void handle(MouseEvent t) {
            action.run();
        }
    };

    
    public Menu() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                System.out.println(e.getCode());
            }
        });
    }
    
    private String getId(String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return new String(md.digest(name.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void addEntry(String name, Runnable action) {
        Text text = TextBuilder.create().font(font)
                .effect(gaussianBlur).onMouseEntered(deblurHandler)
                .onMouseExited(blurHandler).fill(menuColor).text(name)
                .onMouseClicked(new ActionHandler(action)).id(getId(name)).build();
        getChildren().add(BorderPaneBuilder.create().center(text).build());
    }

    public void renameEntry(String oldName, String newName) {
        Node n = lookup("#"+getId(oldName));
        ((Text)n).setText(newName);
        ((Text)n).setId(getId(newName));
    }
    
}
