package NeuralNetHelper;

/**
 *
 * @author Lukas Keller
 */
public class Functions {

    private static int outputYVal;

    public static double sigmoid(double x){
        double y;
        y = 1 / (1 + Math.exp(-x));
        return y;
    }
    
    public static double sigmoidDerivative(double x){
        double y;
        y = sigmoid(x) * (1 - sigmoid(x));
        return y;
    }
    
    public static double tanh(double x) {      //hyperbolic tangent
        double y;
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        y = ((a - b) / (a + b));

        return y;
    }

    public static double tanhDerivative(double x) {     //takes an array and applies the derivative of hyperbolic tangent to it. that deriv is 1-tanh^2
        double tan;
        double y;
        //for(int
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        tan = ((a - b) / (a + b));
        y = (1 - (tan * tan));
//        y = (2 / (a + b)) * (2 / (a + b));

        return y;
    }

    public static double computeRosenBrockOutVal(double[] x) {
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2      <--wiki
        //f(x,y) = (a-z)^2 + b(y-x^2)^2                     <--wiki
        for (int i = 0; i < x.length - 1; i++) {
            outputYVal += ((1 - x[i]) * (1 - x[i])) + 100 * ((x[i + 1] - (x[i] * x[i])) * (x[i + 1] - (x[i] * x[i])));
        }

        return outputYVal;
    }

    public static double gaussian(int r, int c, double x) {  //r is radius and c is center. Those need to be determined at methoud call
        //double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double y;
        double d = (x - c) * (x - c);             //get the joke?
        y = Math.exp(0 - ((d) / (r * r)));               //impluments gausian radial function

        return y;
    }

    public static double gaussianDerivative(int r, int c, double x) {  //r is radius and c is center. Those need to be determined at methoud call
        //double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double d = (x - c) * (x - c);             //get the joke?
        double temp = Math.exp(0 - ((d) / (r * r)));
        double y = ((2 * c * temp) / (r * r)) - ((2 * x * temp) / (r * r));               //impluments derivative of gausian radial function

        return y;
    }
}
