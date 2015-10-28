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

    public static double xValLowerBound = -1;
    public static double xValUpperBound = 1;
    public static int dataSetSize = 1000; // make sure this number is divisible by k
    public static int dimension = 5; 
    
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
    NEURAL NET TUNABLE PARAMS:
    
    Tunable parameters for the Neural Net are as follows:
    */

    public static double eta = 0.001;
    public static double upperBoundWeight = .1; //what does this do?
    public static double upperBoundBiasWeight = .1; //what does this do?
    public static double momentumParameter = .9; 
    public static int[] hiddenLayers = {80};
    public static int epochLimit = 1000;   


    /*
    K FOLDS CROSS VALIDATION:
    
    k sets the number of folds for k-fold cross validation.
    */

    public static int k = 10; // number of folds


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
                    
    NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, upperBoundWeight, upperBoundBiasWeight, eta, momentumParameter, epochLimit, activationFunction, trainingMethod);
    
    //NetworkInterface netInt = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, eta, upperBoundWeight, upperBoundBiasWeight, momentumParameter, epochLimit, isHiddenLayerZero, activationFunction, trainingMethod);


    /*************************************************************************/
    /* Setup below for various params that shouldn't need to be changed      */
    /*************************************************************************/
    
    public double[][] xDataSet = input.initializeXDataSet();
    public double[][] yDataSet = output.initializeYDataSet(xDataSet);    
    
    //public static boolean isHiddenLayerZero = false;
    public static Matrix meanSquaredError;
    public int meansSquaredErrorDivisor = (k - 1) * (subsets[0].length);       
    public static int[][] subsets = DriverHelper.initializeSubsets(dataSetSize, k);
    public static double[] inputLayer = new double[dimension];
    public static double[] outputLayer = {0};
    public Matrix meanSquaredErrorTraining;
    public Matrix meanSquaredErrorTesting;
    


    
    public static void main(String[] args) {
        
        while (true) { //this lets us break if DataSet size is not divisible by K
            if (dataSetSize % k != 0 ) {
                System.out.println("!!!!ERROR: Dataset size is NOT divisble by k!!!!");
                break;
            }

            
            MatrixNeuralNet nNet = new MatrixNeuralNet(inputLayer, outputLayer, hiddenLayers, eta, upperBoundWeight, upperBoundBiasWeight, momentumParameter, epochLimit, activationFunction, trainingMethod);
            NeuralNetDriver nNetHelper = new NeuralNetDriver(nNet);
            nNetHelper.runTest(runWithOutput);
            break; //gets us out of the loop
        }
    }
    
    
    public void runNeuralnet(){};
    
    public void runRBFNet(){};
}