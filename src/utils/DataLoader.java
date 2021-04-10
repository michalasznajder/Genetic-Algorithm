package utils;

import pcb_board.CustomPoint;
import pcb_board.Pair;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader{
    public List<Pair> pairs;
    public int width;
    public int height;

    public DataLoader(String filename) throws IOException {
        this.pairs = new ArrayList<>();
        loadData(filename);
    }

    public void loadData(String fileName) throws IOException {
        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;

        Pattern pattern = Pattern.compile("[^;]+");

        if((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            this.width = Integer.parseInt(matcher.group());
            matcher.find();
            this.height = Integer.parseInt(matcher.group());
        }
        while ((line = reader.readLine()) != null)   {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            int x1 = Integer.parseInt(matcher.group()) + 1;
            matcher.find();
            int y1 = Integer.parseInt(matcher.group()) + 1;
            matcher.find();
            int x2 = Integer.parseInt(matcher.group()) + 1;
            matcher.find();
            int y2 = Integer.parseInt(matcher.group()) + 1;
            this.pairs.add(new Pair(new CustomPoint(x1, y1), new CustomPoint(x2, y2)));
        }
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
