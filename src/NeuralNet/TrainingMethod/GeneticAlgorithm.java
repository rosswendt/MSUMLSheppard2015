package NeuralNet.TrainingMethod;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.MatrixNeuralNet;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ross Wendt
 */
public class GeneticAlgorithm extends TrainingMethodInterface {
    ArrayList<double[]> Population = new ArrayList<>();
    
    
    @Override
    public void applyMethod() {
        initializePopulation();
        int popSize = Population.size();
        
        for (int i = 0; i < popSize; i++) { 
            Random rnd = new Random();
            double roll = rnd.nextDouble();
            double[] x = eliteSelection();
            double[] y = randomSelection();
            double[] child = crossover(x,y);
            double[] mutant = new double[Driver.dimension];
            
            if ( roll < Driver.mutationRate ) {
                mutant = mutate(child);
            }
            
            
            if (popSize == Driver.populationSize) {
                if (fitness(mutant) > fitness(y)) {
                    replace(y, mutant);
                } else if (fitness(mutant) > fitness(x)) {
                    replace(x,mutant);
                }
            } else {
                Population.add(mutant);
            }
        } 
        //updateWeights(neuralNet);
        //updateBiases(neuralNet);
        
    }
    
    public double[] crossover(double[] parent1, double[] parent2) {
        double[] d = new double[Driver.dimension];
        
        for (int i = 0; i < parent1.length; i++) {
            Random rnd = new Random();
            double val = rnd.nextDouble();
            
            if (Driver.crossoverRate >  val) {
                double temp = parent1[i];
                parent1[i] = parent2[i];
                parent2[i] = temp;                
            }
        }
        
        
        return d;
    }
    
    public void initializePopulation() {
    if (Population.isEmpty() == true) {
            for (int i = 0; i < neuralNet.weightMatrices.length; i++) {
                for (int columns = 0; columns < neuralNet.weightMatrices[0].getColumns(); columns ++ ) {
                    for (int rows = 0; rows < neuralNet.weightMatrices[0].getRows(); rows++) {
                        double[] d = new double[neuralNet.weightMatrices[0].getRows()];
                        d[rows] = neuralNet.weightMatrices[i].getArray()[rows][columns];
                        Population.add(d);
                    }
            } 
        }
    }
    }
    
    public double[] randomSelection() {
        Random rnd = new Random(Population.size());
        int randIndex = new Random().nextInt(Population.size());
        double[] d = new double[Driver.dimension];
        
        d = Population.get(randIndex);
        return d;
    }
    public double[] eliteSelection() {
        double[] select = new double[Driver.dimension];
        for (int i = 0; i < Population.size() - 1; i++) {
            double[] d1 = Population.get(i);
            double[] d2 = Population.get(i+1);
            double d1fitness = fitness(d1);
            double d2fitness = fitness(d2);
            if (d1fitness < d2fitness) {
                select = d2;
            } else {
                select = d1;
            }      
        }
        
        return select;
    }
    
    public double[] mutate(double[] in) {
        double mutationRate = Driver.mutationRate;
        Random rnd = new Random();
        double nml = rnd.nextGaussian();
        //rnd.nextDouble();
        
        for (int i = 0; i < in.length; i++ ) {
            in[i] = in[i] + nml*Driver.upperBoundWeight;
        }
        
        return in;
        }
    
    public double fitness(double[] in) {
        return in[0];
    }

    private void replace(double[] x, double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
        

