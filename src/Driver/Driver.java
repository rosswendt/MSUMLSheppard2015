package Driver;
import DataPack.*;
import NeuralNet.*;

import java.util.Random;

/**
 *
 * @author Angus Tomlinson
 */
public class Driver {
    
    //NOTE: WHEN RUNNING THIS NEURAL NETWORK FOR THE ROSENBROCK FUNCTION, IT ALWAYS SEEMED TO CONVERGE ON SOME
    //LOCAL MINIMUM OR WOULD NOT CONVERGE AT ALL. WHEN THE NEURAL NETWORK WAS RUN FOR OTHER FUNCTIONS LIKE z = xy, 
    //THE NEURAL NETWORK WOULD CONVERGE TO ZERO. THEREFORE IT WAS CONCLUDED THAT WE EITHER HAD INCORRECT PARAMETERS 
    //OR BACK PROPAGATION WAS NOT OPTIMALLY SET UP FOR APPROXIMATING THE ROSENBROCK FUNCTION. THIS SEEMED 
    //A REASONABLE CONCLUSION GIVEN THE DIFFICULTY OF FINDING THE GLOBAL MINIMUM OF THE ROSENBROCK FUNCTION.

    //tunable parameters
    private static final double[] TESTED_ROSENBROCK_DOMAIN = {-1, 1};
    private static final int SAMPLES_IN_DATASET = 1000; // size of dataset
    private static final int K = 10; // number of folds
    private static final int N = 2;
    private static final int EPOCH_LIMIT = 10000;

    private static final int[] MULTILAYER_NN_HIDDEN_LAYERS = {2, 2}; // size of array is equivalent to number of layers;
    // numbers stored in array are the hidden nodes per
    // per layer
    private static final int[] RBF_HIDDEN_NODES = {3};

    private static final double UPPER_BOUND_INITIALIZATION_WEIGHT = 1;
    private static final double UPPER_BOUND_INITIALIZATION_BIAS = 1;

    private static final double ETA = 0.9;
    // momentum parameter can be adjusted to turn momentum on and off
    private static final double MOMENTUM_PARAMETER = 0;

    // tunable parameters for gaussian activation function
    private static final int R = 1;
    private static final int C = 2;

