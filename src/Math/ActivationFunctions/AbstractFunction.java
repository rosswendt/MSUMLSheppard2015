
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
        Matrix b = new Matrix(new double[a.getRows()][a.getColumns()]);
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                b.getArray()[i][j] = functionDerivative(a.getArray()[i][j]);
            }
        }
        return b;
    }

    public Matrix apply(Matrix a) {
        Matrix b = new Matrix(new double[a.getRows()][a.getColumns()]);
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                b.getArray()[i][j] = function(a.getArray()[i][j]);
            }
        }
        return b;
    }
    
}
