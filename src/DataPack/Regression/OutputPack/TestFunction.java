package DataPack.Regression.OutputPack;
/**
 *
 * @author Ross Wendt
 */
public class TestFunction extends AbstractGenerateOutputVals {
    
    /***** CONTAINS SIMPLE SUM FUNCTION TO TEST NEURAL NET*****/

    @Override
    public double computeFunction(double[] x) {
        double outVal = 0;
        for (int i = 0; i < x.length; i++) {
            outVal += x[i];
        }
        return outVal;
    }

}
