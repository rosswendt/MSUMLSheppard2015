package NeuralNet.TrainingMethod;

import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.NetworkInterface;
import NeuralNet.NeuralNet;

/**
 *
 * @author Ross Wendt
 */
public class BackPropagation implements TrainingMethodInterface {

    @Override
    public void applyMethod(NeuralNet neuralNet) {
        neuralNet.deltaZMatrices[neuralNet.deltaZMatrices.length - 1] = MatrixOperations.transpose(MatrixOperations.subtractMatrices(neuralNet.output, neuralNet.targetOutput));
        for (int i = neuralNet.deltaZMatrices.length - 2; i > -1; i--) {
            neuralNet.deltaZMatrices[i] = MatrixOperations.hadamardProduct(neuralNet.FMatrices[i], MatrixOperations.multiplyMatrixes(neuralNet.weightMatrices[i + 1], neuralNet.deltaZMatrices[i + 1]));
        }
        updateWeights(neuralNet);
        updateBiases(neuralNet);
    }

    @Override
    public void updateWeights(NeuralNet neuralNet) {
        Matrix deltaWeightUpdate = MatrixOperations.addMatrices(neuralNet.deltaWeight(neuralNet.deltaZMatrices[0], neuralNet.input), neuralNet.lastWeightUpdates[0]);
        neuralNet.lastWeightUpdates[0] = deltaWeightUpdate;
        neuralNet.weightMatrices[0] = MatrixOperations.addMatrices(neuralNet.weightMatrices[0], deltaWeightUpdate);
        for (int i = 1; i < neuralNet.weightMatrices.length; i++) {
            deltaWeightUpdate = MatrixOperations.addMatrices(neuralNet.deltaWeight(neuralNet.deltaZMatrices[i], neuralNet.zMatrices[i - 1]), neuralNet.lastWeightUpdates[i]);
            neuralNet.lastWeightUpdates[i] = deltaWeightUpdate;
            neuralNet.weightMatrices[i] = MatrixOperations.addMatrices(neuralNet.weightMatrices[i], deltaWeightUpdate);
        }
    }

    @Override
    public void updateBiases(NeuralNet neuralNet) {
        Matrix deltaBiasUpdate;
        for (int i = 0; i < neuralNet.biasMatrices.length; i++) {
            deltaBiasUpdate = MatrixOperations.scalarMultiply(-1, MatrixOperations.scalarMultiply(neuralNet.eta,
                    MatrixOperations.transpose(neuralNet.deltaZMatrices[i])));
            deltaBiasUpdate = MatrixOperations.addMatrices(deltaBiasUpdate, MatrixOperations.
                    scalarMultiply(neuralNet.momentumParameter, neuralNet.lastBiasUpdates[i]));
            neuralNet.lastBiasUpdates[i] = deltaBiasUpdate;
            neuralNet.biasMatrices[i] = MatrixOperations.addMatrices(neuralNet.biasMatrices[i], deltaBiasUpdate);
        }
    }
    
}
