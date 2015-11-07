package NeuralNet.TrainingMethod;

import NeuralNet.NetworkInterface;
import NeuralNet.MatrixNeuralNet;
import Driver.*;

/**
 *
 * @author Ross Wendt
 */
public interface TrainingMethodInterface {
    MatrixNeuralNet neuralNet = Driver.getNeuralNet();
    void applyMethod();
    //void updateWeights(MatrixNeuralNet neuralNet);
    //void updateBiases(MatrixNeuralNet neuralNet);
    
}
