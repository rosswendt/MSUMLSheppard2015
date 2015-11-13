package NeuralNet.TrainingMethod;

import Math.Matrix;
import NeuralNet.MatrixNeuralNet;
import java.util.Random;

/**
 *
 * @author Ross Wendt
 */
public class EvolutionStrategy implements TrainingMethodInterface {
    
    public int chromosomeDimension;
    public Random rdm = new Random();
    public int pRandomNumbers = 2;
    
    public Individual[] parents;
    public Individual[] children;

    
    public void initializeParents(){
        parents = new Individual[neuralNet.getMu()];
        chromosomeDimension = getDimension();
        for(int i = 0; i < parents.length; i++){
            parents[i] = new Individual(chromosomeDimension);
        }
        
        for(int i = 0; i < parents.length; i++){
            System.out.print("X: ");
            parents[i].xArray = randomlyInitializeParentChromosome(parents[i].xArray, 
                    neuralNet.getUpperBound());
            for(int j = 0; j < parents[i].xArray.length; j++){
                System.out.print(parents[i].xArray[j] + " ");
            }
            System.out.println();
            System.out.print("Sigma: ");
            parents[i].sigmaArray = randomlyInitializeParentSigmas(parents[i].sigmaArray, neuralNet.getInitialSigma());
            for(int j = 0; j < parents[i].sigmaArray.length; j++){
                System.out.print(parents[i].sigmaArray[j] + " ");
            }
            System.out.println();
            System.out.println();
        }
    }
    
    @Override
    public void applyMethod() {
        // initialize children for next generation
        children = new Individual[neuralNet.getLambda()];
        for(int i = 0; i < children.length; i++){
            children[i] = new Individual(chromosomeDimension);
        }
        
        for(int i = 0; i < children.length; i++){
            // initialize strategy parameter mutation
            double parameterMutation;
            int coin = rdm.nextInt(2);
            if(coin == 0){
                parameterMutation = 1.5;
            } else {
                parameterMutation = (1.0 / 1.5);
            }
            for(int j = 0; j < children[i].xArray.length; j++){
                //intermediate recombination
                double sigmaComponent = 0;
                for(int k = 0; k < pRandomNumbers; k++){
                    int randomParent = rdm.nextInt(neuralNet.getMu());
                    sigmaComponent += parents[randomParent].sigmaArray[j];
                }
                sigmaComponent = (sigmaComponent / pRandomNumbers);
                children[i].sigmaArray[j] = (parameterMutation * sigmaComponent);
                int k = rdm.nextInt(neuralNet.getMu());
                double z = rdm.nextDouble();
                children[i].xArray[j] = (parents[k].xArray[j] + ((children[i].sigmaArray[j] / Math.sqrt(chromosomeDimension))
                         * z));
            }
        }

    }

    public double[] createChromosome() {
        return new double[chromosomeDimension];
    }

    public double[] randomlyInitializeParentChromosome(double[] chromosome, double upperBound) {
        for (int i = 0; i < chromosome.length; i++) {
            double randomValue = (rdm.nextDouble() * (2 * upperBound)) - upperBound;
            chromosome[i] = randomValue;
        }
        return chromosome;
    }
    
    public double[] randomlyInitializeParentSigmas(double[] sigmas, double initialSigma){
        for(int i = 0; i < sigmas.length; i++){
            sigmas[i] = initialSigma;
        }
        return sigmas;
    }
    
    public int getDimension(){
        int chromosomeDimension = 0;
        for (int i = 0; i < neuralNet.weightMatrices.length; i++) {
            chromosomeDimension += ((neuralNet.weightMatrices[i].getRows() * neuralNet.weightMatrices[i].getColumns())
                    + (neuralNet.biasMatrices[i].getRows() * neuralNet.biasMatrices[i].getColumns()));
        }
        return chromosomeDimension;
    }
}
