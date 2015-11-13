package NeuralNet;

import Math.ActivationFunctions.AbstractFunction;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.TrainingMethod.TrainingMethodInterface;

/**
 *
 * @author Ross Wendt
 */
/**
 *
 * @author Angus Tomlinson
 */
public final class MatrixNeuralNet extends NetworkInterface {

    private final double upperBoundInitializationWeight;
    private final double upperBoundInitializationBias;
    public final double eta;
    public final double momentumParameter;
    
    public final double initialSigma;
    public final int mu;
    public final int lambda;

    private final boolean isHiddenLayerCountZero;
    public Matrix input;
    public Matrix output;
    public Matrix targetOutput;
    public Matrix[] weightMatrices;
    public Matrix[] biasMatrices;
    private Matrix[] sMatrices;
    public Matrix[] zMatrices;
    public Matrix[] FMatrices;
    public Matrix[] deltaZMatrices;
    public Matrix[] lastWeightUpdates;
    public Matrix[] lastBiasUpdates;
    public int hiddenLayersLength;

    private final AbstractFunction functionInterface;
    private final TrainingMethodInterface trainingMethodInterface;

    // initialize the RBF
    public MatrixNeuralNet(double[] input, double[] output, int[] hiddenLayers, double upperBoundInitializationWeight,
            double upperBoundInitializationBias, double eta, double momentumParameter, double initialSigma, int mu, 
            int lambda, int epochLimit, AbstractFunction activationFunctionInterface,
            TrainingMethodInterface trainingMethodInterface) {

        this.eta = eta;
        this.momentumParameter = momentumParameter;
        
        this.initialSigma = initialSigma;
        this.mu = mu;
        this.lambda = lambda;

        functionInterface = activationFunctionInterface;
        this.trainingMethodInterface = trainingMethodInterface;
        this.epochLimit = epochLimit;

        this.input = new Matrix(input);
        this.output = new Matrix(new double[output.length]);
        this.targetOutput = new Matrix(new double[output.length]);

        isHiddenLayerCountZero = (hiddenLayers.length == 0);
        hiddenLayersLength = hiddenLayers.length;

        this.upperBoundInitializationWeight = upperBoundInitializationWeight;
        this.upperBoundInitializationBias = upperBoundInitializationBias;

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
    @Override
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
    // Z(i) = f(S(i)) where f(x) is the gaussian function
    // S(i) = W(i) * Z(i - 1)
    // F(i) = (f'(S(i)))^T
    @Override
    public void forwardPropagation() {
//        sMatrices = new Matrix[hiddenLayersLength];
//        zMatrices = new Matrix[hiddenLayersLength];
//        FMatrices = new Matrix[hiddenLayersLength];
//
//        deltaZMatrices = new Matrix[hiddenLayersLength + 1];
        if (isHiddenLayerCountZero) {
            output = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(input, weightMatrices[0]), biasMatrices[0]);
        } else {
            sMatrices[0] = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(input, weightMatrices[0]), biasMatrices[0]);
            zMatrices[0] = functionInterface.apply(sMatrices[0]);
            FMatrices[0] = MatrixOperations.transpose(functionInterface.applyDerivative(sMatrices[0]));
            for (int i = 1; i < sMatrices.length; i++) {
                sMatrices[i] = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(zMatrices[i - 1], weightMatrices[i]), biasMatrices[i]);
                zMatrices[i] = functionInterface.apply(sMatrices[i]);
                FMatrices[i] = MatrixOperations.transpose(functionInterface.applyDerivative(sMatrices[i]));
            }
            output = MatrixOperations.addMatrices(MatrixOperations.multiplyMatrixes(zMatrices[zMatrices.length - 1],
                    weightMatrices[weightMatrices.length - 1]), biasMatrices[biasMatrices.length - 1]);
        }
    }

    public Matrix deltaWeight(Matrix deltaMatrix, Matrix zMatrix) {
        Matrix deltaWeightMatrix = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(eta,
                MatrixOperations.transpose(MatrixOperations.multiplyMatrixes(deltaMatrix, zMatrix))));
        return deltaWeightMatrix;
    }

    public Matrix getInputMatrix() {
        return input;
    }

    public void setInputMatrix(double[] inputMatrix) {
        input = new Matrix(inputMatrix);
    }

    public Matrix getOutputMatrix() {
        return output;
    }

    public void setOutputMatrix(double[] outputMatrix) {
        targetOutput = new Matrix(outputMatrix);
    }

    public Matrix getTargetOutputMatrix() {
        return targetOutput;
    }

    public void setTargetOutputMatrix(double[] targetOutputMatrix) {
        targetOutput = new Matrix(targetOutputMatrix);
    }

    public double getInitialSigma(){
        return initialSigma;
    }
    
    public int getMu(){
        return mu;
    }
    
    public int getLambda(){
        return lambda;
    }
    
    public double getUpperBound(){
        return upperBoundInitializationWeight;
    }
    
    // calculates the error: E = (1 / 2) * (output - targetOutput)^2
    public Matrix getError() {

        Matrix errorPartOne = MatrixOperations.subtractMatrices(targetOutput, output);

        Matrix squareError = MatrixOperations.multiplyMatrixes(errorPartOne, errorPartOne);

        Matrix errorMatrix = MatrixOperations.scalarMultiply(0.5, squareError);

        return errorMatrix;
    }

    public TrainingMethodInterface getTrainingMethodInterface() {
        return trainingMethodInterface;
    }
}
