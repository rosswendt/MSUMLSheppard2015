package Math.ActivationFunctions;

import Math.Matrix;

/**
 *
 * @author Ross Wendt
 */
public class HyperbolicTangent extends AbstractFunction {

    @Override
    public double function(double x) {
        double y;
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        y = ((a - b) / (a + b));

        return y;   
    }

    @Override
    public double functionDerivative(double x) {
        double tan;
        double y;
        //for(int
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        tan = ((a - b) / (a + b));
        y = (1 - (tan * tan));
//        y = (2 / (a + b)) * (2 / (a + b));

        return y;    }
    
}
