package NeuralNet;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.TrainingMethod.TrainingMethodInterface;
import java.util.ArrayList;

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
            //data is random by default so can be considered pre-shuffled
            
        for (int epoch = 0; epoch < neuralNet.getEpochLimit(); epoch++) {
            System.out.println("Epoch" + epoch + ":");
                runNeuralNet();
        }
    }
    
    /*public void runWithOutput() {
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
                    //neuralNet.getTrainingMethodInterface().applyMethod();
                }
            }
            System.out.println("MeanSquaredError for training:" + ((Driver.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (Driver.meansSquaredErrorDivisor * Driver.k)));
            System.out.println("Root Mean Squared Error for training:" + Math.sqrt((Driver.meanSquaredErrorTraining.getMatrixValues()[0][0]) / (Driver.meansSquaredErrorDivisor * Driver.k)));
            System.out.println("MeanSquaredError for testing:" + (Driver.meanSquaredErrorTesting.getMatrixValues()[0][0] / (Driver.subsets[0].length * Driver.k)));
            System.out.println("Root Mean Squared Error for testing:" + Math.sqrt((Driver.meanSquaredErrorTesting.getMatrixValues()[0][0] / (Driver.subsets[0].length * Driver.k))));
            System.out.println();
        }
    }*/
        
    private ArrayList<Matrix> partitionData() {
        int numFolds = Driver.k;
        double[] d;
        ArrayList<Matrix> A = new ArrayList<>();
        
        for (int i = 0; i < numFolds; i++ ) {
            double[][] dub = new double[Driver.dataSetSize][Driver.dimension];
            
            /*
            Initialize values
            */
            
            for (int j = 0; j < Driver.xDataSet.length; j++ ) {
                for (int k = 0; k < (Driver.xDataSet[0].length)/ numFolds; k++ ) {
                    dub[j][k] = Driver.xDataSet[j][k];
                }
            }
            
            Matrix M = new Matrix(dub);
            A.add(M);
        }
        
        
        return A;
    }

    public void runNeuralNet() {
        TrainingMethodInterface T = neuralNet.getTrainingMethodInterface();
        for (int epoch = 0; epoch < Driver.epochLimit; epoch++) {
            ArrayList<Matrix> A = partitionData();    
            for (int k = 0; k < Driver.k; k++ ) {
                Matrix temp = A.get(k);
                A.remove(temp);
                
                
                
                neuralNet.forwardPropagation(A);
                T.applyMethod(); //runs training algorithm
                getNumberWrong();
                
                A.add(temp);
            }
        }
    }

    private void getNumberWrong() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
