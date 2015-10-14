package NeuralNet;

import NeuralNetHelper.*;

/**
 *
 * @author Angus Tomlinson
 */
public final class NeuralNet {
    private double upperBoundInitializationWeight = 1;
    private double upperBoundInitializationBias = 1;
    private double eta = 0.0001;
    private double momentumParameter = 0.8;
    
    private int r = 1;
    private int c = 2;

    private final boolean isHiddenLayerCountZero;
    private Matrix input;
    private Matrix output;
    private Matrix targetOutput;
    private Matrix[] weightMatrices;
    private Matrix[] biasMatrices;
    private final Matrix[] sMatrices;
    private final Matrix[] zMatrices;
    private final Matrix[] FMatrices;
    private final Matrix[] deltaZMatrices;
    private final Matrix[] lastWeightUpdates;
    private final Matrix[] lastBiasUpdates;

    // initialize the neural network
    public NeuralNet(double[] input, double[] targetOutput, int[] hiddenLayers, double upperBoundInitializationWeight, double upperBoundInitializationBias, double eta,
            double momentumParameter, int r, int c) {
        this.eta = eta;
        this.upperBoundInitializationWeight = upperBoundInitializationWeight;
        this.upperBoundInitializationBias = upperBoundInitializationBias;
        this.momentumParameter = momentumParameter;
        this.r = r;
        this.c = c;
        
        this.input = new Matrix(input);
        this.output = new Matrix(new double[targetOutput.length]);
        this.targetOutput = new Matrix(targetOutput);

        isHiddenLayerCountZero = (hiddenLayers.length == 0);

        initializeWeights(hiddenLayers);

        // the lastWeightUpdates and lastBiasUpdats are used for momentum
        lastWeightUpdates = new Matrix[weightMatrices.length];
        for (int i = 0; i < weightMatrices.length; i++) {
            lastWeightUpdates[i] = new Matrix(new double[weightMatrices[i].getRows()][weightMatrices[i].getColumns()]);
        }

        lastBiasUpdates = new Matrix[weightMatrices.length];
        for (int i = 0; i < biasMatrices.length; i++) {
            lastBiasUpdates[i] = new Matrix(new double[biasMatrices[i].getRows()][biasMatrices[i].getColumns()]);
        }

        sMatrices = new Matrix[hiddenLayers.length];
        zMatrices = new Matrix[hiddenLayers.length];
        FMatrices = new Matrix[hiddenLayers.length];

        deltaZMatrices = new Matrix[hiddenLayers.length + 1];
    }

    // randomly initialize the node and bias node weights
    public void initializeWeights(int[] hiddenLayers) {
        weightMatrices = new Matrix[hiddenLayers.length + 1];
        if (isHiddenLayerCountZero) {
            weightMatrices[0] = new Matrix(new double[this.input.getColumns()][output.getColumns()]);
        } else {
            weightMatrices[0] = new Matrix(new double[this.input.getColumns()][hiddenLayers[0]]);
            for (int i = 1; i < hiddenLayers.length; i++) {
                weightMatrices[i] = new Matrix(new double[hiddenLayers[i - 1]][hiddenLayers[i]]);
            }
            weightMatrices[weightMatrices.length - 1] = new Matrix(new double[hiddenLayers[hiddenLayers.length - 1]][output.getColumns()]);
        }

        for (int i = 0; i < weightMatrices.length; i++) {
            weightMatrices[i] = MatrixOperations.randomlyInitializeMatrix(weightMatrices[i], upperBoundInitializationWeight);
        }

        biasMatrices = new Matrix[hiddenLayers.length + 1];
        if (isHiddenLayerCountZero) {
            biasMatrices[0] = new Matrix(new double[1][output.getColumns()]);
        } else {
            for (int i = 0; i < biasMatrices.length - 1; i++) {
                biasMatrices[i] = new Matrix(new double[1][hiddenLayers[i]]);
            }
            biasMatrices[biasMatrices.length - 1] = new Matrix(new double[1][output.getColumns()]);
        }

        for (int i = 0; i < biasMatrices.length; i++) {
            biasMatrices[i] = MatrixOperations.randomlyInitializeMatrix(biasMatrices[i], upperBoundInitializationBias);
        }
    }
    
