package DataPack;

/**
 *
 * @author Ross Wendt
 */
public class FunctionToApproximate {
    int outputYVal;
    
    public FunctionToApproximate() {
        
    }
    
    public double computeRosenBrockOutVal(double[] x) {
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2      <--wiki
        //f(x,y) = (a-z)^2 + b(y-x^2)^2                     <--wiki
        for(int i=0; i<x.length; i++){
        outputYVal+= ((1-x[i])*(1-x[i])) + 100*((x[i+1]-(x[i]*x[i]))*(x[i+1]-(x[i]*x[i]))); 
    }
        
        return outputYVal;
    }
    
}