    public static void main(String[] args) {

        // initialize dataset
        double[][] xDataSet = initializeXDataSet(SAMPLES_IN_DATASET, N, TESTED_ROSENBROCK_DOMAIN);
        double[][] yDataSet = initializeYDataSet(xDataSet);

        //initialize k subsets
        int[][] subsets = initializeSubsets(SAMPLES_IN_DATASET, K);

        double[] inputLayer = new double[N];
        double[] outputLayer = {0};
        Matrix meanSquaredErrorTraining;
        Matrix meanSquaredErrorTesting;
        int meansSquaredErrorDivisor = (K - 1) * (subsets[0].length);

        // code to run multilayer feedforward neural network using k-fold cross validation
        System.out.println("Run Multilayer Feedforward Neural Network:");

        NeuralNet neuralNet = new NeuralNet(inputLayer, outputLayer, MULTILAYER_NN_HIDDEN_LAYERS, UPPER_BOUND_INITIALIZATION_WEIGHT, UPPER_BOUND_INITIALIZATION_BIAS, ETA,
                MOMENTUM_PARAMETER, R, C);
        for (int epoch = 0; epoch < EPOCH_LIMIT; epoch++) {
            System.out.println("Epoch" + epoch + ":");
            meanSquaredErrorTraining = new Matrix(new double[1][outputLayer.length]);
            meanSquaredErrorTesting = new Matrix(new double[1][outputLayer.length]);
            for (int testCounter = 0; testCounter < K; testCounter++) {
                int count = 0;
                for (int trainingCounter = 0; trainingCounter < subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < subsets[trainingCounter].length; i++) {
                            neuralNet.setInputMatrix(xDataSet[subsets[trainingCounter][i]]);
                            neuralNet.setTargetOutputMatrix(yDataSet[subsets[trainingCounter][i]]);
                            neuralNet.forwardPropagation();
                            meanSquaredErrorTraining = MatrixOperations.addMatrices(meanSquaredErrorTraining, neuralNet.getError());
                            neuralNet.backPropagation();
                        }
                    }
                }

                for (int i = 0; i < subsets[testCounter].length; i++) {
                    neuralNet.setInputMatrix(xDataSet[subsets[testCounter][i]]);
                    neuralNet.setTargetOutputMatrix(yDataSet[subsets[testCounter][i]]);
                    neuralNet.forwardPropagation();
                    meanSquaredErrorTesting = MatrixOperations.addMatrices(meanSquaredErrorTesting, neuralNet.getError());
                }
            }
            System.out.println("MeanSquaredError for training:" + ((meanSquaredErrorTraining.getMatrixValues()[0][0]) / (meansSquaredErrorDivisor * K)));
            System.out.println("Root Mean Squared Error for training:" + Math.sqrt((meanSquaredErrorTraining.getMatrixValues()[0][0]) / (meansSquaredErrorDivisor * K)));
            System.out.println("MeanSquaredError for testing:" + (meanSquaredErrorTesting.getMatrixValues()[0][0] / (subsets[0].length * K)));
            System.out.println("Root Mean Squared Error for testing:" + Math.sqrt((meanSquaredErrorTesting.getMatrixValues()[0][0] / (subsets[0].length * K))));
            System.out.println();
        }

        //code to run rbf using cross validation
        System.out.println("Run RBF: ");

        RBF rbf = new RBF(inputLayer, outputLayer, RBF_HIDDEN_NODES, UPPER_BOUND_INITIALIZATION_WEIGHT, UPPER_BOUND_INITIALIZATION_BIAS, ETA,
                MOMENTUM_PARAMETER, R, C);
        for (int epoch = 0; epoch < EPOCH_LIMIT; epoch++) {
            System.out.println("Epoch" + epoch + ":");
            meanSquaredErrorTraining = new Matrix(new double[1][outputLayer.length]);
            meanSquaredErrorTesting = new Matrix(new double[1][outputLayer.length]);
            for (int testCounter = 0; testCounter < K; testCounter++) {
                int count = 0;
                for (int trainingCounter = 0; trainingCounter < subsets.length; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < subsets[trainingCounter].length; i++) {
                            rbf.setInputMatrix(xDataSet[subsets[trainingCounter][i]]);
                            rbf.setTargetOutputMatrix(yDataSet[subsets[trainingCounter][i]]);
                            rbf.forwardPropagation();
                            meanSquaredErrorTraining = MatrixOperations.addMatrices(meanSquaredErrorTraining, rbf.getError());
                            rbf.backPropagation();
                        }
                    }
                }

                for (int i = 0; i < subsets[testCounter].length; i++) {
                    rbf.setInputMatrix(xDataSet[subsets[testCounter][i]]);
                    rbf.setTargetOutputMatrix(yDataSet[subsets[testCounter][i]]);
                    rbf.forwardPropagation();
                    meanSquaredErrorTesting = MatrixOperations.addMatrices(meanSquaredErrorTesting, rbf.getError());
                }
            }
            System.out.println("MeanSquaredError for training:" + ((meanSquaredErrorTraining.getMatrixValues()[0][0]) / (meansSquaredErrorDivisor * K)));
            System.out.println("Root Mean Squared Error for training:" + Math.sqrt((meanSquaredErrorTraining.getMatrixValues()[0][0]) / (meansSquaredErrorDivisor * K)));
            System.out.println("MeanSquaredError for testing:" + (meanSquaredErrorTesting.getMatrixValues()[0][0] / (subsets[0].length * K)));
            System.out.println("Root Mean Squared Error for testing:" + Math.sqrt((meanSquaredErrorTesting.getMatrixValues()[0][0] / (subsets[0].length * K))));
            System.out.println();
        }
    }

    // assign random x values within the chosen rosenbrock domain to the dataset samples 
    public static double[][] initializeXDataSet(int samples, int n, double[] rosenbrockDomain) {
        Random rdm = new Random();
        //double stepCounter = (upperBound - lowerBound) / (samples * n);
        double[][] xDataSet = new double[samples][n];
        for (int i = 0; i < samples; i++) {
            for (int j = 0; j < n; j++) {
                xDataSet[i][j] = (rdm.nextDouble() * (rosenbrockDomain[1] - rosenbrockDomain[0])) + rosenbrockDomain[0];
            }
        }

        return xDataSet;
    }

    // calculate the rosenbrock value for each dataset sample
    public static double[][] initializeYDataSet(double[][] xDataSet) {
        double[][] yDataSet = new double[xDataSet.length][1];
        for (int i = 0; i < yDataSet.length; i++) {
            yDataSet[i][0] = Functions.computeRosenBrockOutVal(xDataSet[i]);
        }
        return yDataSet;
    }

    // randomly assigns samples withing the dataset to the k subsets
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

    // simple search function used for the random initialization of the k subsets
    public static boolean containsValue(int[] array, int a) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                return true;
            }
        }
        return false;
    }
}
