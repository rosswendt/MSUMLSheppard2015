/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Driver;

/**
 *
 * @author Angus Tomlinson
 */
public class Subset {
    private final int samples;
    private final int dimension;
    private final double[][] subsetValues;
    
    public Subset(double[][] initialSubsetValues){
        samples = initialSubsetValues.length;
        dimension = initialSubsetValues.length;
        subsetValues = new double[samples][dimension];
    }
    
    public int getSamples(){
        return samples;
    }
    
    public int getDimension(){
        return dimension;
    }
    
    public double[][] getSubsetValues(){
        return subsetValues;
    }
}
