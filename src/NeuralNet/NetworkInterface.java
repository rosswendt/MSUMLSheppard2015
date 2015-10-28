package NeuralNet;

public abstract class NetworkInterface {
    public int epochLimit;
    
    public abstract void initializeWeights(int[] hiddenLayers);
    public abstract void forwardPropagation();
    
    public void setEpochLimit(int in) {
        epochLimit = in;
    
    }
        
    public int getEpochLimit() {
        return epochLimit;
    }
}