    // forward propagation:
    // S(1) = W(1) * input
    // Z(i) = f(S(i)) where f(x) is the sigmoid function
    // S(i) = W(i) * Z(i - 1)
    // F(i) = (f'(S(i)))^T
    public void forwardPropagation() {
        if (isHiddenLayerCountZero) {
            output = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(input, weightMatrices[0]), biasMatrices[0]);
        } else {
            sMatrices[0] = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(input, weightMatrices[0]), biasMatrices[0]);
            zMatrices[0] = MatrixOperations.sigmoid(sMatrices[0]);
            FMatrices[0] = MatrixOperations.transpose(MatrixOperations.derivativeSigmoid(sMatrices[0]));
            for (int i = 1; i < sMatrices.length; i++) {
                sMatrices[i] = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(zMatrices[i - 1], weightMatrices[i]), biasMatrices[i]);
                zMatrices[i] = MatrixOperations.sigmoid(sMatrices[i]);
                FMatrices[i] = MatrixOperations.transpose(MatrixOperations.derivativeSigmoid(sMatrices[i]));
            }
            output = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(zMatrices[zMatrices.length - 1],
                    weightMatrices[weightMatrices.length - 1]), biasMatrices[biasMatrices.length - 1]);
        }
    }

    // back propagation:
    // Delta(out) = (output - targetOutput)^T
    // Delta(i) = hadamard product of F(i) and (W(i) * Delta(i + 1))
    public void backPropagation() {
        deltaZMatrices[deltaZMatrices.length - 1] = MatrixOperations.transpose(MatrixOperations.subtractMatrices(output, targetOutput));

        for (int i = (deltaZMatrices.length - 2); i > -1; i--) {
            deltaZMatrices[i] = MatrixOperations.hadamardProduct(FMatrices[i],
                    MatrixOperations.multiplyMatrixes(weightMatrices[i + 1], deltaZMatrices[i + 1]));
        }
        updateWeights();
        updateBiases();
    }

    // updates the weights:
    // for the first weights: deltaW(1) = -eta * (Delta(1) * input)^T
    // for the other weights: deltaW(i) = -eta * (Delta(i) * Z(i - 1))^T
    public void updateWeights() {
        Matrix deltaWeightUpdate = MatrixOperations.addMatrices(deltaWeight(deltaZMatrices[0], input), lastWeightUpdates[0]);
        lastWeightUpdates[0] = deltaWeightUpdate;
        weightMatrices[0] = MatrixOperations.addMatrices(weightMatrices[0], deltaWeightUpdate);
        for (int i = 1; i < weightMatrices.length; i++) {
            deltaWeightUpdate = MatrixOperations.addMatrices(deltaWeight(deltaZMatrices[i], zMatrices[i - 1]), lastWeightUpdates[i]);
            lastWeightUpdates[i] = deltaWeightUpdate;
            weightMatrices[i] = MatrixOperations.addMatrices(weightMatrices[i], deltaWeightUpdate);
        }
    }

    // updates the biases:
    // for biases: deltaBias(i) = -eta * (Delta(i))^T
    public void updateBiases() {
        Matrix deltaBiasUpdate;
        for (int i = 0; i < biasMatrices.length; i++) {
            deltaBiasUpdate = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(eta,
                    MatrixOperations.transpose(deltaZMatrices[i])));
            deltaBiasUpdate = MatrixOperations.addMatrices(deltaBiasUpdate, MatrixOperations.
                    scalarMultiply(momentumParameter, lastBiasUpdates[i]));
            lastBiasUpdates[i] = deltaBiasUpdate;
            biasMatrices[i] = MatrixOperations.addMatrices(biasMatrices[i], deltaBiasUpdate);
        }
    }

    public Matrix deltaWeight(Matrix deltaMatrix, Matrix zMatrix) {
        Matrix deltaWeightMatrix = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(eta,
                MatrixOperations.transpose(MatrixOperations.multiplyMatrixes(deltaMatrix, zMatrix))));
        return deltaWeightMatrix;
    }
    
    public void setInputMatrix(double[] inputMatrix){
        input = new Matrix(inputMatrix);
    }
    
    public void setOutputMatrix(double[] outputMatrix){
        targetOutput = new Matrix(outputMatrix);
    }

    public Matrix getTargetOutputMatrix() {
        return targetOutput;
    }
    
    public void setTargetOutputMatrix(double[] targetOutputMatrix){
        targetOutput = new Matrix(targetOutputMatrix);
    }

    // calculates the error: E = (1 / 2) * (output - targetOutput)^2
    public Matrix getError() {
        Matrix errorMatrix = MatrixOperations.scalarMultiply(0.5, MatrixOperations.multiplyMatrixes(MatrixOperations.subtractMatrices(output, targetOutput), 
                MatrixOperations.subtractMatrices(output, targetOutput)));
        return errorMatrix;
    }
}
