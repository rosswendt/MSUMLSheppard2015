package Driver;
import DataPack.*;
import NeuralNet.*;

/**
 *
 * @author Ross Wendt
 */
public class Driver { 
    
    static Point[] dataSet;
    NeuralNet driverNet = new NeuralNet(0,0,0,0);
    
    public static void main(String Args[]) {
        dataSet = generateData();
        run(dataSet);
    }
    
    
    public static Point[] generateData() {
        Point[] P = null;
        return P;
    }
    
    public static void run(Point[] P) {
        //run code goes here
    }
    
    //perhaps various methods for printing different things

}

