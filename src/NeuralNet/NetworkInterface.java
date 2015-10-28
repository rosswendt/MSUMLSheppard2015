package NeuralNet;

import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.TrainingMethod.TrainingMethodInterface;

/**
 *
 * @author Ross Wendt
 */
public interface NetworkInterface {
    public TrainingMethodInterface getTrainingMethodInterface();
    public void initializeWeights(int[] hiddenLayers);
    public void forwardPropagation();
    public Matrix deltaWeight(Matrix deltaMatrix, Matrix zMatrix);
    public Matrix getInputMatrix();  
    public void setInputMatrix(double[] inputMatrix);
    public Matrix getOutputMatrix();
    public Matrix getTargetOutputMatrix();
    public void setTargetOutputMatrix(double[] targetOutputMatrix);
    public Matrix getError();
    public void setEpochLimit(int in);
    public int getEpochLimit();
}
