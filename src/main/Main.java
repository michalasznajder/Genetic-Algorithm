package main;
import evolution.Evolution;
import evolution.Population;
import evolution.SelectionMethod;
import pcb_board.PCBBoard;
import utils.DataLoader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad3.txt");
        Evolution evolution = new Evolution(zad0, 10000, 8, 80, 5, SelectionMethod.TOURNAMENT);
        evolution.evolve(5);

    }
}
