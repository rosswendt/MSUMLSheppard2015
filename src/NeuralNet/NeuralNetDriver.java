package NeuralNet;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;

/**
 *
 * @author Ross Wendt
 */
public class NeuralNetDriver {

    double testSum = 0;
    double trainSum = 0;

    boolean runWithOutput = true;
    MatrixNeuralNet neuralNet;
    //Driver D = new Driver();

    public NeuralNetDriver(MatrixNeuralNet inNeuralNet) {
        neuralNet = inNeuralNet;
    }

    public void runTest(boolean doOutput) {
        if (doOutput == true) {
            runWithOutput();
        } else {
            //NOT IMPLEMENTED runTestWithoutOutput();
        }
    }

    public void runWithOutput() {
        neuralNet.getTrainingMethodInterface().initializeParents();
        for (int epoch = 0; epoch < neuralNet.getEpochLimit(); epoch++) {
            System.out.println("EPOCH" + epoch + ":");
            for (int testCounter = 0; testCounter < Driver.k; testCounter++) {
                System.out.println("TRAIN:");
                for (int trainingCounter = 0; trainingCounter < Driver.k; trainingCounter++) {
                    if (trainingCounter != testCounter) {
                        for (int i = 0; i < Driver.xSubsets[trainingCounter].getSamples(); i++) {
                            neuralNet.setInputMatrix(Driver.xSubsets[trainingCounter].getSubsetValues()[i]);
                            neuralNet.setTargetOutputMatrix(Driver.ySubsets[trainingCounter].getSubsetValues()[i]);
                            neuralNet.getTrainingMethodInterface().applyMethod();
                        }
                    }
                }
                System.out.println("TEST:");
                for (int i = 0; i < Driver.xSubsets[testCounter].getSamples(); i++) {
                    neuralNet.setInputMatrix(Driver.xSubsets[testCounter].getSubsetValues()[i]);
                    neuralNet.setTargetOutputMatrix(Driver.ySubsets[testCounter].getSubsetValues()[i]);
                    neuralNet.forwardPropagation();
                    System.out.println("Input:");
                    for (int k = 0; k < neuralNet.getInputMatrix().getMatrixValues().length; k++) {
                        for (int j = 0; j < neuralNet.getInputMatrix().getMatrixValues()[0].length; j++) {
                            System.out.print(neuralNet.getInputMatrix().getMatrixValues()[k][j] + " ");
                            if (neuralNet.getInputMatrix().getMatrixValues()[k][j] >= 10000) {
                            } else if (neuralNet.getInputMatrix().getMatrixValues()[k][j] >= 1000) {
                                System.out.print(" ");
                            } else if (neuralNet.getInputMatrix().getMatrixValues()[k][j] >= 100) {
                                System.out.print("  ");
                            } else if (neuralNet.getInputMatrix().getMatrixValues()[k][j] >= 10) {
                                System.out.print("   ");
                            } else {
                                System.out.print("    ");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("Output:");
                    for (int k = 0; k < neuralNet.getOutputMatrix().getMatrixValues().length; k++) {
                        for (int j = 0; j < neuralNet.getOutputMatrix().getMatrixValues()[0].length; j++) {
                            System.out.print(neuralNet.getOutputMatrix().getMatrixValues()[k][j] + " ");
                            if (neuralNet.getOutputMatrix().getMatrixValues()[k][j] >= 10000) {
                            } else if (neuralNet.getOutputMatrix().getMatrixValues()[k][j] >= 1000) {
                                System.out.print(" ");
                            } else if (neuralNet.getOutputMatrix().getMatrixValues()[k][j] >= 100) {
                                System.out.print("  ");
                            } else if (neuralNet.getOutputMatrix().getMatrixValues()[k][j] >= 10) {
                                System.out.print("   ");
                            } else {
                                System.out.print("    ");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("Target Output:");
                    for (int k = 0; k < neuralNet.getTargetOutputMatrix().getMatrixValues().length; k++) {
                        for (int j = 0; j < neuralNet.getTargetOutputMatrix().getMatrixValues()[0].length; j++) {
                            System.out.print(neuralNet.getTargetOutputMatrix().getMatrixValues()[k][j] + " ");
                            if (neuralNet.getTargetOutputMatrix().getMatrixValues()[k][j] >= 10000) {
                            } else if (neuralNet.getTargetOutputMatrix().getMatrixValues()[k][j] >= 1000) {
                                System.out.print(" ");
                            } else if (neuralNet.getTargetOutputMatrix().getMatrixValues()[k][j] >= 100) {
                                System.out.print("  ");
                            } else if (neuralNet.getTargetOutputMatrix().getMatrixValues()[k][j] >= 10) {
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
    }
}
