package NeuralNetHelper;

/**
 *
 * @author Lukas Keller
 */
public class Functions {

    public static double computeRosenBrockOutVal(double[] x) { // rosenbrock function
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2
        int y = 0;
        for (int i = 0; i < x.length - 1; i++) {
            y += ((1 - x[i]) * (1 - x[i])) + 100 * ((x[i + 1] - (x[i] * x[i])) * (x[i + 1] - (x[i] * x[i])));
        }

        return y;
    }

    public static double sigmoid(double x){ //sigmoid function
        double y;
        y = 1 / (1 + Math.exp(-x));
        return y;
    }
    
    public static double sigmoidDerivative(double x){ //derivative of sigmoid function
        double y;
        y = sigmoid(x) * (1 - sigmoid(x));
        return y;
    }
    
    public static double tanh(double x) {   //hyperbolic tangent
        double y;
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        y = ((a - b) / (a + b));

        return y;
    }

    public static double tanhDerivative(double x) { //derivative of hyperbolic tangent: 1-tanh^2
        double tan;
        double y;
        double a = Math.exp(x);
        double b = Math.exp(0 - (x));
        tan = ((a - b) / (a + b));
        y = (1 - (tan * tan));

        return y;
    }

    public static double gaussian(int r, int c, double x) { // gaussian function
        double y;
        double d = (x - c) * (x - c);
        y = Math.exp(0 - ((d) / (r * r)));

        return y;
    }

    public static double gaussianDerivative(int r, int c, double x) { //derivative of gaussian function
        double d = (x - c) * (x - c);
        double temp = Math.exp(0 - ((d) / (r * r)));
        double y = ((2 * c * temp) / (r * r)) - ((2 * x * temp) / (r * r));

        return y;
    }
}
