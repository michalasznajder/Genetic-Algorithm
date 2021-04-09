package pcb_board;

public class Pair {
    private final CustomPoint start;
    private final CustomPoint finish;

    public Pair(CustomPoint start, CustomPoint finish) {
        this.start = new CustomPoint(start);
        this.finish = new CustomPoint(finish);
    }

    public CustomPoint getStart() {
        return start;
    }

    public CustomPoint getFinish() {
        return finish;
    }
}