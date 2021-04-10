package main;
import pcb_board.PCBBoard;
import utils.DataLoader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad0.txt");
        for(int i = 0; i < 1; i++){
            PCBBoard board0 = new PCBBoard(zad0);
            board0.draw();
            System.out.println(board0.toString()) ;
        }


    }
}
