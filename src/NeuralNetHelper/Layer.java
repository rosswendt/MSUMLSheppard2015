package NeuralNetHelper;

import NeuralNet.*;


/**
 *
 * @author Ross Wendt
 */
public class Layer {
    int size;
    boolean isInput;
    boolean isOutput;
    ActivationFunction actFunc;
    
    public Layer(int startSize, ActivationFunction startActFunc) {
        size = startSize;
        actFunc = startActFunc;
    }
    
    public boolean isInput() {
        if (isInput == false) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isOutput() {
        if (isOutput == false) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setIsInput(boolean in) {
        isInput = in;
    }
    
        
    public void setIsOutput(boolean in) {
        isOutput = in;
    }
}
