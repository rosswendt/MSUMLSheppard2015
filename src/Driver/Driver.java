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
        // bounds for the x value:
        double lowerBound = -5;
        double upperBound = 10;
        
        int dataSetSize = 4; // make sure this number is divisible by k
        int k = 2; // number of folds
        
        // number of x values:
        int n = 2;

        // initialize dataset:
        double[][] xDataSet = initializeXDataSet(dataSetSize, n, lowerBound, upperBound);
        double[][] yDataSet = initializeYDataSet(xDataSet);

        // set up subsets:
        int[][] subsets = initializeSubsets(dataSetSize, k);

        // set up neural network:
        double[] matrixA = {0, 0};
        double[] matrixB = {0};
        int[] hiddenLayers = {3};
        Matrix meanSquaredError;
        NeuralNet neuralNet = new NeuralNet(matrixA, matrixB, hiddenLayers);
        
        // run cross validation:
        for (int j = 0; j < 100000000; j++) {
            for (int testCounter = 0; testCounter < k; testCounter++) {
                meanSquaredError = new Matrix(matrixB);
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
                System.out.println("Epoch" + j + ":");
                System.out.println("MeanSquaredError for training set:" + meanSquaredError.getMatrixValues()[0][0] / ((k - 1)) * (dataSetSize / k));
                meanSquaredError = new Matrix(matrixB);
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
    }

    public static double[][] initializeXDataSet(int samples, int n, double lowerBound, double upperBound) {
        Random rdm = new Random();
        double stepCounter = (upperBound - lowerBound) / (samples * n);
        double xValue = lowerBound;
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
