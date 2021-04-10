package pcb_board;

import utils.Config;

import static utils.Config.*;

public class FitnessData {
    private final int length;
    private final int numberOfSegments;
    private final int numberOfIntersections;
    private final double fitness;

    public FitnessData(int length, int numberOfSegments, int numberOfIntersections) {
        this.length = length;
        this.numberOfSegments = numberOfSegments;
        this.numberOfIntersections = numberOfIntersections;
        this.fitness = calculateFitness(this.length, this.numberOfIntersections, this.numberOfIntersections);
    }

    private double calculateFitness(int length, int numberOfSegments, int numberOfIntersections){
        return 1.0*FITNESS_SCALE/(INTERSECTION_WEIGHT*numberOfIntersections + LENGTH_WEIGHT*length + SEGMENTS_WEIGHT*numberOfSegments);
    }

    public int getLength() {
        return length;
    }

    public int getNumberOfSegments() {
        return numberOfSegments;
    }

    public int getNumberOfIntersections() {
        return numberOfIntersections;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return "FitnessData{" +
                "length=" + length +
                ", numberOfSegments=" + numberOfSegments +
                ", numberOfIntersections=" + numberOfIntersections +
                ", fitness=" + fitness +
                '}';
    }
}
