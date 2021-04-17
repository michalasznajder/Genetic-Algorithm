package main;

import evolution.Evolution;
import evolution.SelectionMethod;
import javafx.application.Application;
import pcb_board.PCBBoard;
import utils.ChartDrawer;
import utils.DataLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
//
        DataLoader zad0 = new DataLoader("zad3.txt");
        Evolution evolution = new Evolution(zad0, 1000, 8, 80, 10, SelectionMethod.TOURNAMENT);
        evolution.evolve(10);
        System.out.println(evolution.getBest().getFitness());

//        randomSolution("zad3.txt");



        new Thread(() -> Application.launch(ChartDrawer.class)).start();
    }

    public static void randomSolution(String file) throws IOException {



        DataLoader zad0 = new DataLoader(file);
        Evolution evolution = new Evolution(zad0, 1000, 16, 80, 60, SelectionMethod.TOURNAMENT);
        long startTime = System.currentTimeMillis();
        long timeElapsed = 0;
        while(timeElapsed < 20000){
            evolution = new Evolution(zad0, 1000, 16, 80, 60, SelectionMethod.TOURNAMENT);
            long endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
        }
        evolution.getBest().draw();
        System.out.println(evolution.getBest().getFitness());

    }

    public static void experiment(String file, int populationSize, int tournamentSize, int crossoverRate, int mutationRate, SelectionMethod selectionMethod, int iterationSize) throws IOException {

        System.out.println("File " + file);
        System.out.println("Population: " + populationSize);
        System.out.println("Tournament Size " + tournamentSize);
        System.out.println("Crossover % " + crossoverRate);
        System.out.println("Mutation % " + mutationRate);
        System.out.println(selectionMethod);
        DataLoader zad0 = new DataLoader(file);
        List<Double> results = new ArrayList<>();
        List<Long> times = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            long startTime = System.currentTimeMillis();

            Evolution evolution = new Evolution(zad0, populationSize, tournamentSize, crossoverRate, mutationRate, selectionMethod);
            results.add(evolution.evolve(iterationSize));

            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;
            times.add(timeElapsed);
        }

        double sum = 0;
        double best = results.get(0);
        double worst = results.get(0);

        for(Double r : results){
            sum += r;
            if(r > best){
                best = r;
            }
            if(r < worst){
                worst = r;
            }
        }

        long timesSum = 0;
        for(Long t : times){
            timesSum += t;
        }
        timesSum = timesSum/10;



        double avg = sum/10;

        double sumOfSquaredDifferences = 0;

        for(Double r : results){
            sumOfSquaredDifferences += Math.pow(r - avg, 2);
        }

        double standardDeviation = Math.sqrt(sumOfSquaredDifferences/9);

        for(Double r : results){
            System.out.println(r);
        }
        System.out.println(best);
        System.out.println(worst);
        System.out.println(avg);
        System.out.println(standardDeviation);

        System.out.println(timesSum);
        System.out.println();
    }

    public static void report() throws IOException {

        System.out.println("\n\n\nPOPULATION\n\n\n");

        for(int population = 100; population <= 2100; population+= 500){
            experiment("zad1.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int population = 100; population <= 2100; population+= 500){
            experiment("zad2.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int population = 100; population <= 2100; population+= 500){
            experiment("zad3.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }
////
        System.out.println("\n\n\nITERATIONS SIZE\n\n\n");

        for(int iterationSize = 5; iterationSize <= 25; iterationSize+= 5){
            experiment("zad1.txt", 1000, 8, 80, 30, SelectionMethod.TOURNAMENT, iterationSize);
        }

        for(int iterationSize = 5; iterationSize <= 25; iterationSize+= 5){
            experiment("zad2.txt", 1000, 8, 80, 30, SelectionMethod.TOURNAMENT, iterationSize);
        }

        for(int iterationSize = 5; iterationSize <= 25; iterationSize+= 5){
            experiment("zad3.txt", 1000, 8, 80, 30, SelectionMethod.TOURNAMENT, iterationSize);
        }

        System.out.println("\n\n\nTOURNAMENT SIZE\n\n\n");

        for(int tournamentSize = 4; tournamentSize <= 20; tournamentSize+= 4){
            experiment("zad1.txt", 1000, tournamentSize, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int tournamentSize = 4; tournamentSize <= 20; tournamentSize+= 4){
            experiment("zad2.txt", 1000, tournamentSize, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int tournamentSize = 4; tournamentSize <= 20; tournamentSize+= 4){
            experiment("zad3.txt", 1000, tournamentSize, 80, 30, SelectionMethod.TOURNAMENT, 10);
        }

        System.out.println("\n\n\nTOURNAMENT VS ROULETTE\n\n\n");

        experiment("zad1.txt", 1000, 8, 80, 30, SelectionMethod.ROULETTE_WHEEL, 10);
        experiment("zad2.txt", 1000, 8, 80, 30, SelectionMethod.ROULETTE_WHEEL, 10);
        experiment("zad3.txt", 1000, 8, 80, 30, SelectionMethod.ROULETTE_WHEEL, 10);

        System.out.println("\n\n\nCROSSOVER PROBABILITY\n\n\n");

        for(int crossoverProbability = 50; crossoverProbability <= 100; crossoverProbability+= 10){
            experiment("zad1.txt", 1000, 8, crossoverProbability, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int crossoverProbability = 50; crossoverProbability <= 100; crossoverProbability+= 10){
            experiment("zad2.txt", 1000, 8, crossoverProbability, 30, SelectionMethod.TOURNAMENT, 10);
        }

        for(int crossoverProbability = 50; crossoverProbability <= 100; crossoverProbability+= 10){
            experiment("zad3.txt", 1000, 8, crossoverProbability, 30, SelectionMethod.TOURNAMENT, 10);
        }

        System.out.println("\n\n\nMUTATION PROBABILITY\n\n\n");

        for(int mutationProbability = 0; mutationProbability <= 100; mutationProbability+= 20){
            experiment("zad1.txt", 100, 8, 80, mutationProbability, SelectionMethod.TOURNAMENT, 50);
        }

        for(int mutationProbability = 0; mutationProbability <= 100; mutationProbability+= 20){
            experiment("zad2.txt", 100, 8, 80, mutationProbability, SelectionMethod.TOURNAMENT, 50);
        }

        for(int mutationProbability = 0; mutationProbability <= 100; mutationProbability+= 20){
            experiment("zad3.txt", 100, 8, 80, mutationProbability, SelectionMethod.TOURNAMENT, 50);
        }

    }
}
