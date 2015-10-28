package Math.ActivationFunctions;

import Math.Matrix;

/**
 *
 * @author Ross Wendt
 */
public abstract class AbstractFunction {
    
    abstract double function(double in);
    abstract double functionDerivative(double in);   
    
    public Matrix applyDerivative(Matrix a) {
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                a.getArray()[i][j] = functionDerivative(a.getArray()[i][j]);
            }
        }
        return a;
    }

    public Matrix apply(Matrix a) {
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                a.getArray()[i][j] = function(a.getArray()[i][j]);
            }
        }
        return a;
    }
    
}
