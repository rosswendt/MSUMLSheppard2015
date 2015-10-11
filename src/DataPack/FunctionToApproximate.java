package DataPack;

/**
 *
 * @author Ross Wendt
 */
public class Functions {
    int outputYVal;
    
    public static double tanh(double x) {      //hyperbolic tangent
    //double [] y;
    //y = new double[x.length];
    double y;   
    //for(int i=0; i<x.length; i++){
    double a = Math.exp( x );
    double b = Math.exp( 0 - (x));
    y=((a-b)/(a+b));

    
    return y;
    }
    
    public static double tanhDerivative(double x){              //takes an array and applies the derivative of hyperbolic tangent to it. that deriv is 1-tanh^2
        //double[] y = new double[x.length];
        double tan = 0;
        double y;
        //for(int i=0; i<x.length; i++){
        double a = Math.exp( x );
        double b = Math.exp( 0 - (x));
        tan =((a-b)/(a+b));
        y = 1-(tan * tan);
        
    
        return y;
    }
    
    public double computeRosenBrockOutVal(double[] x) {
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2      <--wiki
        //f(x,y) = (a-z)^2 + b(y-x^2)^2                     <--wiki
        for(int i=0; i<x.length; i++){
        outputYVal+= ((1-x[i])*(1-x[i])) + 100*((x[i+1]-(x[i]*x[i]))*(x[i+1]-(x[i]*x[i]))); 
    }
        
        return outputYVal;
    }
    
    public static double gaussian(int r, int c, double x){  //r is radius and c is center. Those need to be determined at methoud call
        //double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double y;
        double d = (x - c) * (x - c);             //get the joke?
        y = Math.exp(0-((d)/(r * r)));               //impluments gausian radial function
        
        return y;
    }
    
    public static double gaussianDerivative(int r, int c, double x){  //r is radius and c is center. Those need to be determined at methoud call
        //double[] y = new double[x.length];              //set of points to put through
        //for(int i=0; i<x.length; i++){
        double d = (x - c) * (x - c);             //get the joke?
        double temp= Math.exp(0-((d)/(r * r)));
        double y = ((2*c*temp)/(r*r)) - ((2*x*temp)/(r*r));               //impluments derivative of gausian radial function
        
        return y;
    }
}
