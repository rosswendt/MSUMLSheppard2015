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
    int epochStep = 20;
    double[] runningError = new double[epochStep];
    public static int epoch = 0;
    
    
    boolean runWithOutput = true;
    MatrixNeuralNet neuralNet = Driver.getNeuralNet();
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
        int foldSize = Driver.dataSetSize/numFolds;
        double[] d;
        ArrayList<Matrix> A = new ArrayList<>();
        
        for (int i = 0; i < numFolds; i++ ) {
            double[][] dub = new double[foldSize][Driver.dimension];
            
            /*
            Initialize values
            */
            // src srcPos dest destPos length
            for (int j = 0; j < foldSize; j++ ) {
                System.arraycopy(Driver.xDataSet, i*(foldSize)+j, dub, j, 1);
            }
            
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
        double error = 0;
        ArrayList<Matrix> train = partitionData();    
        
        for (epoch = 0; epoch < Driver.epochLimit; epoch++) {
            if (epoch % epochStep == 0 ) {
                System.out.println("Epoch " + epoch + ":");
            }

            //ArrayList<Matrix> test = new ArrayList<>();

            for (int k = 0; k < Driver.k; k++ ) {
                Matrix test = train.get(k);
                
                train.remove(test);
                
                for (int p = 0; p < train.size(); p++ ) {
                    for (int z = 0; z < train.get(p).getMatrixValues().length; z++ ) {
                        double targetOutVal = neuralNet.targetOutput.getMatrixValues()[0][z];
                        neuralNet.output.setMatrixValues(0, 0, targetOutVal);
                        double[] helper = train.get(p).getMatrixValues()[z];
                        Matrix M = new Matrix(helper);
                        neuralNet.forwardPropagation(M);
                        T.applyMethod(neuralNet.output.getArray()[0][0]); 

                    }
                
                        //train.add(test);
                        //test.remove(test);
                }
                assignError(neuralNet.getError());
                error = neuralNet.getError();
                
                
                //neuralNet.forwardPropagation(train);
                //T.applyMethod(); //runs training algorithm
                //meanError.add(getError(test, k, epoch));
                
                train.add(test);
            }        
            double tempVal = sumError();   
            if (epoch % epochStep == 0)
            //System.out.println(tempVal);
                System.out.println(error);
        }
    }

    private double getError(Matrix test, int k, int epoch) {
        ArrayList<Matrix> holder = new ArrayList<>();
        holder.add(test);
        double sum = 0;
        for (int i = 0; i < holder.size(); i++) {
            neuralNet.forwardPropagation(holder.get(i));
            sum += neuralNet.getError();
        }
        
        
        //System.out.println("Fold #" + k + "Epoch #" + epoch + " RMSE: " + neuralNet.getError());
        
        
        
        holder.remove(test);
        //System.out.println(sum + " " + epoch);
        return sum;
    }
    
    private double sumError() {
        double sum = 0;
        for (int i = 0; i < epochStep; i++ ) {
            sum += runningError[i];
        }              
        return sum;
    }

    private void assignError(double in) {
        for (int i = 0; i < epochStep; i++) {
            runningError[i] = in;
        }
    }
}
