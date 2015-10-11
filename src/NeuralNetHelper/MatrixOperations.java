package NeuralNetHelper;

/**
 *
 * @author Angus Tomlinson
 */
public class Matrix {
    private final int rows;
    private final int columns;
    private final double[][] matrixValues;
    
    /**
     *
     * @param initiapackage mlproject2;

/**lMatrixValues
     */
    public Matrix(double[][] initialMatrixValues){
        rows = initialMatrixValues.length;
        columns = initialMatrixValues[0].length;
        matrixValues = new double[rows][columns];
        for(int i = 0; i < matrixValues.length; i++){
            System.arraycopy(initialMatrixValues[i], 0, matrixValues[i], 0, matrixValues[0].length);
        }
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getColumns(){
        return columns;
    }
    
    public double[][] getMatrixValues(){
        return matrixValues;
    }
}
