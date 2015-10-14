package Driver;
import DataPack.*;
import NeuralNet.*;

import java.util.Random;

/**
 *
 * @author Angus Tomlinson
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double lowerBound = -5;
        double upperBound = 10;
        int dataSetSize = 4; // make sure this number is divisible by k
        int k = 2; // number of folds
        int n = 2;

        double[][] xDataSet = initializeXDataSet(dataSetSize, n, lowerBound, upperBound);
        double[][] yDataSet = initializeYDataSet(xDataSet);

        int[][] subsets = initializeSubsets(dataSetSize, k);

        double[] inputLayer = new double[n];
        double[] outputLayer = {0};
        int[] hiddenLayers = {1};
        Matrix meanSquaredError;

        NeuralNet neuralNet = new NeuralNet(inputLayer, outputLayer, hiddenLayers);
        for (int j = 0; j < 1000000; j++) {
            System.out.println("Epoch" + j + ":");
            for (int testCounter = 0; testCounter < k; testCounter++) {
                meanSquaredError = new Matrix(outputLayer);
                for (int trainingCounter = 0; trainingCounter < subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(xDataSet[subsets[trainingCounter][i]]);
                            neuralNet.setTargetOutputMatrix(yDataSet[subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
                            //System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
                            neuralNet.backPropagation();
                        }
                    }
                }
                System.out.println("MeanSquaredError for training set:" + meanSquaredError.getMatrixValues()[0][0] / ((k - 1)) * (dataSetSize / k));
                meanSquaredError = new Matrix(outputLayer);
                for (int i = 0; i < subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(xDataSet[subsets[testCounter][i]]);
                    neuralNet.setTargetOutputMatrix(yDataSet[subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
                    //System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
                }
                System.out.println("MeanSquaredError for test set:" + (meanSquaredError.getMatrixValues()[0][0] / (subsets[testCounter].length)));
                System.out.println();
            }
        }
        neuralNet.setInputMatrix(xDataSet[0]);
        neuralNet.setTargetOutputMatrix(yDataSet[0]);
        neuralNet.forwardPropagation();
        System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
        
//        double[] totalTargetOutput = new double[1];
//        double[] totalOutput = new double[1];
//        NeuralNet neuralNet = new NeuralNet(inputLayer, outputLayer, hiddenLayers);
//        for (int j = 0; j < 100000000; j++) {
//            System.out.println("Epoch" + j + ":");
//            totalTargetOutput[0] = 0;
//            totalOutput[0] = 0;
//            for (int testCounter = 0; testCounter < k; testCounter++) {
//                meanSquaredError = new Matrix(outputLayer);
//                for (int trainingCounter = 0; trainingCounter < subsets.length; trainingCounter++) {
//                    if (trainingCounter != testCounter) {
//                        for (int i = 0; i < subsets[trainingCounter].length; i++) {
//                            neuralNet.setInputMatrix(xDataSet[subsets[trainingCounter][i]]);
//                            neuralNet.setTargetOutputMatrix(yDataSet[subsets[trainingCounter][i]]);
//                            totalTargetOutput[0] += yDataSet[subsets[trainingCounter][i]][0];
//                            neuralNet.forwardPropagation();
//                            totalOutput[0] += neuralNet.getOutputMatrix().getMatrixValues()[0][0];
//                            meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
//                            //System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
//                            //neuralNet.backPropagation();
//                        }
//                    }
//                }
//                totalOutput[0] = totalOutput[0] / ((k - 1) * subsets[0].length);
//                totalTargetOutput[0] = totalTargetOutput[0] / ((k - 1) * subsets[0].length);
//                neuralNet.setOutputMatrix(totalOutput);
//                neuralNet.setTargetOutputMatrix(totalTargetOutput);
//                neuralNet.backPropagation();
//                System.out.println("MeanSquaredError for training set:" + meanSquaredError.getMatrixValues()[0][0] / ((k - 1)) * (dataSetSize / k));
//                meanSquaredError = new Matrix(outputLayer);
//                for (int i = 0; i < subsets[testCounter].length; i++) {
//                    neuralNet.setInputMatrix(xDataSet[subsets[testCounter][i]]);
//                    neuralNet.setTargetOutputMatrix(yDataSet[subsets[testCounter][i]]);
//                    neuralNet.forwardPropagation();
//                    meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
//                    //System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
//                }
//                System.out.println("MeanSquaredError for test set:" + (meanSquaredError.getMatrixValues()[0][0] / (subsets[testCounter].length)));
//                System.out.println();
//            }
//        }
    }

    public static double[][] initializeXDataSet(int samples, int n, double lowerBound, double upperBound) {
        Random rdm = new Random();
        //double stepCounter = (upperBound - lowerBound) / (samples * n);
        double[][] xDataSet = new double[samples][n];
        for (int i = 0; i < samples; i++) {
            for (int j = 0; j < n; j++) {
                xDataSet[i][j] = (rdm.nextDouble() * (upperBound - lowerBound)) + lowerBound;
            }
        }

        return xDataSet;
    }

    public static double[][] initializeYDataSet(double[][] xDataSet) {
        double[][] yDataSet = new double[xDataSet.length][1];
        for (int i = 0; i < yDataSet.length; i++) {
            yDataSet[i][0] = Functions.computeRosenBrockOutVal(xDataSet[i]);
        }
        return yDataSet;
    }

    public static int[][] initializeSubsets(int dataSetSize, int k) {
        int[] selectedIndexes = new int[dataSetSize];
        for (int i = 0; i < selectedIndexes.length; i++) {
            selectedIndexes[i] = -1;
        }
        Random rdm = new Random();
        int randomIndex;
        int selectedIndexesCounter = 0;
        int[][] subsets = new int[k][dataSetSize / k];
        for (int[] subset : subsets) {
            for (int j = 0; j < subsets[0].length; j++) {
                boolean counterAssigned = false;
                while (!counterAssigned) {
                    randomIndex = rdm.nextInt(dataSetSize);
                    //System.out.println(randomIndex);
                    if (!containsValue(selectedIndexes, randomIndex)) {
                        subset[j] = randomIndex;
                        selectedIndexes[selectedIndexesCounter] = randomIndex;
                        selectedIndexesCounter++;
                        counterAssigned = true;
                    }
                }
            }
        }
        return subsets;
    }

    public static boolean containsValue(int[] array, int a) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                return true;
            }
        }
        return false;
    }
}
