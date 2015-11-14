package Driver;

import NeuralNet.NeuralNetDriver;
import Math.ActivationFunctions.*;
import DataPack.Regression.InputPack.*;
import DataPack.Regression.OutputPack.*;
import NeuralNet.*;
import Math.Matrix;
import NeuralNet.TrainingMethod.*;

/**
 *
 * @author Angus Tomlinson/ Ross Wendt/ Lukas Keller
 *
 * UPDATE 10 / 27 / 2015 : major refactoring !!!!!! Start by reading through the
 * driver to see how things work guys!!!!! !!!!!! It might not be 100% clear at
 * first but things can be changed !!!!!! very easily. :)
 *
 * TODO: Add in Angus' updates
 */
public class Driver {
    // Evolution strategy parameters:
    
    public static double initialSigma = (1.0 / 6.0);
    public static int mu = 8;
    public static int lambda = 16;
    
    
    /* Evolutionary Computation tunable Params 
    
    
     */
    public static double beta = 1;
    //public static int populationSize = 50; //must be less than dataSetSize
    public static double crossoverRate = .5;

    /*RBF TUNABLE PARAMS:
    
     ***ONLY USED FOR RBF*****
     Chose center and radius here
     */
    static double radius = 1.0;
    static double center = -9.0;

    /*
     REGRESSION PARAMETERS
    
     *****************************************************************
     ********THESE 4 PARAMETERS ARE ONLY USED FOR REGRESSION**********
     ***SKIP TO NEXT SECTION IF DATA FILES WILL BE USED FOR INPUT*****
     *** !!!!!!!!!!DATA INPUT NOT YET IMPLEMENTED!!!!!!!!!       *****
     *****************************************************************
    
     Changes these values to affect the regression parameters;
     */
    public static double xValLowerBound = -5;
    public static double xValUpperBound = 10;
    public static int dataSetSize = 1; // make sure this number is divisible by k
    public static int dimension = 2;

    /*
     NEURAL NET TUNABLE PARAMS:
    
     Tunable parameters for the Neural Net with backprop are as follows:
     */
    public static double eta = .1;
    public static double upperBoundWeight = 1; //what does this do?
    public static double upperBoundBiasWeight = 1; //what does this do?
    public static double momentumParameter = 0.05;
    public static int[] hiddenLayers = {2, 2}; //if you go over 17 nodes in a hidden layer, hyperbolic tangent freaks out... why?!?!?!
    public static int epochLimit = 1000000;


    /*
     IN/OUT VALUE SELECTION
    
     Select whether to generate input values, or use a file for input.
     Also, select whether to use an output function for regression, or use a file
     to select output values (for datasets from repository). Choose training method
     here too.
     */
    static AbstractFunction activationFunction = new HyperbolicTangent();
    static TrainingMethodInterface trainingMethod = new EvolutionStrategy();
    static GenerateInputValsInterface input = new Regression(dataSetSize, dimension, xValLowerBound, xValUpperBound);
    static AbstractGenerateOutputVals output = new Rosenbrock();

    /*
     K FOLDS CROSS VALIDATION:
    
     k sets the number of folds for k-fold cross validation.
    */
    public static int k = 1; // number of folds


    /*
     Program output parameters
     */
    public static boolean runWithOutput = true; //NOT YET IMPLEMENTED

    /**
     * *
     *
     * SELECT NNET OR RBFNET
     *
     * Choose only one pair
     *
     *
     */
    //NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, upperBoundWeight, upperBoundBiasWeight, eta, momentumParameter, epochLimit, activationFunction, trainingMethod);
    //NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, eta, upperBoundWeight, upperBoundBiasWeight, momentumParameter, epochLimit, isHiddenLayerZero, activationFunction, trainingMethod);
    /**
     * **********************************************************************
     */
    /* Setup below for various params that shouldn't need to be changed      */
    /**
     * **********************************************************************
     */
    
    // initialize dataset
    public static double[][] xDataSet = input.initializeXDataSet();
    public static double[][] yDataSet = output.initializeYDataSet(xDataSet);

    // generate randomized indexes for randomizing the dataset
    public static int[] randomizedIndexes = DriverHelper.randomizeIndexes(dataSetSize);

