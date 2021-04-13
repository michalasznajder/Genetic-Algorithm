package main;

import evolution.Evolution;
import evolution.SelectionMethod;
import javafx.application.Application;
import pcb_board.CustomPoint;
import pcb_board.PCBBoard;
import pcb_board.Pair;
import pcb_board.Path;
import utils.ChartDrawer;
import utils.DataLoader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        DataLoader zad0 = new DataLoader("zad3.txt");

        long startTime = System.currentTimeMillis();

        Evolution evolution = new Evolution(zad0, 1000, 4, 95, 10, SelectionMethod.ROULETTE_WHEEL);
        evolution.evolve(100);

        long endTime =System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(timeElapsed);


        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(ChartDrawer.class);
            }
        }.start();
    }
}
