package NeuralNetHelper;


import java.util.Random;

/**
 *
 * @author Angus Tomlinson
 */
public class MatrixOperations {
    private static final int r = 1;
    private static final int c = 2;
    
    public static Matrix initializeZeroMatrix(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = 0;
            }
        }
        return a;
    }
    
    public static Matrix initializeOneMatrix(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = 1;
            }
        }
        return a;
    }
    
    public static Matrix initializeBiasMatrix(double theta, Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = theta;
            }
        }
        return a;
    }
    
    public static Matrix randomlyInitializeMatrix(Matrix a, double upperBound){
        Random randomGenerator = new Random();
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = (randomGenerator.nextDouble() * (2 * upperBound)) - upperBound;
            }
        }
        return a;
    }
    
    public static Matrix addMatrices(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < resultMatrix.getRows(); i++){
            for(int j = 0; j < resultMatrix.getColumns(); j++){
                resultMatrix.getMatrixValues()[i][j] = a.getMatrixValues()[i][j] + b.getMatrixValues()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix subtractMatrices(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < resultMatrix.getRows(); i++){
            for(int j = 0; j < resultMatrix.getColumns(); j++){
                resultMatrix.getMatrixValues()[i][j] = a.getMatrixValues()[i][j] - b.getMatrixValues()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix scalarMultiply(double scalar, Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = a.getMatrixValues()[i][j] * scalar;
            }
        }
        return a;
    }
    
    public static Matrix transpose(Matrix a){
        double[][] zeroMatrix = new double[a.getColumns()][a.getRows()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                resultMatrix.getMatrixValues()[j][i] = a.getMatrixValues()[i][j];
            }
        }
        return resultMatrix;
    }
    
    public static Matrix tanh(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.tanh(a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix derivativeTanh(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.tanhDerivative(a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix sigmoid(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.sigmoid(a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix derivativeSigmoid(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.sigmoidDerivative(a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix gaussian(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.gaussian(r, c, a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix derivativeGaussian(Matrix a){
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                a.getMatrixValues()[i][j] = Functions.gaussianDerivative(r, c, a.getMatrixValues()[i][j]);
            }
        }
        return a;
    }
    
    public static Matrix multiplyMatrixes(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int j = 0; j < b.getColumns(); j++){
            for(int k = 0; k < a.getRows(); k++){
                double result = 0;
                for(int i = 0; i < b.getRows(); i++){
                    result += (a.getMatrixValues()[k][i] * b.getMatrixValues()[i][j]);
                }
                resultMatrix.getMatrixValues()[k][j] = result;
            }
        }
        return resultMatrix;
    }
    
    public static Matrix hadamardProduct(Matrix a, Matrix b){
        double[][] zeroMatrix = new double[a.getRows()][b.getColumns()];
        Matrix resultMatrix = new Matrix(zeroMatrix);
        for(int i = 0; i < a.getRows(); i++){
            for(int j = 0; j < a.getColumns(); j++){
                resultMatrix.getMatrixValues()[i][j] = a.getMatrixValues()[i][j] * b.getMatrixValues()[i][j];
            }
        }
        return resultMatrix;
    }
}
