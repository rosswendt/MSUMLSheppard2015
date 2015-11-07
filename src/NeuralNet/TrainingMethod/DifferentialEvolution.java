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
    
    @Override
    public void applyMethod() {
        
        /*
        Need to do this FOR EACH HIDDEN LAYER, taking into account their size
        */

        for (int i = 0; i < lengthWeights; i++) { // go through each weights layer
            for (int k = 0; k < Driver.dataSetSize; k++) { //there are |Driver.dataSetSize| individuals
                Matrix example = new Matrix(neuralNet.weightMatrices[i].getArray()[k]);
                //double[] example = neuralNet.weightMatrices[i].getArray()[k];
                Matrix mutation = new Matrix(createTrialVector(i));
                //double[] mutation = createTrialVector(i); //mutation mutates on the example
                Matrix crossover = new Matrix(createOffspring(mutation.getArray()[0]));
                //double[] crossover = createOffspring(mutation); //crossover creates one offspring from the example
                
                if (evaluate(crossover, i, k) > evaluate(example, i, k) ) {
                    Matrix replaceVal = crossover;
                    neuralNet.weightMatrices[i].getArray()[k] = replaceVal.getArray()[0];
                }
            }
        }
    }
    
    double evaluate(Matrix candidate, int i, int k) {
        /*
        Basically need to copy the weight matrix, update that weight matrix to
        a new version with all the same values EXCEPT for we update the matrix
        in the right place, with the crossover attribute, or the example attribute
        
        So we need to create two new matrices, based off the current
        */
        
        Matrix temp = new Matrix(neuralNet.weightMatrices[i].getArray()[k]);
        
        neuralNet.weightMatrices[i].getArray()[k] = candidate.getArray()[0];
        neuralNet.forwardPropagation();
        
        double summedError = 0;
        
        for (int l = 0; l < neuralNet.getError().getArray().length; l++) {
            for (int z = 0; z < neuralNet.getError().getArray()[0].length; z++) {
                summedError += neuralNet.getError().getArray()[l][z];
            }
        }
            
            
        neuralNet.weightMatrices[i].getArray()[k] = temp.getArray()[0];
        neuralNet.forwardPropagation();
        
        
        return summedError;
   }

    double[] createTrialVector(int in) {
        System.out.println(Driver.dataSetSize);
        int chooseIndex = new Random().nextInt(Driver.dataSetSize);
        Matrix chosenVector = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndex]);       
        
        int chooseIndexExampleOne = new Random().nextInt(Driver.dataSetSize); 
        Matrix exampleOne = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndexExampleOne]);

        int chooseIndexExampleTwo = new Random().nextInt(Driver.dataSetSize);
        Matrix exampleTwo = new Matrix(neuralNet.weightMatrices[in].getArray()[chooseIndexExampleTwo]);
        
        
        
        
        /*
        "reroll" for new values if any pair is equal
        */
        while( exampleOne.equals(exampleTwo)|| exampleTwo.equals(chosenVector) || chosenVector.equals(exampleOne) ) {
            int newIndex1 = new Random().nextInt(Driver.dataSetSize);
            int newIndex2 = new Random().nextInt(Driver.dataSetSize);
            System.out.println(Driver.dataSetSize);
            System.out.println(newIndex1 + " " + newIndex2);
            
            exampleOne = new Matrix(neuralNet.weightMatrices[in].getArray()[newIndex1]);
            exampleTwo = new Matrix(neuralNet.weightMatrices[in].getArray()[newIndex2]);
       
        } 
        
        //create u_i(t)
        
        Matrix subtract = MatrixOperations.subtractMatrices(exampleOne, exampleTwo);
        Matrix applyScalar = MatrixOperations.scalarMultiply(Driver.beta, subtract);
        Matrix createdVector = MatrixOperations.addMatrices(chosenVector, applyScalar);       
        
        return createdVector.getArray()[0];
    }

    double[] createOffspring(double[] in) {
        //double[] a = new double[5];
        return in;
    }
}
