package pcb_board;
import utils.Config;
import utils.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PCBBoard {
    private final List<Path> paths;
    private final int width;
    private final int height;

    public PCBBoard(List<Pair> inputs, int width, int height) {
        this.width = width;
        this.height = height;
        this.paths = getPaths(inputs, this.width, this.height);
    }

    private List<Path> getPaths(List<Pair> inputs, int width, int height){
        List<Path> resultPaths = new ArrayList<>();
        for(Pair p : inputs){
            resultPaths.add(new Path(p, width, height));
        }
        return resultPaths;
    }

    public void draw(){
        Color frameBackgroundColor = Color.decode("#d8ebe4");
        JFrame frame = new JFrame("PCB_Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((width+1)* Config.GAP +200, (height+1)* Config.GAP +200);
        frame.getContentPane().setBackground(frameBackgroundColor);
        frame.setLayout(null);
        frame.setVisible(true);
        Board board = new Board();
        board.setBounds(50,50, (width+1)* Config.GAP + Config.POINT_SIZE, (height+1)* Config.GAP + Config.POINT_SIZE);
        frame.add(board);
    }

    private class Board extends JPanel {
        Random rand;
        private final Color basicPointColor = Color.decode("#282846");
        private final Color boardBackgroundColor = Color.decode("#007580");

        public Board() {
            this.rand = new Random();
        }

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
