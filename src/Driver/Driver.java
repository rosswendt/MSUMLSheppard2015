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
    static double[] dataSet;
    static double[] trainingSet;                         //needs to be arbitrarily chosen
    static double[] rosenbrock;
    //NeuralNet driverNet = new NeuralNet(0,0,0,0);
    static double outputYVal;
    
    public static void main(String Args[]) {
        int n = 5;                                      //n = number of points in the data set
        dataSet = generateData(n);                      //generates n points in the data set
        run(dataSet);
  
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
           computeRosenBrockOutVal(dataSet);

    }
    
    //perhaps various methods for printing different things 
                    //for now I've shoved those in run   

    public static double computeRosenBrockOutVal(double[] x) {
        // f(x) = sigma 100*(xi+1 - xi^2)^2 + (1-xi)^2      <--wiki
        //f(x,y) = (a-z)^2 + b(y-x^2)^2 <--wiki

        double temp = 0.0;
        for(int i=0; i<x.length -1; i++){
        double a = x[i];
        double b = x[i+1];
        double c = a*a;
     
        temp+= ((1-a)*(1-a)) + 100*((b-c)*(b-c)); 
        System.out.println(temp);
        
    }
        return temp;
}}
