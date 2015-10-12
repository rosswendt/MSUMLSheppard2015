package NeuralNet;

import NeuralNetHelper.*;

/**
 *
 * @author Angus Tomlinson
 */
public class NeuralNet {

    private double eta = 2;
    private double theta = 2;

    private Matrix inputMatrix;
    private Matrix outputMatrix;
    private Matrix targetOutputMatrix;
    private Matrix deltaOutputMatrix;
    private Matrix[] weightMatrices;
    private Matrix[] biasMatrices;
    private Matrix[] sMatrices;
    private Matrix[] zMatrices;
    private Matrix[] derivativeSMatrices;
    private Matrix[] deltaSMatrices;
    private Matrix[] deltaZMatrices;

    public NeuralNet(double[][] input, double[][] targetOutput, int[] hiddenLayers) {
        inputMatrix = new Matrix(input);
        outputMatrix = new Matrix(new double[targetOutput.length][targetOutput[0].length]);
        targetOutputMatrix = new Matrix(targetOutput);
        weightMatrices = new Matrix[hiddenLayers.length + 1];
        if (weightMatrices.length == 1) {
            weightMatrices[0] = new Matrix(new double[inputMatrix.getColumns()][outputMatrix.getColumns()]);
        } else {
            weightMatrices[0] = new Matrix(new double[inputMatrix.getColumns()][hiddenLayers[0]]);
            for (int i = 1; i < hiddenLayers.length; i++) {
                weightMatrices[i] = new Matrix(new double[hiddenLayers[i - 1]][hiddenLayers[i]]);
            }
            weightMatrices[weightMatrices.length - 1] = new Matrix(new double[hiddenLayers[hiddenLayers.length - 1]][outputMatrix.getColumns()]);
        }
        setWeights();
        biasMatrices = new Matrix[hiddenLayers.length + 1];
        for (int i = 0; i < biasMatrices.length - 1; i++) {
            biasMatrices[i] = new Matrix(new double[1][hiddenLayers[i]]);
        }
        biasMatrices[biasMatrices.length - 1] = new Matrix(new double[1][outputMatrix.getColumns()]);
        setBiases();
        sMatrices = new Matrix[hiddenLayers.length + 1];
        zMatrices = new Matrix[hiddenLayers.length + 1];
        derivativeSMatrices = new Matrix[hiddenLayers.length + 1];
        deltaSMatrices = new Matrix[hiddenLayers.length + 1];
        deltaZMatrices = new Matrix[hiddenLayers.length + 1];
    }

    public void setWeights() {
        for (int i = 0; i < weightMatrices.length; i++) {
            weightMatrices[i] = MatrixOperations.randomlyInitializeMatrix(weightMatrices[i]);
        }
    }

    public void setBiases() {
        for (int i = 0; i < biasMatrices.length; i++) {
            biasMatrices[i] = MatrixOperations.initializeBiasMatrix(theta, biasMatrices[i]);
        }
    }

    public Matrix updateWeights(Matrix weightMatrix, Matrix deltaMatrix, Matrix zMatrix) {
        Matrix deltaWeightMatrix = MatrixOperations.scalarMultiply(1, MatrixOperations.scalarMultiply(eta,
                MatrixOperations.multiplyMatrixes(deltaMatrix, zMatrix)));
        weightMatrix = MatrixOperations.addMatrixes(weightMatrix, deltaWeightMatrix);
        return weightMatrix;
    }

