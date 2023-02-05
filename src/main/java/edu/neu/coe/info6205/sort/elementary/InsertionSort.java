/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.util.Random;
import java.util.function.Consumer;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        // FIXME
        for (int i = from + 1; i < to; i++) {
            for (int j = i; j > from; j--) {
                if (!helper.swapStableConditional(xs, j)) {
                    break;
                }
            }
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    private static final int NUM_TESTS = 5;
    private static final int MIN_SIZE = 1000;
    private static final int MAX_SIZE = 16000;
    private static final int SIZE_INCREMENT = 1000;

    public static void main(String[] args) {
        // Measure the running times for each order type
        measureRunningTime(OrderType.RANDOM);
        measureRunningTime(OrderType.ORDERED);
        measureRunningTime(OrderType.PARTIALLY_ORDERED);
        measureRunningTime(OrderType.REVERSE_ORDERED);
    }

    private static void measureRunningTime(OrderType orderType) {
        System.out.println("Measuring running time for: " + orderType.toString());
        for (int i = 0; i < NUM_TESTS; i++) {
            int size = MIN_SIZE + i * SIZE_INCREMENT;
            Integer[] array = generateArray(size, orderType);
            Consumer<Integer[]> insertionSort = a -> InsertionSort.sort(array);
            Benchmark_Timer<Integer[]> bm = new Benchmark_Timer<>(orderType.toString(),
                    a -> { return a; },
                    insertionSort,
                    a -> {});
            double avgTime = bm.runFromSupplier(() -> array, 10);
            System.out.println("Size: "+size+"  Avg Time : " + avgTime + " ms");
        }
    }

    private static Integer[] generateArray(int size, OrderType orderType) {
        Integer[] array = new Integer[size];
        Random random = new Random();
        switch (orderType) {
            case RANDOM:
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt();
                }
                break;
            case ORDERED:
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                break;
            case PARTIALLY_ORDERED:
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt(size / 2);
                }
                break;
            case REVERSE_ORDERED:
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
        }
        return array;
    }

    private enum OrderType {
        RANDOM, ORDERED, PARTIALLY_ORDERED, REVERSE_ORDERED
    }
}
