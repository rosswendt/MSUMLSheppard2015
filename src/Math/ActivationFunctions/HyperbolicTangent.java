package Math.ActivationFunctions;

/**
 *
 * @author Ross Wendt
 */
public class HyperbolicTangent extends AbstractFunction {

    @Override
    public double function(double x) {
        double y = Math.tanh(x);

        return y;   
    }

    @Override
    public double functionDerivative(double x) {
       double y = 1 - Math.pow(Math.tanh(x), 2);

        return y;    
    }
    
}
