package Driver;
import DataPack.*;
import NeuralNet.*;
import NeuralNetHelper.Matrix;

/**
 *
 * @author Angus Tomlinson
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double stepsize = .01; //value for how little or how large we want to step through the function
        int dimension = 5; //n per vector (as mentioned in Sheppard's pdf for proj 2)
        double startVal = -1; //starting value on the domain we want to look at
        double finVal = 1; //stopping value 
        
        double[][] matrixA = generateXVal(dimension, startVal, finVal, stepsize);
        double[][] matrixB = computeRosenbrock(matrixA, stepsize);
        int[] hiddenLayers = {2, 1, 4, 5};

        NeuralNet neuralNet = new NeuralNet(matrixA, matrixB, hiddenLayers);

        Matrix c;

        for (int k = 0; k < neuralNet.getWeightMatrixes().length; k++) {
            c = neuralNet.getWeightMatrixes()[k];
            for (int i = 0; i < c.getRows(); i++) {
                for (int j = 0; j < c.getColumns(); j++) {
                    System.out.print(c.getMatrixValues()[i][j] + " ");
                    if (c.getMatrixValues()[i][j] >= 10000) {
                    } else if (c.getMatrixValues()[i][j] >= 1000) {
                        System.out.print(" ");
                    } else if (c.getMatrixValues()[i][j] >= 100) {
                        System.out.print("  ");
                    } else if (c.getMatrixValues()[i][j] >= 10) {
                        System.out.print("   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        neuralNet.forwardPropagation();
        c = neuralNet.getOutputMatrix();
        for (int i = 0; i < c.getRows(); i++) {
            for (int j = 0; j < c.getColumns(); j++) {
                System.out.print(c.getMatrixValues()[i][j] + " ");
                if (c.getMatrixValues()[i][j] >= 10000) {
                } else if (c.getMatrixValues()[i][j] >= 1000) {
                    System.out.print(" ");
                } else if (c.getMatrixValues()[i][j] >= 100) {
                    System.out.print("  ");
                } else if (c.getMatrixValues()[i][j] >= 10) {
                    System.out.print("   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }
        
    public static double[][] generateXVal(int dimension, double minval, double maxval, double step) {
        double[][] list = new double[(int) Math.round(((maxval-minval)/step)/dimension)][dimension];
        double temp = minval;
        
        for (int i = 0; i < list.length; i++ ) {
            for (int j = 0; j < list[0].length; j++ ) {
            list[i][j] = Math.round((temp / step)) * step; //This line sometimes corrects some double instability
            temp += step;
            System.out.print(list[i][j] + " ");
            
        }
            System.out.println();

        }
         
        return list;
    }
    
    /* Generates the associated y values from the input x values using the 
     *Rosenbrock function.
     */
    
    public static double[][] computeRosenbrock(double[][] input, double step) {
        
        double[][] output = new double[input.length][1]; 
        double outval = 0;
        
        for (int i = 0; i < input.length - 1; i++ ) {
            for (int j = 0; j < input[0].length; j++ ) {
            outval +=  step * ( (1 - input[i][j])*(1-input[i][j]) + 100 * ( ( input[i+1][j] - (input[i][j]*input[i][j]) )) * ( input[i+1][j] - (input[i][j]*input[i][j]) ) );
            }
            //System.out.println(outval);
            output[i][0] = outval; // assign val to element in output
            System.out.println();
            System.out.println(output[i][0]);
            outval = 0; //reset outval to 0 so the subsequent summations make sense (start with sum of 0)
        }

        return output;     
        
    }
}
