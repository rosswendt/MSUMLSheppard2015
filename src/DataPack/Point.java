package DataPack;

/**
 *
 * @author Ross Wendt, Lukas Keller
 */
public class Point {
    int generatedXVal;
    
    
    public Point(int startGeneratedXVal) {
        generatedXVal = startGeneratedXVal;
    }
    
    public boolean isTrainingSet(int x) {
        boolean b = false;
        if(x == 1){
            b = true;
        }
        return b;
    }
    
    public boolean isTestSet(int x) {
        boolean b = false;
        if(x == 1){
            b = true;
        }
        return b;
    }
    
    public static Point generatePoint(int x) {
        Point p = new Point(x);
        return p;
    }
    
      public int generatedXVal(int generatedXVal) {
        this.generatedXVal = generatedXVal;
        return generatedXVal;
    }

}
