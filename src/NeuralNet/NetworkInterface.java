package NeuralNet;

import Math.Matrix;
import java.util.ArrayList;

public abstract class NetworkInterface {
    public int epochLimit;
    
    public abstract void initializeWeights(int[] hiddenLayers);
    public abstract void forwardPropagation(Matrix Example);
    
    public void setEpochLimit(int in) {
        epochLimit = in;
    
    }
        
    public int getEpochLimit() {
        return epochLimit;
    }
}
