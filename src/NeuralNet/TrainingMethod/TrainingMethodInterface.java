package NeuralNet.TrainingMethod;

import NeuralNet.NetworkInterface;
import NeuralNet.NeuralNet;

/**
 *
 * @author Ross Wendt
 */
public interface TrainingMethodInterface {
    
    void applyMethod(NeuralNet N);
    void updateWeights(NeuralNet N);
    void updateBiases(NeuralNet N);
    
}
