package main;

import evolution.Evolution;
import evolution.SelectionMethod;
import pcb_board.CustomPoint;
import pcb_board.PCBBoard;
import pcb_board.Pair;
import pcb_board.Path;
import utils.DataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad3.txt");
        Evolution evolution = new Evolution(zad0, 1000, 8, 90, 0, SelectionMethod.TOURNAMENT);
        evolution.evolve();
    }
}
