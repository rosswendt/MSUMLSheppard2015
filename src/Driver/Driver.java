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
    
    static Point[] dataSet;
    //NeuralNet driverNet = new NeuralNet(0,0,0,0);
    
    public static void main(String Args[]) {
        int n = 5;                                      //n = number of points in the data set
        dataSet = generateData(n);                  //generates n points in the data set
        run(dataSet);
  
    }
    
    
    public static Point[] generateData(int x) {
        Point[] P = new Point[x];
        
        for(int i = 0; i<x; i++){
           P[i] = Point.generatePoint(5);
        }
        
        return P;
    }
    
    public static void run(Point[] P) {
//        for(int i = 0; i<P.length; i++){
//           Random ran = new Random();
//           int randomNum = ran.nextInt(5)+1;
//           System.out.print(P[i].generatedXVal(randomNum) + ", ");      //Generating random values for the x variable from 1-5
//        }
//        
    }
    
    //perhaps various methods for printing different things 
                    //for now I've shoved those in run   

}

