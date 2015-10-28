package NeuralNet;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.NeuralNet;
import NeuralNet.TrainingMethod.TrainingMethodInterface;

/**
 *
 * @author Ross Wendt
 */
public class NeuralNetDriver {
    
    double testSum = 0;
    double trainSum = 0;
    
    boolean runWithOutput = true;
    NeuralNet neuralNet;
    Driver D = new Driver();

    public NeuralNetDriver(NeuralNet inNeuralNet) {
        neuralNet = inNeuralNet;
    }
    
    public void runTest(boolean doOutput) {
        if ( doOutput == true ) {
            runWithOutput();
        } else {
            //NOT IMPLEMENTED runTestWithoutOutput();
        }
    }

    /*private void runTestWithOutput() {
        for (int epoch = 0; epoch < neuralNet.getEpochLimit(); epoch++ ) {
            System.out.println("********* EPOCH " + epoch + ":" + "\n");
            for (int testCounter = 0; testCounter < D.k; testCounter++) {
                Driver.meanSquaredError = new Matrix(new double[1][D.outputLayer.length]);
                for (int trainingCounter = 0; trainingCounter < Driver.subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        
                        
                        This sets up the initial input and output matrices,
                        and then does feedforward and backprop.
                        
                        
                        //
                        for (int i = 0; i < D.subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(D.xDataSet[D.subsets[trainingCounter][i]]);
                            neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            D.meanSquaredError = MatrixOperations.addMatrices(D.meanSquaredError, neuralNet.getError());
                            neuralNet.getTrainingMethodInterface().applyMethod(neuralNet);
                            //neuralNet.backPropagation();
                        }
                    }
                }
                //System.out.println("MeanSquaredError for the training set:" + ((D.meanSquaredError.getArray()[0][0]) / D.meansSquaredErrorDivisor));
                System.out.println("RMS TRAIN:" + Math.sqrt((D.meanSquaredError.getArray()[0][0]) / D.meansSquaredErrorDivisor) + "\n") ;
                
                
                D.meanSquaredError = new Matrix(new double[1][D.outputLayer.length]);
                
                for (int i = 0; i < D.subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(D.xDataSet[D.subsets[testCounter][i]]);
                    neuralNet.setTargetOutputMatrix(D.yDataSet[D.subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    D.meanSquaredError = MatrixOperations.addMatrices(D.meanSquaredError, neuralNet.getError());
                }
                //System.out.println("MeanSquaredError for the test set:" + (D.meanSquaredError.getArray()[0][0] / (D.subsets[testCounter].length)));
                System.out.println("RMS TEST:" + Math.sqrt((D.meanSquaredError.getArray()[0][0] / (D.subsets[testCounter].length))) + "\n");
                //trainSum += Math.sqrt((D.meanSquaredError.getArray()[0][0]) / D.meansSquaredErrorDivisor);
                //testSum += Math.sqrt((D.meanSquaredError.getArray()[0][0] / (D.subsets[testCounter].length)));


            }
        }
    } */  
    
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
