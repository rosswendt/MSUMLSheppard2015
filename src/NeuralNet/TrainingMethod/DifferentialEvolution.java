package NeuralNet.TrainingMethod;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import java.util.ArrayList;
import java.util.Random;
import java.math.*;

/**
 *
 * @author Ross Wendt
 */
public class DifferentialEvolution implements TrainingMethodInterface {
    ArrayList<Double> A = new ArrayList<>();
    int lengthWeights = Driver.hiddenLayers.length;
    int lengthMatrix = Driver.dataSetSize;    
    //int populationSize = Driver.populationSize;
    //ArrayList<double[]> Population = new ArrayList<>();
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
       // for (int t = 0; t < Driver.time; t++)
            for (int i = 0; i < lengthWeights; i++) { // go through each weights layer
                //System.out.println(i);
                for (int k = 0; k < neuralNet.weightMatrices[0].getColumns(); k++) { //there are |Driver.dataSetSize| individuals
                    //System.out.println(k);
                    double[] example = new double[neuralNet.weightMatrices[0].getRows()];
                    //System.out.println(example.length);
                    //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
                    for (int z = 0; z < neuralNet.weightMatrices[0].getRows(); z++ ) {
                        example[z] = neuralNet.weightMatrices[i].getArray()[z][k];
                        //System.out.println(z);
                    }
                    //System.out.println("Example length is " + example.length);

                    //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
                    Matrix exampleMatrix = new Matrix(example);

                    double[] mutantVector = createMutant(i);
                    //System.out.println("Mutant length is " + mutantVector.length);
                    double[] crossover = createOffspring(mutantVector, example);
                    Matrix crossoverMatrix = new Matrix(crossover);


                    double examplesEval = evaluate(exampleMatrix, i, k);
                    double candidatesEval = evaluate(crossoverMatrix, i, k);
                    //System.out.println(examplesEval - candidatesEval);
                    amplify(crossover);
                    if (candidatesEval < examplesEval ) {
                        for (int z = 0; z < neuralNet.weightMatrices[0].getRows(); z++ ) {
                        //double amplified = Driver.amplify*crossover[z];
                        neuralNet.weightMatrices[i].getArray()[z][k] = crossover[z];
                    }

                       // neuralNet.weightMatrices[i].getArray()[k] = crossover;


                    }

                    /*neuralNet.deltaZMatrices[neuralNet.deltaZMatrices.length - 1] = neuralNet.weightMatrices[0];

                    for (int u = 0; u < neuralNet.deltaZMatrices.length; u++) {
                        neuralNet.deltaZMatrices[u] = neuralNet.weightMatrices[i];
                    }

                    updateWeights(neuralNet);
                    updateBiases(neuralNet);
                    //updateBiases(neuralNet);
                    */
                    //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
                }
        }
    }
    
 
    double evaluate(Matrix candidate, int i, int k) {
        //Matrix temp = new Matrix(neuralNet.weightMatrices[i].getArray()[k]);
        //double[] example = neuralNet.weightMatrices[i].getArray()[k];
        double potentialOutput = 0;
        double error = 0;
        for (int l = 0; l < candidate.getColumns(); l++ ) {
            potentialOutput += candidate.getArray()[0][l] * neuralNet.getInputMatrix().getArray()[0][l];
        }
        
        for (int q = 0; q < neuralNet.targetOutput.getColumns(); q++ ) {
            error += Math.pow(potentialOutput - neuralNet.targetOutput.getArray()[0][q],2.0);
        }
        
        error = error / neuralNet.targetOutput.getColumns();
        error = Math.abs(error);
        
        Math.sqrt(error);
        

        //neuralNet.weightMatrices[i].getArray()[k] = candidate.getArray()[0];

        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        //neuralNet.forwardPropagation();
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
       
        //Matrix errorMatrix = neuralNet.getError();
        //int columns = errorMatrix.getColumns();
        //double summedError = 0;
        
        //for (int l = 0; l < columns; l++ ) {
        //    summedError += neuralNet.getError().getArray()[0][l];
        //}
        
        //neuralNet.weightMatrices[i].getArray()[k] = temp.getArray()[0];
        
        //neuralNet.weightMatrices[i].getArray()[k] = [0];
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        //neuralNet.forwardPropagation();
        //neuralNet.weightMatrices[i] = MatrixOperations.transpose(neuralNet.weightMatrices[i]);
        
       // neuralNet.weightMatrices[i].getArray()[k] = temp.getArray()[0];
        
        //Matrix error = MatrixOperations.subtractMatrices(k, k)
        
        return error;
   }

    double[] createMutant(int in) {
        //System.out.println(Driver.dataSetSize);
        //neuralNet.weightMatrices[in] = MatrixOperations.transpose(neuralNet.weightMatrices[in]);
        int size = neuralNet.weightMatrices[0].getRows();
        int chooseIndex = new Random().nextInt(size);
        //System.out.println(neuralNet.weightMatrices[in].getArray());
        
        //
        double[] choose = new double[neuralNet.weightMatrices[0].getRows()];
        int vectorIndex = new Random().nextInt(size); 
        for (int i = 0; i < size; i++ ) {
            choose[i] = neuralNet.weightMatrices[in].getArray()[vectorIndex][i];
        }
        
        Matrix chosenVector = new Matrix(choose);
        
        
        //Matrix chosenVector = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndex]);       
        
        //int chooseIndexExampleOne = new Random().nextInt(size); 
        double[] choose2 = new double[neuralNet.weightMatrices[0].getRows()];
        vectorIndex = new Random().nextInt(size); 
        for (int i = 0; i < size; i++ ) {
            choose2[i] = neuralNet.weightMatrices[in].getArray()[vectorIndex][i];
        }
        
        
        Matrix exampleOne = new Matrix(choose2);
        
        double[] choose3 = new double[neuralNet.weightMatrices[0].getRows()];
        vectorIndex = new Random().nextInt(size); 
        for (int i = 0; i < size; i++ ) {
            choose3[i] = neuralNet.weightMatrices[in].getArray()[vectorIndex][i];
        }

        //int chooseIndexExampleTwo = new Random().nextInt(size);
        Matrix exampleTwo = new Matrix(choose3);
        
        
        
        
        /*
        "reroll" for new values if any pair is equal
        */
        while( exampleOne.equals(exampleTwo)|| exampleTwo.equals(chosenVector) || chosenVector.equals(exampleOne) ) {
            int newIndex1 = new Random().nextInt(size);
            int newIndex2 = new Random().nextInt(size);
            //System.out.println(Driver.dimension);
            //System.out.println(newIndex1 + " " + newIndex2);
            
            choose2 = new double[neuralNet.weightMatrices[0].getRows()];
            vectorIndex = new Random().nextInt(size); 
            for (int i = 0; i < size; i++ ) {
                choose2[i] = neuralNet.weightMatrices[in].getArray()[vectorIndex][i];
            }
        
        
            exampleOne = new Matrix(choose2);
        
            choose3 = new double[neuralNet.weightMatrices[0].getRows()];
            vectorIndex = new Random().nextInt(size); 
            for (int i = 0; i < size; i++ ) {
                choose3[i] = neuralNet.weightMatrices[in].getArray()[vectorIndex][i];
            }
            
            exampleTwo = new Matrix(choose3);
       
        } 
        //neuralNet.weightMatrices[in] = MatrixOperations.transpose(neuralNet.weightMatrices[in]);
        //create u_i(t)
        
        Matrix difference = new Matrix(MatrixOperations.subtractMatrices(exampleOne, exampleTwo).getArray());
        difference = MatrixOperations.scalarMultiply(Driver.beta, difference);
        difference = MatrixOperations.addMatrices(chosenVector, difference);    
        
        //neuralNet.weightMatrices[in] = MatrixOperations.transpose(neuralNet.weightMatrices[in]);
        
        return difference.getArray()[0];
    }
    
    private double[] createOffspring(double[] mutant, double[] example) {
        double[] crossingOver = new double[mutant.length];
        double rate = Driver.crossoverRate;
        if ( mutant.length != example.length ) {
            System.out.println("MUTANT AND EXAMPLE LENGTH DIFFER");
        }
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

    private void amplify(double[] crossover) {
        for (int i = 0; i < crossover.length; i++ ) {
            crossover[i] *= Driver.amplify;
        }
    }
}
