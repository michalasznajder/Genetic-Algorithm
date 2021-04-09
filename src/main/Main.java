package main;

import pcb_board.PCBBoard;
import pcb_board.Pair;
import pcb_board.Path;
import pcb_board.CustomPoint;
import utils.DataLoader;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad0.txt");
        PCBBoard board0 = new PCBBoard(zad0.getPairs(), zad0.getWidth(), zad0.getHeight());
        board0.draw();

    }
}
