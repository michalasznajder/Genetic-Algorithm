package pcb_board;

public class Segment {

    private final CustomPoint start;
    private final CustomPoint finish;

    public Segment(CustomPoint start, CustomPoint finish) {
        this.start = start;
        this.finish = finish;
    }

    public Segment(Segment s){
        this.start = new CustomPoint(s.getStart());
        this.finish = new CustomPoint(s.getFinish());
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
