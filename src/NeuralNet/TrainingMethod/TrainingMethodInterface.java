package NeuralNet.TrainingMethod;

import NeuralNet.NetworkInterface;
import NeuralNet.MatrixNeuralNet;

/**
 *
 * @author Ross Wendt
 */
public interface TrainingMethodInterface {
    
    void applyMethod(MatrixNeuralNet N);
    void updateWeights(MatrixNeuralNet N);
    void updateBiases(MatrixNeuralNet N);
    
}
