package main;
import pcb_board.PCBBoard;
import utils.DataLoader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad1.txt");
        PCBBoard board0 = new PCBBoard(zad0.getPairs(), zad0.getWidth(), zad0.getHeight());
        board0.draw();

    }
}
