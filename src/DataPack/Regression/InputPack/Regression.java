package DataPack.Regression.InputPack;

import DataPack.Regression.InputPack.GenerateInputValsInterface;
import java.util.Random;

/**
 *
 * @author Ross Wendt
 */
public class Regression implements GenerateInputValsInterface{
    
    int samples;
    int n;
    double lowerBound;
    double upperBound;

    public Regression(int inSamples, int inN, double inLowerbound, double inUpperbound) {
        samples = inSamples;
        n = inN;
        lowerBound = inLowerbound;
        upperBound = inUpperbound;
        
    }
    
    @Override
    public double[][] initializeXDataSet() {
        Random rdm = new Random();
        double[][] xDataSet = new double[samples][n];
        for (int i = 0; i < samples; i++) {
            for (int j = 0; j < n; j++) {
                xDataSet[i][j] = (rdm.nextDouble() * (upperBound - lowerBound)) + lowerBound;
            }
        }
        return xDataSet;
    }    

}
