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

    public void initializeParents() {
        parents = new Individual[neuralNet.getMu()];
        chromosomeDimension = getDimension();
        for (int i = 0; i < parents.length; i++) {
            parents[i] = new Individual(chromosomeDimension);
        }

        for (int i = 0; i < parents.length; i++) {
            System.out.print("X: ");
            parents[i].xArray = randomlyInitializeParentChromosome(parents[i].xArray,
                    neuralNet.getUpperBound());
            for (int j = 0; j < parents[i].xArray.length; j++) {
                System.out.print(parents[i].xArray[j] + " ");
            }
            System.out.println();
            System.out.print("Sigma: ");
            parents[i].sigmaArray = randomlyInitializeParentSigmas(parents[i].sigmaArray, neuralNet.getInitialSigma());
            for (int j = 0; j < parents[i].sigmaArray.length; j++) {
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
        for (int i = 0; i < children.length; i++) {
            children[i] = new Individual(chromosomeDimension);
        }

        for (int i = 0; i < children.length; i++) {
            // initialize strategy parameter mutation
            double parameterMutation;
            int coin = rdm.nextInt(2);
            if (coin == 0) {
                parameterMutation = 3.0;
            } else {
                parameterMutation = (1.0 / 3.0);
            }
            for (int j = 0; j < children[i].xArray.length; j++) {
                //intermediate recombination
                double sigmaComponent = 0;
                for (int k = 0; k < pRandomNumbers; k++) {
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
        evaluateIndividuals();
    }

    @Override
    public void evaluateIndividuals() {

        double[] lowestErrors = new double[neuralNet.getMu()];
        for(int i = 0; i < lowestErrors.length; i++){
            lowestErrors[i] = Double.POSITIVE_INFINITY;
        }
        int[] lowestIndexes = new int[neuralNet.getMu()];
        for(int i = 0; i < lowestIndexes.length; i++){
            lowestIndexes[i] = -1;
        }
        String[] lowestDesignations = new String[neuralNet.getMu()];
        for(int i = 0; i < lowestDesignations.length; i++){
            lowestDesignations[i] = " ";
        }
        for (int i = 0; i < parents.length; i++) {
            setWeightMatricesToChromosome(parents[i].xArray);
            System.out.println("Weight Matrices:");
            for (int k = 0; k < neuralNet.weightMatrices.length; k++) {
                for (int l = 0; l < neuralNet.weightMatrices[k].getRows(); l++) {
                    for (int j = 0; j < neuralNet.weightMatrices[k].getArray()[l].length; j++) {
                        System.out.print(neuralNet.weightMatrices[k].getArray()[l][j] + " ");
                        if (neuralNet.weightMatrices[k].getArray()[l][j] >= 10000) {
                        } else if (neuralNet.weightMatrices[k].getArray()[l][j] >= 1000) {
                            System.out.print(" ");
                        } else if (neuralNet.weightMatrices[k].getArray()[l][j] >= 100) {
                            System.out.print("  ");
                        } else if (neuralNet.weightMatrices[k].getArray()[l][j] >= 10) {
                            System.out.print("   ");
                        } else {
                            System.out.print("    ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println("Bias Matrices");
            for (int k = 0; k < neuralNet.biasMatrices.length; k++) {
                for (int l = 0; l < neuralNet.biasMatrices[k].getRows(); l++) {
                    for (int j = 0; j < neuralNet.biasMatrices[k].getArray()[l].length; j++) {
                        System.out.print(neuralNet.biasMatrices[k].getArray()[l][j] + " ");
                        if (neuralNet.biasMatrices[k].getArray()[l][j] >= 10000) {
                        } else if (neuralNet.biasMatrices[k].getArray()[l][j] >= 1000) {
                            System.out.print(" ");
                        } else if (neuralNet.biasMatrices[k].getArray()[l][j] >= 100) {
                            System.out.print("  ");
                        } else if (neuralNet.biasMatrices[k].getArray()[l][j] >= 10) {
                            System.out.print("   ");
                        } else {
                            System.out.print("    ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
            neuralNet.forwardPropagation();
            System.out.println("Error: " + neuralNet.getError().getMatrixValues()[0][0] + " p" + i);
            addLowestError(lowestErrors, neuralNet.getError().getMatrixValues()[0][0], lowestIndexes, i, 
                    lowestDesignations, "p");
        }
        for (int i = 0; i < children.length; i++) {
            setWeightMatricesToChromosome(children[i].xArray);
            neuralNet.forwardPropagation();
            System.out.println("Error: " + neuralNet.getError().getMatrixValues()[0][0] + " c" + i);
            addLowestError(lowestErrors, neuralNet.getError().getMatrixValues()[0][0], lowestIndexes, i,
                    lowestDesignations, "c");
        }
        System.out.println("New Parent Generation:");
        for(int i = 0; i < neuralNet.getMu(); i++){
            if(lowestDesignations[i].equals("p")){
                parents[i] = parents[lowestIndexes[i]];
                System.out.println("Error: " + lowestErrors[i] + " p" + lowestIndexes[i]);
            } else {
                parents[i] = children[lowestIndexes[i]];
                System.out.println("Error: " + lowestErrors[i] + " c" + lowestIndexes[i]);
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

    public double[] randomlyInitializeParentSigmas(double[] sigmas, double initialSigma) {
        for (int i = 0; i < sigmas.length; i++) {
            sigmas[i] = initialSigma;
        }
        return sigmas;
    }

    public int getDimension() {
        int chromosomeDimension = 0;
        for (int i = 0; i < neuralNet.weightMatrices.length; i++) {
            chromosomeDimension += ((neuralNet.weightMatrices[i].getRows() * neuralNet.weightMatrices[i].getColumns())
                    + (neuralNet.biasMatrices[i].getRows() * neuralNet.biasMatrices[i].getColumns()));
        }
        return chromosomeDimension;
    }

    public void setWeightMatricesToChromosome(double[] chromosome) {
        int chromosomeCounter = 0;
        for (int i = 0; i < neuralNet.weightMatrices.length; i++) {
            for (int j = 0; j < neuralNet.weightMatrices[i].getRows(); j++) {
                for (int k = 0; k < neuralNet.weightMatrices[i].getColumns(); k++) {
                    neuralNet.weightMatrices[i].getMatrixValues()[j][k] = chromosome[chromosomeCounter++];
                }
            }
        }
        for (int i = 0; i < neuralNet.biasMatrices.length; i++) {
            for (int j = 0; j < neuralNet.biasMatrices[i].getRows(); j++) {
                for (int k = 0; k < neuralNet.biasMatrices[i].getColumns(); k++) {
                    neuralNet.biasMatrices[i].getMatrixValues()[j][k] = chromosome[chromosomeCounter++];
                }
            }
        }
    }
    
    public void addLowestError(double[] lowestErrors, double error, int[] lowestIndexes, int index, 
            String[] lowestDesignations, String designation){
        for(int i = 0; i < lowestErrors.length; i++){
            if(error < lowestErrors[i]){
                double temporaryError;
                int temporaryIndex;
                String temporaryDesignation;
                temporaryError = lowestErrors[i];
                temporaryIndex = lowestIndexes[i];
                temporaryDesignation = lowestDesignations[i];
                lowestErrors[i] = error;
                lowestIndexes[i] = index;
                lowestDesignations[i] = designation;
                error = temporaryError;
                index = temporaryIndex;
                designation = temporaryDesignation;
            }
        }
    }
}
