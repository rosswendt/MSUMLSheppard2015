package NeuralNet;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.MatrixNeuralNet;
import NeuralNet.TrainingMethod.TrainingMethodInterface;

/**
 *
 * @author Ross Wendt
 */
public class NeuralNetDriver {
    
    double testSum = 0;
    double trainSum = 0;
    
    boolean runWithOutput = true;
    MatrixNeuralNet neuralNet;
    //Driver D = new Driver();

    public NeuralNetDriver(MatrixNeuralNet inNeuralNet) {
        neuralNet = inNeuralNet;
    }
    
    public void runTest(boolean doOutput) {
        if ( doOutput == true ) {
            runWithOutput();
        } else {
            //NOT IMPLEMENTED runTestWithoutOutput();
        }
    }
    
    public void runWithOutput() {
        for (int epoch = 0; epoch < neuralNet.getEpochLimit(); epoch++) {
            System.out.println("Epoch" + epoch + ":");
            Driver.meanSquaredErrorTraining = new Matrix(new double[1][Driver.outputLayer.length]);
            Driver.meanSquaredErrorTesting = new Matrix(new double[1][Driver.outputLayer.length]);
            for (int testCounter = 0; testCounter < Driver.k; testCounter++) {
                int count = 0;
                for (int trainingCounter = 0; trainingCounter < Driver.subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < Driver.subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(Driver.xDataSet[Driver.subsets[trainingCounter][i]]);
                            //neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            Driver.meanSquaredErrorTraining = MatrixOperations.addMatrices(Driver.meanSquaredErrorTraining, neuralNet.getError());
                            neuralNet.getTrainingMethodInterface().applyMethod();
                            //neuralNet.getTrainingMethodInterface().updateWeights(neuralNet);
                        }
                    }
                }

                for (int i = 0; i < Driver.subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(Driver.xDataSet[Driver.subsets[testCounter][i]]);
                    //neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    Driver.meanSquaredErrorTesting = MatrixOperations.addMatrices(Driver.meanSquaredErrorTesting, neuralNet.getError());
                    neuralNet.getTrainingMethodInterface().applyMethod();
                }
            }
            System.out.println("MeanSquaredError for training:" + ((Driver.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (Driver.meansSquaredErrorDivisor * Driver.k)));
            System.out.println("Root Mean Squared Error for training:" + Math.sqrt((Driver.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (Driver.meansSquaredErrorDivisor * Driver.k)));
            System.out.println("MeanSquaredError for testing:" + (Driver.meanSquaredErrorTesting.getMatrixValues()[0][0] / (Driver.subsets[0].length * Driver.k)));
            System.out.println("Root Mean Squared Error for testing:" + Math.sqrt((Driver.meanSquaredErrorTesting.getMatrixValues()[0][0] / (Driver.subsets[0].length * Driver.k))));
            System.out.println();
        }
    }
}
