package DataPack.Regression.OutputPack;
/**
 *
 * @author Ross Wendt
 */
public class TestFunction extends AbstractGenerateOutputVals {
    
    /***** CONTAINS SIMPLE SUM FUNCTION TO TEST NEURAL NET*****/

    double outVal = 0;
    
    @Override
    public double computeFunction(double[] x) {

        for (int i = 0; i < x.length; i++) {
            outVal += x[i];
        }
        return outVal;
    }

}
