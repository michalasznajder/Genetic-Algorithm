package pcb_board;
import utils.Config;
import utils.DataLoader;
import utils.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PCBBoard {
    private final List<Path> paths;
    private final int width;
    private final int height;
    private FitnessData fitnessData;
    private boolean breedOnly;

    public PCBBoard(DataLoader dataLoader) {
        this.width = dataLoader.getWidth();
        this.height = dataLoader.getHeight();;
        this.paths = createPaths(dataLoader.getPairs(), this.width, this.height);
        this.fitnessData = calculateFitnessData(this.paths);
        this.setBreedOnly(false);
    }

    public PCBBoard(PCBBoard p){
        this.width = p.getWidth();
        this.height = p.getHeight();
        List<Path> paths = new ArrayList<>();
        List<Path> pathsToClone = p.getPaths();
        for(Path path : pathsToClone){
            paths.add(new Path(path));
        }
        this.paths = paths;
        this.fitnessData = calculateFitnessData(this.paths);
        this.setBreedOnly(false);
    }

    public PCBBoard(PCBBoard mother, PCBBoard father){
        if(father.getWidth() != mother.getWidth() || father.getHeight() != mother.getHeight()){
            throw new RuntimeException("Mother and Father board must have equal dimensions!");
        }

        this.width = mother.getWidth();
        this.height = mother.getHeight();
        this.paths = new ArrayList<>();

        for(int i = 0; i < mother.getPaths().size(); i++){
            Path newPath;
            if(RandomGenerator.getInt(2) == 0){
                newPath = new Path(mother.getPaths().get(i));
            }else{
                newPath = new Path(father.getPaths().get(i));
            }
            this.paths.add(newPath);
        }
        this.fitnessData = calculateFitnessData(this.paths);
        this.setBreedOnly(false);

    }

    public void mutate(){
        int mutationIndex = RandomGenerator.getInt(paths.size());
        paths.get(mutationIndex).mutate();
        this.fitnessData = calculateFitnessData(this.paths);
    }

    public FitnessData getFitnessData() {
        return fitnessData;
    }

    public PCBBoard(List<Path> paths, int width, int height){
        this.width = width;
        this.height = height;
        this.paths = paths;
        this.fitnessData = calculateFitnessData(this.paths);
    }

    public FitnessData calculateFitnessData(List<Path> paths){
        int numberOfIntersections = 0;
        int length = 0;
        int numberOfSegments = 0;
        for(int i = 0; i < paths.size(); i++) {
            Path path1 = paths.get(i);
            length += path1.getPointsLength();
            numberOfSegments += path1.getSegmentsLength();

            List<CustomPoint> points1 = path1.getPoints();
            for (int j = i+1; j < paths.size(); j++) {
                List<CustomPoint> points2 = paths.get(j).getPoints();
                for(CustomPoint p1 : points1){
                    for(CustomPoint p2 : points2){
                        if(p1.isEqual(p2))
                            numberOfIntersections++;
                    }
                }
            }
        }
        return new FitnessData(length, numberOfSegments, numberOfIntersections);
    }

    private List<Path> createPaths(List<Pair> inputs, int width, int height){
        List<Path> resultPaths = new ArrayList<>();
        for(Pair p : inputs){
            resultPaths.add(new Path(p, width, height));
        }
        return resultPaths;
    }


    public void draw(){
        Color frameBackgroundColor = Color.decode("#d8ebe4");
        JFrame frame = new JFrame("PCBBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((width+1)* Config.GAP +200, (height+1)* Config.GAP +200);
        frame.getContentPane().setBackground(frameBackgroundColor);
        frame.setLayout(null);
        frame.setVisible(true);
        Board board = new Board();
        board.setBounds(50,50, (width+1)* Config.GAP + Config.POINT_SIZE, (height+1)* Config.GAP + Config.POINT_SIZE);
        frame.add(board);
    }

    public double getFitness(){
        return this.fitnessData.getFitness();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public String toString() {
        return "PCBBard = " + fitnessData.toString();
    }

    public boolean isBreedOnly() {
        return breedOnly;
    }

    public void setBreedOnly(boolean breedOnly) {
        this.breedOnly = breedOnly;
    }

    private class Board extends JPanel {
        private final Color basicPointColor = Color.decode("#282846");
        private final Color boardBackgroundColor = Color.decode("#007580");

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(boardBackgroundColor);
            drawBoard(g);

        }
        private void drawBoard(Graphics g){
            for(int i = 1; i <= width; i++){
                for(int j = 1; j <= height; j++){
                    drawAPoint(g, new CustomPoint(i, j), basicPointColor);
                }
            }
            for (Path path : paths) {
                drawAPath(g, path.getSegments());
            }
        }
        private void drawAPoint(Graphics g, CustomPoint point, Color color){
            g.setColor(color);
            g.fillOval(point.getX()*Config.GAP, point.getY()*Config.GAP,Config.POINT_SIZE, Config.POINT_SIZE);
        }

        private void drawAPath(Graphics g, List<Segment> segments){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(RandomGenerator.getInt(255), RandomGenerator.getInt(255), RandomGenerator.getInt(255)));

            for(Segment s : segments){
                CustomPoint start = s.getStart();
                CustomPoint finish = s.getFinish();

                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(start.getX()*Config.GAP + Config.POINT_SIZE/2, start.getY()*Config.GAP + Config.POINT_SIZE/2,
                        finish.getX()*Config.GAP + Config.POINT_SIZE/2, finish.getY()*Config.GAP + Config.POINT_SIZE/2);
            }
        }
    }
}
