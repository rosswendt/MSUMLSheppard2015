/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NeuralNet.TrainingMethod;

/**
 *
 * @author Angus Tomlinson
 */
public class Individual {
    public double[] xArray;
    public double[] sigmaArray;
    
    public Individual(int dimension){
        xArray = new double[dimension];
        sigmaArray = new double[dimension];
    }
}
