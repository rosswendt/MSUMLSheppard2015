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
        int dataSetSize = 1000; // make sure this number is divisible by k
        int k = 10; // number of folds
        int n = 2;
        
        double[][] xDataSet = initializeXDataSet(dataSetSize, n, lowerBound, upperBound);
        //double[][] yDataSet = initializeYDataSet(xDataSet);
        
        
        
        double[][] matrixA = {{4, 2}};//xDataSet[0];
        System.out.println();
        double[][] matrixB = {{20}};//yDataSet[0];
        System.out.println();
        int[] hiddenLayers = {1};

        NeuralNet neuralNet = new NeuralNet(matrixA, matrixB, hiddenLayers);

        neuralNet.forwardPropagation();
        Matrix c = neuralNet.getError();

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
        for (int l = 0; l < 1000; l++) {
            neuralNet.backPropagation();
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
            c = neuralNet.getTargetOutputMatrix();
            
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
            c = neuralNet.getError();
            System.out.println("Error:");
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
    }
    
    public static double[][] initializeXDataSet(int samples, int n, double lowerBound, double upperBound){
        Random rdm = new Random();
        double stepCounter = (upperBound - lowerBound) / (samples * n);
        double xValue = lowerBound;
        double [][] xDataSet = new double[samples][n];
        for(int i = 0; i < samples; i++){
            for(int j = 0; j < n; j++){
                xDataSet[i][j] = (rdm.nextDouble() * (upperBound - lowerBound)) + lowerBound;
            }
        }
        
        return xDataSet;
    }
    
    public static double[][] initializeYDataSet(double[][] xDataSet){
        double [][] yDataSet = new double[xDataSet.length][1];
        for(int i = 0; i < yDataSet.length; i++){
            yDataSet[i][0] = Functions.computeRosenBrockOutVal(xDataSet[i]);
        }
        return yDataSet;
    }
}
