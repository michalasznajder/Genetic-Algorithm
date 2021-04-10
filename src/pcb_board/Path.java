package pcb_board;

import utils.RandomGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private List<Segment> segments;
    private List<CustomPoint> points;

    public Path(Pair pair, int width, int height) {
        this.points = getPointsList(pair, width, height);
        this.segments = getSegmentsList(this.points);
    }

    private List<CustomPoint> getPointsList(Pair pair, int width, int height){
        CustomPoint start = pair.getStart();
        CustomPoint finish = pair.getFinish();

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
            System.out.println("points path consists of less than 2 points!");
            return null;
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

    private List<CustomPoint> connect(List<CustomPoint> pathFromStart, List<CustomPoint> pathFromEnd){
        for(int i = 0; i < pathFromStart.size(); i++){
            CustomPoint p1 = pathFromStart.get(i);
            for(int j = 0; j < pathFromEnd.size(); j++){
                CustomPoint p2 = pathFromEnd.get(j);
                if (p1.equals(p2)) {
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Segment s : segments){
            result.append(s.toString()).append(" => ");
        }
        result.append("finish");
        return result.toString();
    }
}
