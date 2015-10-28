package DataPack.Regression.OutputPack;

/**
 *
 * @author Ross Wendt
 */
public abstract class AbstractGenerateOutputVals {
    
    abstract double computeFunction(double[] x);
    
    public double[][] initializeYDataSet(double[][] xDataSet) {
        double[][] yDataSet = new double[xDataSet.length][1];
        for (int i = 0; i < yDataSet.length; i++) {
            yDataSet[i][0] = computeFunction(xDataSet[i]);
        }
        return yDataSet;
    }
}
