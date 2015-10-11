package NeuralNet;

import NeuralNetHelper.*;

/**
 *
 * @author Angus Tomlinson
 */
public class NeuralNet {

    private Matrix inputMatrix;
    private Matrix outputMatrix;
    private Matrix[] weightMatrixes;
    private Matrix[] sMatrixes;
    private Matrix[] zMatrixes;
    private final MatrixOperations matrixOperations = new MatrixOperations();

    public NeuralNet(double[][] input, double[][] output, int[] hiddenLayers) {
        inputMatrix = new Matrix(input);
        outputMatrix = new Matrix(output);
        weightMatrixes = new Matrix[hiddenLayers.length + 1];
        if (weightMatrixes.length == 1) {
            weightMatrixes[0] = new Matrix(new double[inputMatrix.getColumns()][outputMatrix.getColumns()]);
        } else {
            weightMatrixes[0] = new Matrix(new double[inputMatrix.getColumns()][hiddenLayers[0]]);
            for (int i = 1; i < hiddenLayers.length; i++) {
                weightMatrixes[i] = new Matrix(new double[hiddenLayers[i - 1]][hiddenLayers[i]]);
            }
            weightMatrixes[weightMatrixes.length - 1] = new Matrix(new double[hiddenLayers[hiddenLayers.length - 1]][outputMatrix.getColumns()]);
        }
        setWeights();
        sMatrixes = new Matrix[hiddenLayers.length];
        zMatrixes = new Matrix[hiddenLayers.length];
    }

    public void setWeights() {
        for (int i = 0; i < weightMatrixes.length; i++) {
            weightMatrixes[i] = matrixOperations.randomlyInitializeMatrix(weightMatrixes[i], 2);
        }
    }

    public void forwardPropagation() {
        sMatrixes[0] = matrixOperations.multiplyMatrixes(inputMatrix, weightMatrixes[0]);
        zMatrixes[0] = matrixOperations.scalarMultiply(2, sMatrixes[0]);
        for (int i = 1; i < sMatrixes.length; i++) {
            sMatrixes[i] = matrixOperations.multiplyMatrixes(zMatrixes[i - 1], weightMatrixes[i]);
            zMatrixes[i] = matrixOperations.scalarMultiply(2, sMatrixes[i]);
        }
        outputMatrix = matrixOperations.multiplyMatrixes(zMatrixes[zMatrixes.length - 1],
                weightMatrixes[weightMatrixes.length - 1]);
    }

    public Matrix getInputMatrix() {
        return inputMatrix;
    }

    public Matrix getOutputMatrix() {
        return outputMatrix;
    }

    public Matrix[] getWeightMatrixes() {
        return weightMatrixes;
    }
}
