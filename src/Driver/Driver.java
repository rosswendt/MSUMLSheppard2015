package Driver;
import DataPack.*;
import NeuralNet.*;

/**
 *
 * @author Angus Tomlinson
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] matrixA = {{1, 2}};
        double[][] matrixB = {{1}};
        int[] hiddenLayers = {3};

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
        for (int k = 0; k < 1000; k++) {
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
}
