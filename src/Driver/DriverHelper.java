package Driver;

import java.util.Random;

/**
 *
 * @author Angus Tomlinson
 */
public class DriverHelper {

    public static int[] randomizeIndexes(int dataSetSize) {
        int[] randomizedIndexes = new int[dataSetSize];
        for (int i = 0; i < randomizedIndexes.length; i++) {
            randomizedIndexes[i] = -1;
        }
        Random rdm = new Random();
        int randomIndex;
        for (int i = 0; i < randomizedIndexes.length; i++) {
            while (true) {
                randomIndex = rdm.nextInt(dataSetSize);
                if (!containsValue(randomizedIndexes, randomIndex)) {
                    randomizedIndexes[i] = randomIndex;
                    break;
                }
            }
        }

        return randomizedIndexes;
    }
    
    public static double[][] randomizeDataSet(double[][] dataSet, int[] randomizedIndexes){
        double[][] randomizedDataSet = new double[dataSet.length][dataSet[0].length];
        for(int i = 0; i < randomizedDataSet.length; i++){
            randomizedDataSet[i] = dataSet[randomizedIndexes[i]];
        }
        return randomizedDataSet;
    }
    
    public static Subset[] generateSubsets(double[][] dataSet, int k){
        Subset[] subsets = new Subset[k];
        for(int i = 0; i < subsets.length; i++){
            subsets[i] = new Subset(new double[dataSet.length / k][k]);
        }
        int dataSetCounter = 0;
        for(int i = 0; i < subsets.length; i++){
            for(int j = 0; j < (dataSet.length / k); j++){
                subsets[i].getSubsetValues()[j] = dataSet[dataSetCounter++];
            }
        }
        
        return subsets;
    }
    
    public static boolean containsValue(int[] array, int a) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                return true;
            }
        }
        return false;
    }

}
