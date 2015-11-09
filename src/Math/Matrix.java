package Math;

/**
 *
 * @author Angus Tomlinson
 */
public class Matrix {
    private final int rows;
    private final int columns;
    private double[][] matrixValues;
    
    /**
     *
     * @param initialMatrixValues
     */
    public Matrix(double[][] initialMatrixValues){
        rows = initialMatrixValues.length;
        columns = initialMatrixValues[0].length;
        matrixValues = new double[rows][columns];
        for(int i = 0; i < matrixValues.length; i++){
            System.arraycopy(initialMatrixValues[i], 0, matrixValues[i], 0, matrixValues[0].length);
        }
    }
    
    public Matrix(double[] initialMatrixValues){
        rows = 1;
        columns = initialMatrixValues.length;
        matrixValues = new double[rows][columns];
        System.arraycopy(initialMatrixValues, 0, matrixValues[0], 0, matrixValues[0].length);
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getColumns(){
        return columns;
    }
    
    public double[][] getArray(){
        return matrixValues;
    }
    
    public double[][] getMatrixValues(){
        return matrixValues;
    }

    public void setMatrixValues(int index1, int index2, double value) {
        matrixValues[index1][index2] = value;
    }
}