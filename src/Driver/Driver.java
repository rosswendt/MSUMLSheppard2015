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
        double lowerBound = -1;
        double upperBound = 1;
        int dataSetSize = 1000; // make sure this number is divisible by k
        int k = 10; // number of folds
        int n = 2;
        int epochLimit = 100;

        double[][] xDataSet = initializeXDataSet(dataSetSize, n, lowerBound, upperBound);
        double[][] yDataSet = initializeYDataSet(xDataSet);

        int[][] subsets = initializeSubsets(dataSetSize, k);

        double[] inputLayer = new double[n];
        double[] outputLayer = {0};
        int[] hiddenLayers = {2,3};
        Matrix meanSquaredError;
        int meansSquaredErrorDivisor = (k - 1) * (subsets[0].length);
        System.out.println(meansSquaredErrorDivisor);

        NeuralNet neuralNet = new NeuralNet(inputLayer, outputLayer, hiddenLayers);
        for (int epoch = 0; epoch < epochLimit; epoch++) {
            System.out.println("Epoch" + epoch + ":");
            for (int testCounter = 0; testCounter < k; testCounter++) {
                meanSquaredError = new Matrix(new double[1][outputLayer.length]);
                int count = 0;
                for (int trainingCounter = 0; trainingCounter < subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(xDataSet[subsets[trainingCounter][i]]);
                            neuralNet.setTargetOutputMatrix(yDataSet[subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
                            neuralNet.backPropagation();
                        }
                    }
                }
                System.out.println("MeanSquaredError for training set:" + ((meanSquaredError.getMatrixValues()[0][0]) / meansSquaredErrorDivisor));
                
                meanSquaredError = new Matrix(new double[1][outputLayer.length]);
                for (int i = 0; i < subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(xDataSet[subsets[testCounter][i]]);
                    neuralNet.setTargetOutputMatrix(yDataSet[subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    meanSquaredError = MatrixOperations.addMatrices(meanSquaredError, neuralNet.getError());
                }
                System.out.println("MeanSquaredError for test set:" + (meanSquaredError.getMatrixValues()[0][0] / (subsets[testCounter].length)));
                System.out.println();
            }
        }
        neuralNet.setInputMatrix(xDataSet[0]);
        neuralNet.setTargetOutputMatrix(yDataSet[0]);
        neuralNet.forwardPropagation();
        System.out.println(neuralNet.getError().getMatrixValues()[0][0]);
    }

    public static double[][] initializeXDataSet(int samples, int n, double lowerBound, double upperBound) {
        Random rdm = new Random();
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
