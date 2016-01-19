package NeuralNet.TrainingMethod;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.MatrixNeuralNet;

/**
 *
 * @author Ross Wendt
 */
public abstract class TrainingMethodInterface {
    
    public abstract void applyMethod(double predictedOutput);
    
    public void updateWeights(MatrixNeuralNet neuralNet) {
        Matrix deltaWeightUpdate = MatrixOperations.addMatrices(neuralNet.deltaWeight(neuralNet.deltaZMatrices[0], neuralNet.input), neuralNet.lastWeightUpdates[0]);
        neuralNet.lastWeightUpdates[0] = deltaWeightUpdate;
        neuralNet.weightMatrices[0] = MatrixOperations.addMatrices(neuralNet.weightMatrices[0], deltaWeightUpdate);
        for (int i = 1; i < neuralNet.weightMatrices.length; i++) {
            deltaWeightUpdate = MatrixOperations.addMatrices(neuralNet.deltaWeight(neuralNet.deltaZMatrices[i], neuralNet.zMatrices[i - 1]), neuralNet.lastWeightUpdates[i]);
            neuralNet.lastWeightUpdates[i] = deltaWeightUpdate;
            neuralNet.weightMatrices[i] = MatrixOperations.addMatrices(neuralNet.weightMatrices[i], deltaWeightUpdate);
        }
    }

    public void updateBiases(MatrixNeuralNet neuralNet) {
        Matrix deltaBiasUpdate;
        for (int i = 0; i < neuralNet.biasMatrices.length; i++) {
            deltaBiasUpdate = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(neuralNet.eta,
                    MatrixOperations.transpose(neuralNet.deltaZMatrices[i])));
            deltaBiasUpdate = MatrixOperations.addBiasMatrices(deltaBiasUpdate, MatrixOperations.
                    scalarMultiply(neuralNet.momentumParameter, neuralNet.lastBiasUpdates[i]));
            neuralNet.lastBiasUpdates[i] = deltaBiasUpdate;
            neuralNet.biasMatrices[i] = MatrixOperations.addBiasMatrices(deltaBiasUpdate, neuralNet.biasMatrices[i]);
        }
    }
    
}
