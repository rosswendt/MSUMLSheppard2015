package NeuralNet.TrainingMethod;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.NetworkInterface;
import NeuralNet.MatrixNeuralNet;

/**
 *
 * @author Ross Wendt
 */
public class BackPropagation extends TrainingMethodInterface {

    @Override
    public void applyMethod(double predictedOutput) {    
        MatrixNeuralNet neuralNet = Driver.getNeuralNet();
        //neuralNet.deltaZMatrices[0] = null;
        //neuralNet.deltaZMatrices[1] = MatrixOperations.initializeZeroMatrix(neuralNet.zMatrices[0]);
        //neuralNet.deltaZMatrices[2] = MatrixOperations.initializeZeroMatrix(neuralNet.zMatrices[0]);
        double[] d = {predictedOutput};
        Matrix targetOutput = new Matrix(d);
        
        
        neuralNet.deltaZMatrices[neuralNet.deltaZMatrices.length - 1] = MatrixOperations.transpose(MatrixOperations.subtractMatrices(neuralNet.output, targetOutput));
        for (int i = neuralNet.deltaZMatrices.length - 2; i > -1; i--) {
            neuralNet.deltaZMatrices[i] = MatrixOperations.hadamardProduct(neuralNet.FMatrices[i], MatrixOperations.multiplyMatrixes(neuralNet.weightMatrices[i + 1], neuralNet.deltaZMatrices[i + 1]));
        }
        
        updateWeights(neuralNet);
        updateBiases(neuralNet);
    }    
}