    public Matrix updateBiases(Matrix biasMatrix, Matrix deltaMatrix) {
        Matrix deltaBiasMatrix = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(theta,
                deltaMatrix));
        biasMatrix = MatrixOperations.addMatrixes(biasMatrix, deltaBiasMatrix);
        return biasMatrix;
    }

    public void forwardPropagation() {
        sMatrices[0] = MatrixOperations.multiplyMatrixes(inputMatrix, weightMatrices[0]);
        zMatrices[0] = MatrixOperations.tanh(sMatrices[0]);
        derivativeSMatrices[0] = MatrixOperations.derivativeTanh(sMatrices[0]);
        for (int i = 1; i < sMatrices.length - 1; i++) {
            sMatrices[i] = MatrixOperations.multiplyMatrixes(zMatrices[i - 1], weightMatrices[i]);
            zMatrices[i] = MatrixOperations.tanh(sMatrices[i]);
            derivativeSMatrices[i] = MatrixOperations.derivativeTanh(sMatrices[i]);
        }
        sMatrices[sMatrices.length - 1] = MatrixOperations.multiplyMatrixes(zMatrices[zMatrices.length - 2],
                weightMatrices[weightMatrices.length - 1]);
        zMatrices[zMatrices.length - 1] = MatrixOperations.tanh(sMatrices[sMatrices.length - 1]);
        derivativeSMatrices[derivativeSMatrices.length - 1]
                = MatrixOperations.derivativeTanh(sMatrices[sMatrices.length - 1]);
        outputMatrix = zMatrices[zMatrices.length - 1];
    }

    public void backPropagation() {
        deltaOutputMatrix = MatrixOperations.hadamardProduct(MatrixOperations.
                transpose(derivativeSMatrices[derivativeSMatrices.length - 1]),
                MatrixOperations.transpose(MatrixOperations.subtractMatrixes(outputMatrix, targetOutputMatrix)));
        deltaSMatrices[0] = MatrixOperations.multiplyMatrixes(weightMatrices[weightMatrices.length - 1], deltaOutputMatrix);
        deltaZMatrices[0] = MatrixOperations.hadamardProduct(MatrixOperations.
                transpose(derivativeSMatrices[derivativeSMatrices.length - 2]), deltaSMatrices[0]);
        weightMatrices[weightMatrices.length - 1]
                = updateWeights(weightMatrices[weightMatrices.length - 1], deltaZMatrices[0], outputMatrix);
        biasMatrices[biasMatrices.length - 1]
                = updateBiases(biasMatrices[biasMatrices.length - 1], deltaZMatrices[0]);
        for (int i = 1; i < deltaSMatrices.length - 1; i++) {
            deltaSMatrices[i] = MatrixOperations.multiplyMatrixes(weightMatrices[weightMatrices.length - (i + 1)], 
                    deltaZMatrices[i - 1]);
            deltaZMatrices[i] = MatrixOperations.hadamardProduct(MatrixOperations.
                    transpose(derivativeSMatrices[derivativeSMatrices.length - (i + 1)]),
                    deltaSMatrices[i]);
            weightMatrices[weightMatrices.length - (i + 1)]
                    = updateWeights(weightMatrices[weightMatrices.length - (i + 1)], deltaZMatrices[i],
                            zMatrices[zMatrices.length - (i + 1)]);
            biasMatrices[biasMatrices.length - (i + 1)]
                    = updateBiases(biasMatrices[biasMatrices.length - (i + 1)], deltaZMatrices[i]);
        }
        deltaSMatrices[deltaSMatrices.length - 1]
                = MatrixOperations.multiplyMatrixes(weightMatrices[0], deltaZMatrices[deltaZMatrices.length - 2]);//, weightMatrices[0]);
        deltaZMatrices[zMatrices.length - 1] = deltaSMatrices[deltaSMatrices.length - 1];
        weightMatrices[0]
                = updateWeights(weightMatrices[0], deltaZMatrices[deltaZMatrices.length - 1],
                        zMatrices[0]);
        biasMatrices[0]
                = updateBiases(biasMatrices[0], deltaZMatrices[deltaZMatrices.length - 1]);
    }

    public Matrix getInputMatrix() {
        return inputMatrix;
    }

    public Matrix getOutputMatrix() {
        return outputMatrix;
    }
    
    public Matrix getTargetOutputMatrix(){
        return targetOutputMatrix;
    }

    public Matrix[] getWeightMatrixes() {
        return weightMatrices;
    }
    
    public Matrix getError(){
        Matrix errorMatrix = MatrixOperations.multiplyMatrixes(MatrixOperations.
                subtractMatrixes(outputMatrix, targetOutputMatrix), MatrixOperations.
                subtractMatrixes(outputMatrix, targetOutputMatrix));
        return errorMatrix;
    }
}
