package evolution;

import pcb_board.PCBBoard;
import utils.ChartDrawer;
import utils.DataLoader;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private final Population population;
    private final int tournamentSize;
    private final int crossoverRate;
    private final int mutationRate;
    private SelectionMethod selectionMethod;

    public Evolution(DataLoader dataLoader, int populationSize, int tournamentSize, int crossoverRate, int mutationRate, SelectionMethod selectionMethod) {
        this.tournamentSize = tournamentSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.population = new Population(dataLoader, populationSize);
        this.selectionMethod = selectionMethod;
    }

    public double evolve(int iterations){
        int i = 0;
        List<Double> fitnesses = new ArrayList<>();
        while(i < iterations){
            this.population.setIndividuals(createNewGeneration());
            this.population.makeNotBreedOnly();
            i++;
            fitnesses.add(getBest().getFitness());
//            System.out.println(i);

        }
//        getBest().draw();
        return getBest().getFitness();

    }


    public PCBBoard getBest(){
        PCBBoard best = this.population.getIndividuals().get(0);
        for(PCBBoard pcb : this.population.getIndividuals()){
            if(pcb.getFitness() > best.getFitness()){
                best = pcb;
            }
        }
        return best;
    }

    private List<PCBBoard> createNewGeneration(){
        List<PCBBoard> newGeneration = new ArrayList<>();
        while(newGeneration.size() < this.population.getSize()){
            PCBBoard mother;
            PCBBoard father;
            int i = 0;
            do {
                if(this.selectionMethod == SelectionMethod.TOURNAMENT){
                    mother = pickIndividualThroughTournament(this.population);
                    father = pickIndividualThroughTournament(this.population);
                }else{
                    mother = pickIndividualThroughRoulette(this.population);
                    father = pickIndividualThroughRoulette(this.population);
                }
                i++;
                if(i > 100){
                    System.out.println();
                }
            }while(mother == father);

            if(RandomGenerator.getInt(100) < this.crossoverRate){
                PCBBoard firstBorn = new PCBBoard(mother, father);
                PCBBoard secondBorn = new PCBBoard(father, mother);

                mutate(firstBorn);
                mutate(secondBorn);

                if(newGeneration.size() + 1 == this.population.getSize()){
                    newGeneration.add(firstBorn);
                    continue;
                }
                newGeneration.add(firstBorn);
                newGeneration.add(secondBorn);
            }else{
                if(mother.isBreedOnly() && father.isBreedOnly()){
                    continue;
                }
                if(mother.isBreedOnly()){
                    mutate(father);
                    father.setBreedOnly(true);
                    newGeneration.add(father);

                }else if(father.isBreedOnly()){
                    mutate(mother);
                    mother.setBreedOnly(true);
                    newGeneration.add(mother);

                }else {
                    mutate(mother);
                    mutate(father);
//  change for creating new individual
                    mother.setBreedOnly(true);
                    newGeneration.add(mother);
                    father.setBreedOnly(true);
                    newGeneration.add(father);
                }
            }

        }
        return newGeneration;
    }

    private void mutate(PCBBoard individual){
        if(RandomGenerator.getInt(100) < this.mutationRate) {
            individual.mutate();
        }
    }

    private PCBBoard pickIndividualThroughTournament(Population population){
        PCBBoard winner;
        List<PCBBoard> individuals = population.getIndividuals();
        List<PCBBoard> participants = new ArrayList<>();
        for(int i = 0; i < this.tournamentSize; i++){
            int index = RandomGenerator.getInt(individuals.size());
            PCBBoard randomIndividual = individuals.get(index);
            if(!(participants.contains(randomIndividual))){
                participants.add(randomIndividual);
            }else{
                i--;
            }
        }
        List<PCBBoard> nextRound = new ArrayList<>();
        while(participants.size() > 1){
            PCBBoard contender1 = participants.get(RandomGenerator.getInt(participants.size()));
            PCBBoard contender2 = participants.get(RandomGenerator.getInt(participants.size()));
            while(contender1 == contender2){
                contender2 = participants.get(RandomGenerator.getInt(participants.size()));
            }
            if(contender1.getFitness() >= contender2.getFitness()){
                nextRound.add(contender1);
            }else{
                nextRound.add(contender2);
            }
            participants.remove(contender1);
            participants.remove(contender2);
            if(participants.size() < 2){
                participants = new ArrayList<>(nextRound);
                nextRound.clear();
            }
        }
        winner = participants.get(0);
        return winner;
    }

    private PCBBoard pickIndividualThroughRoulette(Population population){
        PCBBoard winner;
        List<PCBBoard> individuals = population.getIndividuals();
        double totalFitness = 0;
        List<Double> roulette = new ArrayList<>();
        roulette.add(individuals.get(0).getFitness());
        for(int i = 1; i < individuals.size(); i++){
            roulette.add(individuals.get(i).getFitness() + roulette.get(i-1));
        }
        double shot = RandomGenerator.getDouble(roulette.get(roulette.size()-1));
        for(int i = 0; i < individuals.size(); i++){
            if(i == 0){
                if(shot <= roulette.get(0)){
                    return individuals.get(0);
                }
            }
            if(shot <= roulette.get(i) && shot > roulette.get(i-1)){
                return individuals.get(i);
            }
        }

        throw new RuntimeException("roulette hasn't picked anyone");
    }


    public Population getPopulation() {
        return population;
    }
}
