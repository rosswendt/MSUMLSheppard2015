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
    Driver D = new Driver();

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
            D.meanSquaredErrorTraining = new Matrix(new double[1][D.outputLayer.length]);
            D.meanSquaredErrorTesting = new Matrix(new double[1][D.outputLayer.length]);
            for (int testCounter = 0; testCounter < D.k; testCounter++) {
                int count = 0;
                for (int trainingCounter = 0; trainingCounter < D.subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < D.subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(D.xDataSet[D.subsets[trainingCounter][i]]);
                            neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            D.meanSquaredErrorTraining = MatrixOperations.addMatrices(D.meanSquaredErrorTraining, neuralNet.getError());
                            neuralNet.getTrainingMethodInterface().applyMethod(neuralNet);
                            //neuralNet.getTrainingMethodInterface().updateWeights(neuralNet);
                        }
                    }
                }

                for (int i = 0; i < D.subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(D.xDataSet[D.subsets[testCounter][i]]);
                    neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    D.meanSquaredErrorTesting = MatrixOperations.addMatrices(D.meanSquaredErrorTesting, neuralNet.getError());
                }
            }
            System.out.println("MeanSquaredError for training:" + ((D.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (D.meansSquaredErrorDivisor * D.k)));
            System.out.println("Root Mean Squared Error for training:" + Math.sqrt((D.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (D.meansSquaredErrorDivisor * D.k)));
            System.out.println("MeanSquaredError for testing:" + (D.meanSquaredErrorTesting.getMatrixValues()[0][0] / (D.subsets[0].length * D.k)));
            System.out.println("Root Mean Squared Error for testing:" + Math.sqrt((D.meanSquaredErrorTesting.getMatrixValues()[0][0] / (D.subsets[0].length * D.k))));
            System.out.println();
        }
    }
}
