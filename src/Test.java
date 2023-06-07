import interfaces.sorter;
import sortings.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    private static final int NUM_ITERATIONS = 10;
    private static final int[] SIZES_TO_TEST = {10, 100, 500, 1000, 10000, 20000, 40000, 60000, 80000, 100000};

    static Class<? extends sorter>[] classes;
    static String[] classNames;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        load();
        run();
    }

    public static void run() throws InstantiationException, IllegalAccessException {
        long[] timers = new long[classNames.length];

        String tableHeader = "Array size";
        for (int i = 0; i < classNames.length; i++) {
            tableHeader+= ", " + classNames[i] + " (ns)";
        }
        System.out.println(tableHeader);
        for (int arrSize : SIZES_TO_TEST) {
            resetTimers(timers);
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                long startTime = 0, endTime = 0;

                int[] baseArr = createRandomArray(arrSize);

                for (int j = 0; j < classNames.length; j++) {
                    // --------- sort ---------
                    int[] arrCopy = Arrays.copyOf(baseArr, baseArr.length);
                    startTime = System.currentTimeMillis();
                    ((sorter) classes[j].newInstance()).sort(arrCopy);
                    endTime = System.currentTimeMillis();
                    timers[j] += (endTime - startTime);
                    // ------------------------
                }

            }

            String printStatement = arrSize + ", ";
            for (int i = 0; i < classNames.length; i++) {
                printStatement += (double)timers[i]/NUM_ITERATIONS + ", ";
            }
            System.out.println(printStatement);
        }
    }

    private static void resetTimers(long[] timers) {
        for (int i = 0; i < timers.length; i++) {
            timers[i]=0;
        }
    }

    public static void load() {
        classNames = loopDirectory();
        classes = new Class[classNames.length];
        try {
            // For each class name in the array
            for (int i = 0; i < classNames.length; i++) {
                String className = classNames[i];
                // Load the class using the class loader
                Class<?> clazz = Class.forName("sortings."+className);
                clazz.newInstance();
                classes[i] = (Class<? extends sorter>) clazz;
            }
        } catch (Exception e) {
            // Handle the case where the specified class cannot be found
            e.printStackTrace();
        }
    }

    public static String[] loopDirectory() {
        File directory = new File("C:/Users/zivbe/IdeaProjects/Merge/src/sortings");
        // Get a list of files in the directory
        File[] fileList = directory.listFiles();

        // Create an array to store the names of the files
        String[] fileNames = new String[fileList.length];

        // Loop through the files and add their names to the array
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                String fileName = fileList[i].getName();
                String className = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                fileNames[i] = className;
            }
        }

        return fileNames;
    }



    private static int[] createRandomArray(int arrSize) {
        int[] arr = new int[arrSize];

        for (int i = 0; i < arrSize; i++) {
            arr[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }

        return arr;
    }
}
