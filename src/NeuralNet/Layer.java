package NeuralNet;

import Math.*;
import NeuralNet.*;


/**
 *
 * @author Ross Wendt
 */
public class Layer {
    int size;
    boolean isInput;
    boolean isOutput;
    Function actFunc;
    
    public Layer(int startSize, boolean startIsInput, boolean startIsOutput, Function startActFunc) {
        size = startSize;
        isInput = startIsInput;
        isOutput = startIsOutput;
        actFunc = startActFunc;
    }
}
