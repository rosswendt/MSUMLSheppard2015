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
 * UPDATE 10 / 27 / 2015 : major refactoring
 * !!!!!! Start by reading through the driver to see how things work guys!!!!!
 * !!!!!! It might not be 100% clear at first but things can be changed
 * !!!!!! very easily. :) 
 * 
 * TODO: Add in Angus's updates
 */
public class Driver {   
    
    /* Evolutionary Computation tunable Params 
    
    
    */
    
    public static double beta = .45;
    public static double time = 1000;
    public static double amplify = 100;
    //public static int populationSize = 50; //must be less than dataSetSize
    public static double crossoverRate = .3;
        
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

    public static double xValLowerBound = -2;
    public static double xValUpperBound = 2;
    public static int dataSetSize = 50; // make sure this number is divisible by k
    public static int dimension = 5; 
    
    
        /*
    NEURAL NET TUNABLE PARAMS:
    
    Tunable parameters for the Neural Net with backprop are as follows:
    */

    public static double eta = .001;
    public static double upperBoundWeight = 1.0; //what does this do?
    public static double upperBoundBiasWeight = 1.0; //what does this do?
    public static double momentumParameter = .15; 
    public static int[] hiddenLayers = {100,100}; //if you go over 17 nodes in a hidden layer, hyperbolic tangent freaks out... why?!?!?!
    public static int epochLimit = 100;   


    /*
    IN/OUT VALUE SELECTION
    
    Select whether to generate input values, or use a file for input.
    Also, select whether to use an output function for regression, or use a file
    to select output values (for datasets from repository). Choose training method
    here too.
    */
    
    static AbstractFunction activationFunction = new HyperbolicTangent();
    static TrainingMethodInterface trainingMethod = new BackPropagation();
    static GenerateInputValsInterface input = new Regression(dataSetSize, dimension, xValLowerBound, xValUpperBound);
    static AbstractGenerateOutputVals output = new TestFunction();

    
    /*
    K FOLDS CROSS VALIDATION:
    
    k sets the number of folds for k-fold cross validation.
    */
    public static int repetitions = 2;
    public static int k = 5; // number of folds


    /*
    Program output parameters
    */

    public static boolean runWithOutput = true; //NOT YET IMPLEMENTED
    
    /*** 
     * 
     * SELECT NNET OR RBFNET
     * 
     * Choose only one pair
     * 
     * 
     */
                    
    //NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, upperBoundWeight, upperBoundBiasWeight, eta, momentumParameter, epochLimit, activationFunction, trainingMethod);
    
    //NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, eta, upperBoundWeight, upperBoundBiasWeight, momentumParameter, epochLimit, isHiddenLayerZero, activationFunction, trainingMethod);


    /*************************************************************************/
    /* Setup below for various params that shouldn't need to be changed      */
    /*************************************************************************/
    
    public static double[][] xDataSet = input.initializeXDataSet();
    public static double[][] yDataSet = output.initializeYDataSet(xDataSet);    
    
    //public static boolean isHiddenLayerZero = false;
    public static Matrix meanSquaredError;     
    public static int[][] subsets = DriverHelper.initializeSubsets(dataSetSize, k);
    public static int meansSquaredErrorDivisor = (k - 1) * (subsets[0].length);  
    public static double[][] inputLayer = xDataSet;
    public static double[][] outputLayer = yDataSet;
    public static double[][] targetOutput = yDataSet;
    public static Matrix meanSquaredErrorTraining;
    public static Matrix meanSquaredErrorTesting;            
    public static MatrixNeuralNet nNet = new MatrixNeuralNet(inputLayer, targetOutput, hiddenLayers, upperBoundWeight, upperBoundBiasWeight, eta, momentumParameter, epochLimit, activationFunction, trainingMethod);
    public static NeuralNetDriver nNetHelper = new NeuralNetDriver(nNet);
    
    
    /*
    
    MatrixNeuralNet parameters order:

    double[] input, double[] targetOutput, int[] hiddenLayers, double upperBoundInitializationWeight, 
    double upperBoundInitializationBias, double eta, double momentumParameter, int inEpochLimit, 
    AbstractFunction inActivationFunctionInterface,
    TrainingMethodInterface inTrainingMethodInterface

    */
    


    
    public static void main(String[] args) {
            while (true) { //this lets us break if DataSet size is not divisible by K
                if (dataSetSize % k != 0 ) {
                    System.out.println("!!!!ERROR: Dataset size is NOT divisble by k!!!!");
                    break;
                }
                //if (dataSetSize % populationSize != 0 ) {
                //    System.out.println("ERROR POP SIZE AND DATASET SIZE INCOMPATIBLE");
                //    break;
                //}
                nNetHelper.runNeuralNet(); //initializes and runs NNET with x-validate
                break; //gets us out of the loop
        }

    }
        
public static MatrixNeuralNet getNeuralNet() {
        return nNet;
    }

public static int getDimension() {
    return dimension;
}
}