package pcb_board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Segment {

    private final CustomPoint start;
    private final CustomPoint finish;

    public Segment(CustomPoint start, CustomPoint finish) {
        this.start = start;
        this.finish = finish;
    }

    public CustomPoint getStart() {
        return start;
    }

    public CustomPoint getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return "(" + start.toString() + ", " + finish.toString() + ")";
    }
}
