package pcb_board;

import java.util.Objects;

public class CustomPoint {
    private final int x;
    private final int y;
    public CustomPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CustomPoint(CustomPoint p){
        this.x = p.x;
        this.y = p.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomPoint point = (CustomPoint) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
