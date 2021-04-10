package evolution;

import pcb_board.PCBBoard;
import utils.DataLoader;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private Population population;
    private final int tournamentSize;
    private final int crossoverRate;
    private final int mutationRate;


    public Evolution(DataLoader dataLoader, int populationSize, int tournamentSize, int crossoverRate, int mutationRate) {
        this.tournamentSize = tournamentSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.population = new Population(dataLoader, populationSize);
    }

    public void evolveThroughTournament(){

    }

    private List<PCBBoard> createNewGenerationThroughTournament(Population population, int tournamentSize, int crossoverRate, int mutationRate){
        List<PCBBoard> newGeneration = new ArrayList<>();
        while(newGeneration.size() < population.getSize()){
            PCBBoard mother;
            PCBBoard father;
            do {
                mother = pickIndividualThroughTournament(population, tournamentSize);
                father = pickIndividualThroughTournament(population, tournamentSize);
            }while(mother == father);

            PCBBoard firstBorn;
            PCBBoard secondBorn;


//            if(RandomGenerator.getInt(100) < crossoverRate){
//                firstBorn = new PCBBoard(mother, father);
//                secondBorn = new PCBBoard(father, mother);
//            }else{
//                firstBorn = new PCBBoard(mother);
//                secondBorn = new PCBBoard(father);
//            }
//            if(rand.nextInt() % 100 < mutationRate){
////                firstBorn.mutate();
////                secondBorn.mutate();
//            }
//
//            if(newGeneration.size() + 1 == size){
//                newGeneration.add(firstBorn);
//            }else{
//                newGeneration.add(firstBorn);
//                newGeneration.add(secondBorn);
//            }
        }
        return newGeneration;
    }


    private PCBBoard pickIndividualThroughTournament(Population population, int tournamentSize){
        PCBBoard winner;
        List<PCBBoard> individuals = population.getIndividuals();
        List<PCBBoard> participants = new ArrayList<>();
        for(int i = 0; i < tournamentSize; i++){
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
        individuals.remove(winner);
        return winner;
    }
}
