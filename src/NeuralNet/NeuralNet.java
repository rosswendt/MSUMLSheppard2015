package NeuralNet;

import NeuralNetHelper.*;

/**
 *
 * @author Angus Tomlinson
 */
public class NeuralNet {
    private int dummyActivationFunction = 2;
    private int dummyDerivativeActivationFunction;
    
    private double eta = 2;
    private int upperRandomBound = 2;
    
    private Matrix inputMatrix;
    private Matrix outputMatrix;
    private Matrix targetOutputMatrix;
    private Matrix deltaOutputMatrix;
    private Matrix[] weightMatrices;
    private Matrix[] sMatrices;
    private Matrix[] zMatrices;
    private Matrix[] derivativeSMatrices;
    private Matrix[] deltaSMatrices;
    private Matrix[] deltaZMatrices;
    private final MatrixOperations matrixOperations = new MatrixOperations();

    public NeuralNet(double[][] input, double[][] output, int[] hiddenLayers) {
        inputMatrix = new Matrix(input);
        outputMatrix = new Matrix(new double[output[0].length][output.length]);
        targetOutputMatrix = new Matrix(output);
        weightMatrices = new Matrix[hiddenLayers.length + 1];
        if (weightMatrices.length == 1) {
            weightMatrices[0] = new Matrix(new double[inputMatrix.getColumns()][outputMatrix.getColumns()]);
        } else {
            weightMatrices[0] = new Matrix(new double[inputMatrix.getColumns()][hiddenLayers[0]]);
            for (int i = 1; i < hiddenLayers.length; i++) {
                weightMatrices[i] = new Matrix(new double[hiddenLayers[i - 1]][hiddenLayers[i]]);
            }
            weightMatrices[weightMatrices.length - 1] = new Matrix(new double[hiddenLayers[hiddenLayers.length - 1]]
                    [outputMatrix.getColumns()]);
        }
        setWeights();
        sMatrices = new Matrix[hiddenLayers.length + 1];
        zMatrices = new Matrix[hiddenLayers.length + 1];
        derivativeSMatrices = new Matrix[hiddenLayers.length + 1];
        deltaSMatrices = new Matrix[hiddenLayers.length + 1];
        deltaZMatrices = new Matrix[hiddenLayers.length + 1];
    }

    public void setWeights() {
        for (int i = 0; i < weightMatrices.length; i++) {
            weightMatrices[i] = matrixOperations.randomlyInitializeMatrix(weightMatrices[i], upperRandomBound);
        }
    }
    
    public Matrix updateWeights(Matrix weightMatrix, Matrix deltaMatrix, Matrix zMatrix){
        Matrix deltaWeightMatrix = matrixOperations.scalarMultiply(-1, matrixOperations.scalarMultiply(eta, 
                matrixOperations.multiplyMatrixes(deltaMatrix, zMatrix)));
        weightMatrix = matrixOperations.addMatrixes(weightMatrix, deltaWeightMatrix);
        return weightMatrix;
    }

    public void forwardPropagation() {
        sMatrices[0] = matrixOperations.multiplyMatrixes(inputMatrix, weightMatrices[0]);
        zMatrices[0] = matrixOperations.scalarMultiply(dummyActivationFunction, sMatrices[0]);
        derivativeSMatrices[0] = matrixOperations.scalarMultiply(dummyDerivativeActivationFunction, sMatrices[0]);
        for (int i = 1; i < sMatrices.length - 1; i++) {
            sMatrices[i] = matrixOperations.multiplyMatrixes(zMatrices[i - 1], weightMatrices[i]);
            zMatrices[i] = matrixOperations.scalarMultiply(dummyActivationFunction, sMatrices[i]);
            derivativeSMatrices[i] = matrixOperations.scalarMultiply(dummyDerivativeActivationFunction, sMatrices[i]);
        }
        sMatrices[zMatrices.length - 1] = matrixOperations.multiplyMatrixes(zMatrices[zMatrices.length - 2],
                weightMatrices[weightMatrices.length - 1]);
        zMatrices[zMatrices.length - 1] = matrixOperations.scalarMultiply(dummyActivationFunction, 
                sMatrices[sMatrices.length - 1]);
        derivativeSMatrices[derivativeSMatrices.length - 1] = 
                matrixOperations.scalarMultiply(dummyDerivativeActivationFunction, sMatrices[sMatrices.length - 1]);
        outputMatrix = zMatrices[zMatrices.length - 1];
    }
    
    public void backPropagation() {
        deltaOutputMatrix = matrixOperations.hadamardProduct(derivativeSMatrices[derivativeSMatrices.length - 1], 
                matrixOperations.subtractMatrixes(outputMatrix, targetOutputMatrix));
        deltaSMatrices[0] = matrixOperations.multiplyMatrixes(deltaOutputMatrix, weightMatrices[weightMatrices.length - 1]);
        deltaZMatrices[0] = matrixOperations.hadamardProduct(derivativeSMatrices[derivativeSMatrices.length - 1], 
                deltaSMatrices[0]);
        weightMatrices[weightMatrices.length - 1] = 
                updateWeights(weightMatrices[weightMatrices.length - 1], deltaZMatrices[0], outputMatrix);
        for (int i = 1; i < deltaSMatrices.length - 1; i++) {
            deltaSMatrices[i] = matrixOperations.multiplyMatrixes(deltaZMatrices[i - 1], 
                    weightMatrices[weightMatrices.length - (i + 1)]);
            deltaZMatrices[i] = matrixOperations.hadamardProduct(derivativeSMatrices[derivativeSMatrices.length - (i + 1)], 
                    deltaSMatrices[i]);
            weightMatrices[weightMatrices.length - 1] = 
                updateWeights(weightMatrices[weightMatrices.length - (i + 1)], deltaZMatrices[i], 
                        zMatrices[zMatrices.length - (i + 1)]);
        }
        deltaSMatrices[deltaSMatrices.length - 1] = 
                matrixOperations.multiplyMatrixes(deltaZMatrices[deltaZMatrices.length -1], weightMatrices[0]);
        deltaZMatrices[zMatrices.length - 1] = deltaSMatrices[deltaSMatrices.length - 1];
            weightMatrices[weightMatrices.length - 1] = 
                updateWeights(weightMatrices[0], deltaZMatrices[deltaZMatrices.length - 1], 
                        zMatrices[0]);
    }

    public Matrix getInputMatrix() {
        return inputMatrix;
    }

    public Matrix getOutputMatrix() {
        return outputMatrix;
    }

    public Matrix[] getWeightMatrixes() {
        return weightMatrices;
    }
}
