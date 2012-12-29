package glurbi.labifx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class LabiModel implements Observable {

    private static enum Dir {
        NORTH(0,-1), SOUTH(0,+1), EAST(+1,0), WEST(-1,0);
        Dir(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public final int x;
        public final int y;
    }
    
    public static class Cell {
        public EnumSet<Dir> walls;
        public Cell(Dir... dirs) {
            this.walls = EnumSet.copyOf(Arrays.asList(dirs));
        }
    }
    
    public static class Pos {
        public final int x;
        public final int y;
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + this.x;
            hash = 53 * hash + this.y;
            return hash;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pos other = (Pos) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }
    }
    
    private final Map<Pos, Cell> cells;
    private final int width;
    private final int height;
    private final List<InvalidationListener> invalidationListeners;

    public LabiModel(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new HashMap<>();
        this.invalidationListeners = new ArrayList<>();
        init(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void init(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pos pos = new Pos(x, y);
                Cell cell = new Cell(Dir.values());
                cells.put(pos, cell);
            }
        }
    }
    
    public Cell getCell(int x, int y) {
        return cells.get(new Pos(x, y));
    }
    
    public void createRandomLabyrinth() {
        Set<Pos> visited = new HashSet<>();
        
        notifyInvalidated();
    }
    
    private List<Pos> getUnvisitedNeighborsShuffled(Pos p, Set<Pos> visited) {
        List<Pos> l = new ArrayList<>();
        for (Dir d : Dir.values()) {
            Pos neighborPos = new Pos(p.x+d.x, p.y+d.y);
            if (cells.get(neighborPos) != null && !visited.contains(neighborPos)) {
                l.add(neighborPos);
            }
        }
        Collections.shuffle(l);
        return l;
    }

    
    private void notifyInvalidated() {
        for (InvalidationListener il : invalidationListeners) {
            il.invalidated(this);
        }
    }
    
    @Override
    public void addListener(InvalidationListener il) {
        invalidationListeners.add(il);
    }

    @Override
    public void removeListener(InvalidationListener il) {
        invalidationListeners.remove(il);
    }
}
