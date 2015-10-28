package DataPack.Regression.OutputPack;

/**
 *
 * @author Ross Wendt
 */
public class Rosenbrock extends AbstractGenerateOutputVals {
    
    /*****CONTAINS ROSENBROCK STUFF*****/    
    
 
    
    @Override
    public double computeFunction(double[] x) {
    double y = 0;
        for (int i = 0; i < x.length - 1; i++) {
            y += 100 * Math.pow(x[i+1] - Math.pow(x[i],2),2) + Math.pow(1 - x[i], 2);
        }
        return y;
    }
    


}
