package pcb_board;

import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final CustomPoint start;
    private final CustomPoint finish;
    private List<Segment> segments;
    private List<CustomPoint> points;
    private final int width;
    private final int height;

    public Path(Pair pair, int width, int height) {
        this.start = new CustomPoint(pair.getStart());
        this.finish = new CustomPoint(pair.getFinish());
        this.width = width;
        this.height = height;
        this.points = getPointsList(this.start, this.finish, width, height);
        this.segments = getSegmentsList(this.points);
    }

    public Path(Path p){
        this.start = new CustomPoint(p.getStart());
        this.finish = new CustomPoint(p.getFinish());
        this.width = p.getWidth();
        this.height = p.getHeight();
        this.points = clonePoints(p.getPoints());
        this.segments = getSegmentsList(this.points);
    }

    public void mutate2(){
        this.points = getPointsList(this.start, this.finish, width, height);
        this.segments = getSegmentsList(this.points);
    }

    public void mutate(){
        int segmentIndex = RandomGenerator.getInt(segments.size());
        Segment mutationSegment = segments.get(segmentIndex);
        CustomPoint mutationSegmentStart = mutationSegment.getStart();
        CustomPoint mutationSegmentFinish = mutationSegment.getFinish();


        int mutationStartIndex = points.indexOf(mutationSegmentStart);
        int mutationFinishIndex = points.indexOf(mutationSegmentFinish);

        int firstPointIndex;
        int secondPointIndex;

        CustomPoint mutationPointStart;
        CustomPoint mutationPointEnd;
        int i = 0;
        do{
//            if(mutationFinishIndex)
            firstPointIndex = RandomGenerator.getInt(mutationFinishIndex - mutationStartIndex + 1) + mutationStartIndex;
            secondPointIndex = RandomGenerator.getInt(mutationFinishIndex - mutationStartIndex + 1) + mutationStartIndex;
            i++;
        }while(firstPointIndex == secondPointIndex);

        if(firstPointIndex > secondPointIndex){
            int temp = firstPointIndex;
            firstPointIndex = secondPointIndex;
            secondPointIndex = temp;
        }

        mutationPointStart = points.get(firstPointIndex);
        mutationPointEnd = points.get(secondPointIndex);

        List<CustomPoint> newPoints = getPointsList(mutationPointStart, mutationPointEnd, this.width, this.height);
        List<CustomPoint> pointsToChange = this.points.subList(points.indexOf(mutationPointStart), points.indexOf(mutationPointEnd)+1);

        this.points.removeAll(pointsToChange);
        this.points.addAll(firstPointIndex, newPoints);
        this.segments = getSegmentsList(this.points);
    }

    private List<CustomPoint> clonePoints(List<CustomPoint> points){
        List<CustomPoint> resultPoints = new ArrayList<>();
        for(CustomPoint p : points){
            resultPoints.add(new CustomPoint(p));
        }
        return resultPoints;
    }

    private List<CustomPoint> getMutatedPointsList(CustomPoint start, CustomPoint finish, int width, int height){
        List<CustomPoint> pathFromStart = new ArrayList<>();
        List<CustomPoint> fullPath;
        pathFromStart.add(start);
        do {
            CustomPoint nextStart;
            if(pathFromStart.size() >= 2){
                nextStart = getNextPoint(pathFromStart.get(pathFromStart.size() - 2), start, width, height);
            }else{
                nextStart = getNextPoint(null, start,  width, height);
            }

            addPoints(pathFromStart, start, nextStart);

            start = nextStart;

            fullPath = connectMutated(pathFromStart, finish);
        } while(fullPath == null);

        return fullPath;
    }

    private List<CustomPoint> getPointsList(CustomPoint start, CustomPoint finish, int width, int height){
        List<CustomPoint> pathFromStart = new ArrayList<>();
        List<CustomPoint> pathFromEnd = new ArrayList<>();
        List<CustomPoint> fullPath;
        pathFromStart.add(start);
        pathFromEnd.add(finish);
        do {
            CustomPoint nextStart;
            CustomPoint nextFinish;
            if(pathFromStart.size() >= 2){
                nextStart = getNextPoint(pathFromStart.get(pathFromStart.size() - 2), start, width, height);
                nextFinish = getNextPoint(pathFromEnd.get(pathFromEnd.size() - 2), finish, width, height);
            }else{
                nextStart = getNextPoint(null, start,  width, height);
                nextFinish = getNextPoint(null, finish,  width, height);
            }

            addPoints(pathFromStart, start, nextStart);
            addPoints(pathFromEnd, finish, nextFinish);

            start = nextStart;
            finish = nextFinish;

            fullPath = connect(pathFromStart, pathFromEnd);
        } while(fullPath == null);

        return fullPath;
    }

    private List<Segment> getSegmentsList(List<CustomPoint> points){
        if(points.size() < 2){
            throw new RuntimeException("points path consists of less than 2 points!");
        }
        List<Segment> resultSegments = new ArrayList<>();
        CustomPoint beforeCurve;
        CustomPoint afterCurve;
        CustomPoint segmentStart = points.get(0);
        for(int i = 0; i < points.size() - 2; i++){
            beforeCurve = points.get(i);
            afterCurve = points.get(i+2);
            if(beforeCurve.getX() != afterCurve.getX() && beforeCurve.getY() != afterCurve.getY()){
                CustomPoint segmentEnd = points.get(i+1);
                resultSegments.add(new Segment(segmentStart, segmentEnd));
                segmentStart = segmentEnd;
            }
        }
        resultSegments.add(new Segment(segmentStart, points.get(points.size()-1)));
        return resultSegments;
    }

    private void addPoints(List<CustomPoint> points, CustomPoint start , CustomPoint finish){
        if(start.getX() == finish.getX()){
            if(finish.getY() > start.getY()){
                for(int i = start.getY()+1; i <= finish.getY(); i++){
                    CustomPoint p = new CustomPoint(start.getX(), i);
                    points.add(p);
                }
            }else{
                for(int i = start.getY()-1; i >= finish.getY(); i--){
                    CustomPoint p = new CustomPoint(start.getX(), i);
                    points.add(p);
                }
            }
        }
        if(start.getY() == finish.getY()){
            if(finish.getX() > start.getX()){
                for(int i = start.getX()+1; i <= finish.getX(); i++){
                    CustomPoint p = new CustomPoint(i, start.getY());
                    points.add(p);
                }
            }else{
                for(int i = start.getX()-1; i >= finish.getX(); i--){
                    CustomPoint p = new CustomPoint(i, start.getY());
                    points.add(p);
                }
            }
        }
    }

    private List<CustomPoint> connectMutated(List<CustomPoint> pathFromStart, CustomPoint end){
        for(int i = 0; i < pathFromStart.size(); i++){
            CustomPoint p1 = pathFromStart.get(i);
            if (p1.isEqual(end)) {
                List<CustomPoint> resultPath = new ArrayList<>(pathFromStart.subList(0, i + 1));
                return resultPath;
            }
        }
        return null;
    }

    private List<CustomPoint> connect(List<CustomPoint> pathFromStart, List<CustomPoint> pathFromEnd){
        for(int i = 0; i < pathFromStart.size(); i++){
            CustomPoint p1 = pathFromStart.get(i);
            for(int j = 0; j < pathFromEnd.size(); j++){
                CustomPoint p2 = pathFromEnd.get(j);
                if (p1.isEqual(p2)) {
                    List<CustomPoint> resultPath = new ArrayList<>(pathFromStart.subList(0, i + 1));
                    List<CustomPoint> rest = pathFromEnd.subList(0, j);
                    Collections.reverse(rest);
                    resultPath.addAll(rest);
                    return resultPath;
                }
            }
        }
        return null;
    }

    private CustomPoint getNextPoint(CustomPoint previous, CustomPoint current, int width, int height){
        while(true){
            int axisIndex = RandomGenerator.getInt(2);
            if(axisIndex == 0){
                int newPointX;
                do {
                    newPointX = RandomGenerator.getInt(width) + 1;
                }while(newPointX == current.getX());

                if(previous == null){
                    return new CustomPoint(newPointX, current.getY());
                }
                if(!(newPointX <= current.getX() && previous.getX() < current.getX())
                        && !(newPointX >= current.getX() && previous.getX() > current.getX()))
                    return new CustomPoint(newPointX, current.getY());
            }else{
                int newPointY;
                do {
                    newPointY = RandomGenerator.getInt(height) + 1;
                }while(newPointY == current.getY());

                if(previous == null){
                    return new CustomPoint(current.getX(), newPointY);
                }
                if(!(newPointY <= current.getY() && previous.getY() < current.getY()) &&
                        !(newPointY >= current.getY() && previous.getY() > current.getY()))
                    return new CustomPoint(current.getX(), newPointY);
            }
        }
    }

    public int getPointsLength(){
        return points.size() - 1;
    }

    public int getSegmentsLength(){
        return segments.size();
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<CustomPoint> getPoints() {
        return points;
    }

    public void setPoints(List<CustomPoint> points) {
        this.points = points;
    }

    public CustomPoint getStart() {
        return start;
    }

    public CustomPoint getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Points:\n");
        for(CustomPoint p : points){
            result.append(p.toString()).append(" => ");
        }
        result.append("Segments:\n");
        for(Segment s : segments){
            result.append(s.toString()).append(" => ");
        }
        result.append("finish\n");
        return result.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
