package glurbi.labifx;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class LabiModel {

    public static enum Dir {
        NORTH, SOUTH, EAST, WEST
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

    public LabiModel(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new HashMap<>();
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
    
}
