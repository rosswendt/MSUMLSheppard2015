package Math;
import java.util.Random;

/**
 *
 * @author Angus Tomlinson
 */
public class MatrixOperations {
    
    public static Matrix initializeZeroMatrix(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getArray()[i][j] = 0;
            }
        }
        return a;
    }
    
    public static Matrix initializeOneMatrix(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getArray()[i][j] = 1;
            }
        }
        return a;
    }
    
    public static Matrix initializeBiasMatrix(double theta, Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getArray()[i][j] = theta;
            }
        }
        return a;
    }
    
    public static Matrix randomlyInitializeMatrix(Matrix a, double upperBound){
        Random randomGenerator = new Random();
        //System.out.print(randomGenerator.nextDouble() + " ");
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                double lowerBound = 0;
                double randomValue = lowerBound + (upperBound - lowerBound) * randomGenerator.nextDouble();
                //a.getArray()[i][j] = (randomGenerator.nextDouble() * (2 * upperBound)) - upperBound;
                a.getArray()[i][j] = randomValue;
            }
        }
        return a;
    }
    
    public static Matrix addMatrices(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < resultMatrix.getRows(); i++){
            for(int j = 0; j < resultMatrix.getColumns(); j++){
                resultMatrix.getArray()[i][j] = a.getArray()[i][j] + b.getArray()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix subtractMatrices(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < resultMatrix.getRows(); i++){
            for(int j = 0; j < resultMatrix.getColumns(); j++){
                resultMatrix.getArray()[i][j] = a.getArray()[i][j] - b.getArray()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix scalarMultiply(double scalar, Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getArray()[i][j] = a.getArray()[i][j] * scalar;
            }
        }
        return a;
    }
    
    public static Matrix transpose(Matrix a){
        double[][] zeroMatrix = new double[a.getColumns()][a.getRows()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                resultMatrix.getArray()[j][i] = a.getArray()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix multiplyMatrixes(Matrix a, Matrix b){
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            //System.out.println("fuck");
        }
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for (int i = 0; i < resultMatrix.getRows(); i++ ) {
            for (int j = 0; j < resultMatrix.getColumns(); j++ ) {
                for (int k = 0; k < a.getColumns(); k++ ) {
                    resultMatrix.getArray()[i][j] += (a.getArray()[i][k] * b.getArray()[k][j] ) ;
                }
            }
        }
        return resultMatrix;
    }
    
    /*public static Matrix multiplyMatrixes(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int j = 0; j < b.getColumns(); j++){
            for(int k = 0; k < a.getRows(); k++){
                double result = 0;
                for(int i = 0; i < b.getRows(); i++){
                    result += (a.getArray()[k][i] * b.getArray()[i][j]);
                }
                resultMatrix.getArray()[k][j] = result;
            }
        }
        return resultMatrix;
    }*/
    
    public static Matrix hadamardProduct(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                resultMatrix.getArray()[i][j] = a.getArray()[i][j] * b.getArray()[i][j];
            }
        }
        return resultMatrix;
    }

    public static Matrix multiplyTrainMatrixes(Matrix train, Matrix b){
        MatrixOperations.transpose(train);
        double[][] zeroMatrix = new double[b.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for (int i = 0; i < resultMatrix.getRows(); i++ ) {
            for (int j = 0; j < resultMatrix.getColumns(); j++ ) {
                for (int k = 0; k < train.getColumns(); k++ ) {
                    resultMatrix.getArray()[i][j] += (train.getArray()[i][k] * b.getArray()[k][j] ) ;
                }
            }
        }
        return resultMatrix;
    }

    public static Matrix addBiasMatrices(Matrix a, Matrix bias){
        double[][] zeroMatrix = new double[a.getRows()][a.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        
        for (int i = 0; i < bias.getColumns(); i++ ) {
            for (int j = 0; j < a.getRows(); j++ ) {
                for (int k = 0; k < a.getColumns(); k++ ) {
                    resultMatrix.getArray()[j][k] = a.getArray()[j][k] + bias.getArray()[0][i];
                }
            }
        }
        
        //for(int i = 0; i < resultMatrix.getRows(); i++){
        //    for(int j = 0; j < resultMatrix.getColumns(); j++){
        //        resultMatrix.getArray()[i][j] = a.getArray()[i][j] + bias.getArray()[i][j];
        //    }
        //}
        return resultMatrix;
    }

    public static Matrix specialSubtractMatrices(Matrix a, Matrix bias) {
        double[][] zeroMatrix = new double[a.getRows()][a.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        
        for (int i = 0; i < bias.getColumns(); i++ ) {
            for (int j = 0; j < a.getRows(); j++ ) {
                for (int k = 0; k < a.getColumns(); k++ ) {
                    resultMatrix.getArray()[j][k] = a.getArray()[j][k] - bias.getArray()[0][i];
                }
            }
        }
        
        //for(int i = 0; i < resultMatrix.getRows(); i++){
        //    for(int j = 0; j < resultMatrix.getColumns(); j++){
        //        resultMatrix.getArray()[i][j] = a.getArray()[i][j] + bias.getArray()[i][j];
        //    }
        //}
        return resultMatrix;
    }
}