    // randomize the dataset using the randomized indexes and split it into k subsets
    public static double[][] randomizedXDataSet = DriverHelper.randomizeDataSet(xDataSet, randomizedIndexes);
    public static Subset[] xSubsets = DriverHelper.generateSubsets(randomizedXDataSet, k);

    public static double[][] randomizedYDataSet = DriverHelper.randomizeDataSet(yDataSet, randomizedIndexes);
    public static Subset[] ySubsets = DriverHelper.generateSubsets(randomizedYDataSet, k);

    public static double[] inputLayer = new double[dimension];
    public static double[] outputLayer = new double[1];
 
    public static MatrixNeuralNet nNet = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, upperBoundWeight, 
            upperBoundBiasWeight, eta, momentumParameter, initialSigma, mu, lambda, epochLimit, activationFunction, 
                trainingMethod);
    public static NeuralNetDriver nNetHelper = new NeuralNetDriver(nNet);

    public static void main(String[] args) {
        
        System.out.println("Randomized X DataSet:");
        
        for (int i = 0; i < randomizedXDataSet.length; i++) {
            for (int j = 0; j < randomizedXDataSet[0].length; j++) {
                System.out.print(randomizedXDataSet[i][j] + " ");
                if (randomizedXDataSet[i][j] >= 10000) {
                } else if (randomizedXDataSet[i][j] >= 1000) {
                    System.out.print(" ");
                } else if (randomizedXDataSet[i][j] >= 100) {
                    System.out.print("  ");
                } else if (randomizedXDataSet[i][j] >= 10) {
                    System.out.print("   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("X Subsets:");
        for (int k = 0; k < xSubsets.length; k++) {
            for (int i = 0; i < xSubsets[k].getSubsetValues().length; i++) {
                for (int j = 0; j < xSubsets[k].getSubsetValues()[0].length; j++) {
                    System.out.print(xSubsets[k].getSubsetValues()[i][j] + " ");
                    if (xSubsets[k].getSubsetValues()[i][j] >= 10000) {
                    } else if (xSubsets[k].getSubsetValues()[i][j] >= 1000) {
                        System.out.print(" ");
                    } else if (xSubsets[k].getSubsetValues()[i][j] >= 100) {
                        System.out.print("  ");
                    } else if (xSubsets[k].getSubsetValues()[i][j] >= 10) {
                        System.out.print("   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
        
        System.out.println("Randomized Y DataSet:");
        for (int i = 0; i < randomizedYDataSet.length; i++) {
            for (int j = 0; j < randomizedYDataSet[0].length; j++) {
                System.out.print(randomizedYDataSet[i][j] + " ");
                if (randomizedYDataSet[i][j] >= 10000) {
                } else if (randomizedYDataSet[i][j] >= 1000) {
                    System.out.print(" ");
                } else if (randomizedYDataSet[i][j] >= 100) {
                    System.out.print("  ");
                } else if (randomizedYDataSet[i][j] >= 10) {
                    System.out.print("   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Y Subsets:");
        for (int k = 0; k < ySubsets.length; k++) {
            for (int i = 0; i < ySubsets[k].getSubsetValues().length; i++) {
                for (int j = 0; j < ySubsets[k].getSubsetValues()[0].length; j++) {
                    System.out.print(ySubsets[k].getSubsetValues()[i][j] + " ");
                    if (ySubsets[k].getSubsetValues()[i][j] >= 10000) {
                    } else if (ySubsets[k].getSubsetValues()[i][j] >= 1000) {
                        System.out.print(" ");
                    } else if (ySubsets[k].getSubsetValues()[i][j] >= 100) {
                        System.out.print("  ");
                    } else if (ySubsets[k].getSubsetValues()[i][j] >= 10) {
                        System.out.print("   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
        
//        trainingMethod.initializeParents();
//        trainingMethod.applyMethod();
//        trainingMethod.evaluateIndividuals();
        if (dataSetSize % k != 0) {
            System.out.println("!!!!ERROR: Dataset size is NOT divisble by k!!!!");
        } else {
            nNetHelper.runTest(runWithOutput);
        }
    }

    public static MatrixNeuralNet getNeuralNet() {
        return nNet;
    }

    public static int getDimension() {
        return dimension;
    }
}
