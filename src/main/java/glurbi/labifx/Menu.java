package glurbi.labifx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.Pane;
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
    private final Map<Text, Runnable> actions = new HashMap<>();
    
    private Text activeEntry = null;
    
    private final EventHandler<MouseEvent> blurHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            Text entry = (Text) t.getSource();
            entry.setEffect(gaussianBlur);
        }
    };

    private final EventHandler<MouseEvent> deblurHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            Text entry = (Text) t.getSource();
            entry.setEffect(dropShadow);
            activeEntry = entry;
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
    }
    
    private String computeId(String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return new String(md.digest(name.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void install(Pane parent) {
        parent.getChildren().add(this);
        parent.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                    int step = e.getCode() == KeyCode.UP ? -1 : +1;
                    if (activeEntry != null) {
                        activeEntry.setEffect(gaussianBlur);
                        activeEntry = getNextEntry(activeEntry, step);
                    } else {
                        activeEntry = getEntry(0);
                    }
                    activeEntry.setEffect(dropShadow);
                } else if (e.getCode() == KeyCode.ENTER && activeEntry != null) {
                    actions.get(activeEntry).run();
                }
            }
        });
    }
    
    private Text getEntry(int index) {
        Pane pane = (Pane) getChildren().get(index);
        return (Text) pane.getChildren().get(0);
    }
    
    private int getIndex(Text entry) {
        for (int i = 0; i < getChildren().size(); i++) {
            if (getEntry(i).equals(entry)) {
                return i;
            }
        }
        throw new RuntimeException("Programming error!");
    }
    
    private Text getNextEntry(Text entry, int step) {
        int i = getIndex(entry) + getChildren().size();
        i += step;
        i = i % getChildren().size();
        return getEntry(i);
    }
    
    public void uninstall(Pane parent) {
        parent.getChildren().remove(this);
        parent.getScene().setOnKeyPressed(null);
    }
    
    public void addEntry(String name, Runnable action) {
        Text text = TextBuilder.create().font(font)
                .effect(gaussianBlur).onMouseEntered(deblurHandler)
                .onMouseExited(blurHandler).fill(menuColor).text(name)
                .onMouseClicked(new ActionHandler(action)).id(computeId(name)).build();
        actions.put(text, action);
        getChildren().add(BorderPaneBuilder.create().center(text).build());
    }

    public void renameEntry(String oldName, String newName) {
        Node n = lookup("#"+computeId(oldName));
        ((Text)n).setText(newName);
        ((Text)n).setId(computeId(newName));
    }
    
}
