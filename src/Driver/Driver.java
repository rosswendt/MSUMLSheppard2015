package Driver;
import DataPack.*;
import DataPack.Point;
import java.util.Random;
//import NeuralNet.*;

/**
 *
 * @author Ross Wendt, Lukas Keller
 */
public class Driver { 
    
    //static Point[] dataSet;                             //I don't think we need a Point class/object at all
    static double[] dataSetX;
    static double[] trainingSetX;                         //needs to be arbitrarily chosen
    static double[] trainingSetY;                         //these could be 2d arrays if we'd rather do that
    static double[] rosenbrock;
    static double[] dataSetY;
    //NeuralNet driverNet = new NeuralNet(0,0,0,0);
    static double outputYVal;
    static FunctionToApproximate func;                         //Used to call the activation function and incase we don't want the rosenbrock in the driver function
    
    public static void main(String Args[]) {
        int n = 5;                                      //n = number of points in the data set
        dataSetX = generateData(n);                      //generates n points in the data set
        //trainingSet = generateData(n);
        run(dataSetX);
        dataSetY = func.FunctionToApproximate(dataSetX);
        
        //printArray(dataSetX);
        //printArray(dataSetY);

    }
    
    
    public static double[] generateData(int x) {
       double[] temp;
        temp = new double[x];
        for(int i = 0; i<x; i++){
           Random ran = new Random();
           double random = ran.nextInt(5)+1;
           temp[i] = random;
        }
        
        return temp;
    }
    
    public static void run(double[] x) {

//        for(int i = 0; i<x.length; i++){
//           Random ran = new Random();
//           int randomNum = ran.nextInt(5)+1;
//           System.out.print(x[i] + ", ");      //Generating random values for the x variable from 1-5
//        }
           computeRosenBrockOutVal(dataSetX);
//           System.out.println(rosenbrock[0]);
    }
    
    //perhaps various methods for printing different things 
                    //for now I've shoved those in run   

    public static double computeRosenBrockOutVal(double[] x) {          //did this really need to be in Function Approximate?
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2      <--wiki
        //f(x,y) = (a-z)^2 + b(y-x^2)^2 <--wiki
        rosenbrock = new double[1];
        double temp = 0.0;
        for(int i=0; i<x.length -1; i++){
        double a = x[i];
        double b = x[i+1];
        double c = a*a;
     
        temp+= ((1-a)*(1-a)) + 100*((b-c)*(b-c)); 
        //System.out.println(temp);
        rosenbrock[0] = temp;
        
    }
        return temp;
}

    public static void printArray(double[] x){
        for(int i=0; i<x.length; i++){
            System.out.println(x[i]);
        }
    }
}
