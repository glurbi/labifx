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

    public static enum Dir {
        NORTH(0,-1), EAST(+1,0), SOUTH(0,+1), WEST(-1,0);
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
        @Override
        public String toString() {
            return "Cell [walls=" + walls + "]";
        }
    }
    
    public static class Neighbor {
        public final Pos pos;
        public final Dir dir;
        public Neighbor(Pos pos, Dir dir) {
            this.pos = pos;
            this.dir = dir;
        }
        @Override
        public String toString() {
            return "Neighbor [pos=" + pos + ", dir=" + dir + "]";
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
        @Override
        public String toString() {
            return "Pos [x=" + x + ", y=" + y + "]";
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
        init(width, height);
        Set<Pos> visited = new HashSet<>();
        visit(new Pos(0,0), visited);
        notifyInvalidated();
    }
    
    private void visit(Pos p, Set<Pos> visited) {
        visited.add(p);
        List<Neighbor> neighbors = getUnvisitedNeighborsShuffled(p, visited);
        for (Neighbor n : neighbors) {
            if (visited.contains(n.pos)) {
                continue;
            }
            removeWall(p, n.dir, n.pos);
            visit(n.pos, visited);
        }
    }
    
    private void removeWall(Pos p1, Dir d, Pos p2) {
        Cell c1 = cells.get(p1);
        c1.walls.remove(d);
        Cell c2 = cells.get(p2);
        Dir opposite = Dir.values()[(d.ordinal()+2)%4];
        c2.walls.remove(opposite);
    }
    
    private List<Neighbor> getUnvisitedNeighborsShuffled(Pos p, Set<Pos> visited) {
        List<Neighbor> l = new ArrayList<>();
        for (Dir d : Dir.values()) {
            Pos neighborPos = new Pos(p.x+d.x, p.y+d.y);
            if (cells.get(neighborPos) != null && !visited.contains(neighborPos)) {
                l.add(new Neighbor(neighborPos, d));
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
