package Math;

/**
 *
 * @author Ross Wendt
 */
public class Matrix {
    public Matrix( ) {
    }
    
    private Matrix mmul(Matrix ... matrices) {
        Matrix M = new Matrix();
        return M;
    }
    
    public Matrix feedforward(Matrix one, Matrix two) {
        return mmul(one, two);       
    }
}
