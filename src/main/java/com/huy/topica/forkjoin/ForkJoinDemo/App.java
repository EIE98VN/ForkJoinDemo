package com.huy.topica.forkjoin.ForkJoinDemo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Hello world!
 *
 */
public class App {

    private static final int dis = 40000;
    private static final int ARR_LENGTH = 500019829 ;
    
    public static void main(String[] args) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(nThreads);

        int[] numbers = new int[ARR_LENGTH];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        long start = System.currentTimeMillis();
        
        ForkJoinPool forkJoinPool = new ForkJoinPool(nThreads);
        Long result = forkJoinPool.invoke(new ForkJoinSum(numbers, 0, numbers.length));
        System.out.println(result);
        
        long end = System.currentTimeMillis();
        System.out.println("Run time fork/join: " + (end - start));
        
        start = System.currentTimeMillis();
        System.out.println(sequentialSum(numbers, 0, numbers.length));
        end = System.currentTimeMillis();
        System.out.println("Run time sequential: " + (end-start));
        
    }

    @SuppressWarnings("serial")
    static class ForkJoinSum extends RecursiveTask<Long> {
        int low;
        int high;
        int[] array;

        ForkJoinSum(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        protected Long compute() {

            if (high - low <= dis) {
                return sequentialSum(array, low, high);
            } else {
                int mid = low + (high - low) / 2;
                ForkJoinSum left = new ForkJoinSum(array, low, mid);
                ForkJoinSum right = new ForkJoinSum(array, mid, high);
                
                invokeAll(left,right);
//                left.fork();
//                right.fork();
//                long rightResult = right.join();
//                long leftResult = left.join();
                return left.join() + right.join();
            }
        }
    }

    static long sequentialSum(int[] array, int low, int high) {
        long sum = 0;
        for (int i = low; i < high; i++)
            sum += array[i];
        return sum;
    }
}
