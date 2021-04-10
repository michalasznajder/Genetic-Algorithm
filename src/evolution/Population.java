package evolution;

import pcb_board.PCBBoard;
import utils.DataLoader;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<PCBBoard> individuals;

    public Population(DataLoader dataLoader, int populationSize) {
        this.individuals = createIndividuals(dataLoader, populationSize);
    }

    private List<PCBBoard> createIndividuals(DataLoader dataLoader, int size){
        List<PCBBoard> resultIndividuals = new ArrayList<>();
        for(int i = 0; i < size; i++){
            resultIndividuals.add(new PCBBoard(dataLoader));
        }
        return resultIndividuals;
    }

    public int getSize(){
        return individuals.size();
    }

    public void setIndividuals(List<PCBBoard> individuals) {
        this.individuals = individuals;
    }

    public void makeNotBreedOnly(){
        for(PCBBoard p: this.individuals){
            p.setBreedOnly(false);
        }
    }

    public List<PCBBoard> getIndividuals() {
        return individuals;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(PCBBoard p: this.individuals){
            result.append(p.toString());
        }
        return result.toString();
    }
}
