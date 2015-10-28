package Math.ActivationFunctions;

/**
 *
 * @author Ross Wendt
 */
public class Sigmoid extends AbstractFunction {


    @Override
    public double function(double x) {
                double y;
        y = 1 / (1 + Math.exp(-x));
        return y;
    }

    @Override
    public double functionDerivative(double x) {
        double y;
        y = function(x) * (1 - function(x));
        return y;
    }
    
}
