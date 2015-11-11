package NeuralNet;

import Driver.Driver;
import Math.Matrix;
import Math.MatrixOperations;
import NeuralNet.TrainingMethod.TrainingMethodInterface;
import java.util.ArrayList;

/**
 *
 * @author Ross Wendt
 */
public class NeuralNetDriver {
    
    double testSum = 0;
    double trainSum = 0;
    public static int epoch = 0;
    
    boolean runWithOutput = true;
    MatrixNeuralNet neuralNet;
    //Driver D = new Driver();

    public NeuralNetDriver(MatrixNeuralNet inNeuralNet) {
        neuralNet = inNeuralNet;
    }
    
    public void runTest(boolean doOutput) {
        if ( doOutput == true ) {
            runWithOutput();
        } else {
            //NOT IMPLEMENTED runTestWithoutOutput();
        }
    }
    
        public void runWithOutput() {
            //data is random by default so can be considered pre-shuffled
            
        for (int epoch = 0; epoch < neuralNet.getEpochLimit(); epoch++) {
            System.out.println("Epoch" + epoch + ":");
                runNeuralNet();
        }
    }

    private ArrayList<Matrix> partitionData() {
        int numFolds = Driver.k;
        double[] d;
        ArrayList<Matrix> A = new ArrayList<>();
        
        for (int i = 0; i < numFolds; i++ ) {
            double[][] dub = new double[Driver.dataSetSize][Driver.dimension];
            
            /*
            Initialize values
            */
            // src srcPos dest destPos length
            System.arraycopy(Driver.xDataSet, i*Driver.k, dub, i*Driver.k, Driver.xDataSet.length/numFolds);
            
            //for (int j = 0; j < Driver.xDataSet.length; j++ ) {
            //   for (int k = 0; k < (Driver.xDataSet[0].length)/ numFolds; k++ ) {
            //        dub[j][k] = Driver.xDataSet[j][k];
            //    }
            //}
            
            Matrix M = new Matrix(dub);
            A.add(M);
        }
        
        
        return A;
    }

    public void runNeuralNet() {
        TrainingMethodInterface T = neuralNet.getTrainingMethodInterface();
        for (epoch = 0; epoch < Driver.epochLimit; epoch++) {
            if (epoch % 20 == 0 ) {
                System.out.println("Epoch " + epoch + ":");
            }
            ArrayList<Matrix> train = partitionData();    
            //ArrayList<Matrix> test = new ArrayList<>();
            ArrayList<Double> meanError = new ArrayList<>();
            for (int k = 0; k < Driver.k; k++ ) {
                Matrix temp = train.get(k);
                
                train.remove(temp);
                //test.add(temp);
                
                for (int p = 0; p < train.size(); p++ ) {
                    for (int z = 0; z < train.get(p).getMatrixValues().length; z++ ) {
                        double targetOutVal = neuralNet.targetOutput.getMatrixValues()[0][z];
                        neuralNet.output.setMatrixValues(0, 0, targetOutVal);
                        neuralNet.forwardPropagation(new Matrix (train.get(p).getMatrixValues()[z]));
                        T.applyMethod(); 
                        meanError.add(getError(temp, k, epoch));
                    }
                
                        //train.add(temp);
                        //test.remove(temp);
                }
                
                
                //neuralNet.forwardPropagation(train);
                //T.applyMethod(); //runs training algorithm
                //meanError.add(getError(temp, k, epoch));
                
                train.add(temp);
                //test.remove(temp);
            }
            double meanSummedError = 0;
            for (int i = 0; i < meanError.size(); i++ ) {
                meanSummedError += meanError.get(i);
            }
            if (epoch % 20 == 0) {
                meanSummedError = meanSummedError/Driver.k;
                System.out.println("Mean error: " + meanSummedError);
            }
            
            meanError.clear();
        }
    }

    private double getError(Matrix test, int k, int epoch) {
        //ArrayList<Matrix> holder = new ArrayList<>();
        //holder.add(test);
        //neuralNet.forwardPropagation(holder);
        
        
        //System.out.println("Fold #" + k + "RMSE: " + neuralNet.getError());
        
        
        
        //holder.remove(test);
        
        return neuralNet.getError();
    }
}
