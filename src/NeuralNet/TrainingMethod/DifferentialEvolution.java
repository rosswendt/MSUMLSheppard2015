package NeuralNet.TrainingMethod;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.MatrixNeuralNet;
import NeuralNet.NetworkInterface;
import NeuralNet.NeuralNetDriver;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ross Wendt
 */
public class DifferentialEvolution implements TrainingMethodInterface {
    ArrayList<Double> A = new ArrayList<>();
    int lengthWeights = Driver.hiddenLayers.length;
    int lengthMatrix = Driver.dataSetSize;    
    //int populationSize = Driver.populationSize;
    ArrayList<double[]> Population = new ArrayList<>();
    //Matrix[] examples = new Matrix[Driver.populationSize];
    //Matrix[] tempMatrix = new Matrix[Driver.populationSize];
    //Matrix[] candidates = new Matrix[Driver.populationSize];
    
    //public DifferentialEvolution() {
    //    for (int counter = 0; counter < populationSize; counter++ ) {
    //        examples[counter] = new Matrix(new double[Driver.dimension]);
    //        tempMatrix[counter] = new Matrix(new double[Driver.dimension]);
    //        candidates[counter] = new Matrix(new double[Driver.dimension]);
    //    }
    //}
    
    @Override
    public void applyMethod() {
        for (int i = 0; i < lengthWeights; i++) { // go through each weights layer
            for (int k = 0; k < neuralNet.weightMatrices.length; k++) { //there are |Driver.dataSetSize| individuals
                //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
                double[] example = neuralNet.weightMatrices[i].getArray()[k];
                Matrix exampleMatrix = new Matrix(example);
                
                double[] mutantVector = createMutant(i);
                double[] crossover = createOffspring(mutantVector, example);
                Matrix crossoverMatrix = new Matrix(crossover);
                
                
                double examplesEval = evaluate(exampleMatrix, i, k);
                double candidatesEval = evaluate(crossoverMatrix, i, k);
                //System.out.println(examplesEval - candidatesEval);
                
                if (candidatesEval < examplesEval ) {
                    neuralNet.weightMatrices[i].getArray()[k] = crossover;
                }
                
                //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
            }
        }
    }
    
 
    double evaluate(Matrix candidate, int i, int k) {
        Matrix temp = new Matrix(neuralNet.weightMatrices[i].getArray()[k]);
        neuralNet.weightMatrices[i].getArray()[k] = candidate.getArray()[0];

        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        neuralNet.forwardPropagation();
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
       
        Matrix errorMatrix = neuralNet.getError();
        int columns = errorMatrix.getColumns();
        double summedError = 0;
        
        for (int l = 0; l < columns; l++ ) {
            summedError += neuralNet.getError().getArray()[0][l];
        }
        
        neuralNet.weightMatrices[i].getArray()[k] = temp.getArray()[0];
        
        //neuralNet.weightMatrices[i].getArray()[k] = [0];
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        neuralNet.forwardPropagation();
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        
        neuralNet.weightMatrices[i].getArray()[k] = temp.getArray()[0];
        
        //Matrix error = MatrixOperations.subtractMatrices(k, k)
        
        return summedError;
   }

    double[] createMutant(int in) {
        //System.out.println(Driver.dataSetSize);
        int size = neuralNet.weightMatrices[0].getArray()[0].length;
        int chooseIndex = new Random().nextInt(size);
        //System.out.println(neuralNet.weightMatrices[in].getArray());
        
        //neuralNet.weightMatrices[in] = MatrixOperations.transpose(neuralNet.weightMatrices[in]);
        Matrix chosenVector = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndex]);       
        
        int chooseIndexExampleOne = new Random().nextInt(size); 
        Matrix exampleOne = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndexExampleOne]);

        int chooseIndexExampleTwo = new Random().nextInt(size);
        Matrix exampleTwo = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndexExampleTwo]);
        
        
        
        
        /*
        "reroll" for new values if any pair is equal
        */
        while( exampleOne.equals(exampleTwo)|| exampleTwo.equals(chosenVector) || chosenVector.equals(exampleOne) ) {
            int newIndex1 = new Random().nextInt(size);
            int newIndex2 = new Random().nextInt(size);
            //System.out.println(Driver.dimension);
            //System.out.println(newIndex1 + " " + newIndex2);
            
            exampleOne = new Matrix(neuralNet.weightMatrices[in].getArray()[newIndex1]);
            exampleTwo = new Matrix(neuralNet.weightMatrices[in].getArray()[newIndex2]);
       
        } 
        //neuralNet.weightMatrices[in] = MatrixOperations.transpose(neuralNet.weightMatrices[in]);
        //create u_i(t)
        
        Matrix difference = new Matrix(MatrixOperations.subtractMatrices(exampleOne, exampleTwo).getArray());
        difference = MatrixOperations.scalarMultiply(Driver.beta, difference);
        difference = MatrixOperations.addMatrices(chosenVector, difference);       
        
        return difference.getArray()[0];
    }
    
    private double[] createOffspring(double[] mutant, double[] example) {
        double[] crossingOver = new double[mutant.length];
        double rate = Driver.crossoverRate;
        for (int j = 0; j < mutant.length; j++ ) {
            Random rnd = new Random();
            double rand = rnd.nextDouble();
            if (rand < rate) {
                crossingOver[j] = example[j];
            } else {
                crossingOver[j] = mutant[j];
            }
        }
        return crossingOver;
    }
}
