package Math.ActivationFunctions;

import Math.ActivationFunctions.AbstractFunction;
import Math.Matrix;
import Math.MatrixOperations;

/**
 *
 * @author Ross Wendt
 */


public class Gaussian extends AbstractFunction {
    public double r = 9;
    public double c = -1;

    public Gaussian(double inR, double inC) {
        r = inR;
        c = inC;
    }

    @Override
    public double function(double x) {
        //double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double y;
        double d = (x - c) * (x - c);             //get the joke?
        y = Math.exp(0 - ((d) / (r * r)));               //impluments gausian radial function

        return y;    }

    @Override
    public double functionDerivative(double x) {
//double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double d = (x - c) * (x - c);             //get the joke?
        double temp = Math.exp(0 - ((d) / (r * r)));
        double y = ((2 * c * temp) / (r * r)) - ((2 * x * temp) / (r * r));               //impluments derivative of gausian radial function

        return y;    
    }    
}
