package main;

import evolution.Evolution;
import evolution.SelectionMethod;
import utils.DataLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {


        for(int population = 1000; population <= 5000; population+= 1000){
            experiment("zad1.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT);
        }

        for(int population = 1000; population <= 5000; population+= 1000){
            experiment("zad2.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT);
        }

        for(int population = 1000; population <= 5000; population+= 1000){
            experiment("zad3.txt", population, 8, 80, 30, SelectionMethod.TOURNAMENT);
        }

//        ChartDrawer.setGenerations(5);
//        ChartDrawer.setFitness(fitnesses);

//        new Thread() {
//            @Override
//            public void run() {
//                javafx.application.Application.launch(ChartDrawer.class);
//            }
//        }.start();
    }

    public static void experiment(String file, int populationSize, int tournamentSize, int crossoverRate, int mutationRate, SelectionMethod selectionMethod) throws IOException {

        System.out.println("Experiment");
        System.out.println("File " + file);
        System.out.println("Population: " + populationSize);
        System.out.println("Tournament Size " + tournamentSize);
        System.out.println("Crossover % " + crossoverRate);
        System.out.println("Mutation % " + mutationRate);
        System.out.println(selectionMethod);
        System.out.println("\nResults: ");
        DataLoader zad0 = new DataLoader(file);
        List<Double> results = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 2;

        for(int i = 0; i < 10; i++){
            long startTime = System.currentTimeMillis();

            Evolution evolution = new Evolution(zad0, populationSize, tournamentSize, crossoverRate, mutationRate, selectionMethod);
            results.add(evolution.evolve(iterations));

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
}
