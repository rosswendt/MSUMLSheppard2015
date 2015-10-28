package Driver;

import java.util.Random;

/**
 *
 * @author Ross Wendt
 */
public class DriverHelper {
    

    public static boolean containsValue(int[] array, int a) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == a) {
                return true;
            }
        }
        return false;
    }

    public static int[][] initializeSubsets(int dataSetSize, int k) {
        int[] selectedIndexes = new int[dataSetSize];
        for (int i = 0; i < selectedIndexes.length; i++) {
            selectedIndexes[i] = -1;
        }
        Random rdm = new Random();
        int randomIndex;
        int selectedIndexesCounter = 0;
        int[][] subsets = new int[k][dataSetSize / k];
        for (int[] subset : subsets) {
            for (int j = 0; j < subsets[0].length; j++) {
                boolean counterAssigned = false;
                while (!counterAssigned) {
                    randomIndex = rdm.nextInt(dataSetSize);
                    //System.out.println(randomIndex);
                    if (!containsValue(selectedIndexes, randomIndex)) {
                        subset[j] = randomIndex;
                        selectedIndexes[selectedIndexesCounter] = randomIndex;
                        selectedIndexesCounter++;
                        counterAssigned = true;
                    }
                }
            }
        }
        return subsets;
    }

    
}
