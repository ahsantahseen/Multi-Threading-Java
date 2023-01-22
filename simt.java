/*
 * Parallel and Distributed Computing Assignment 1
 * 
 *   Author: M.Ahsan - 1912310
 * 
 *   Tested Device: Lenovo Ideapad 3 
 *   Operating System: Windows 10 Pro
 *   Intel® Core™ i5-1135G7 Processor (4 Cores, 8 Threads)
 *   12 GB DDR4 3200Mhz
 *  
 *  Approach: SIMD's sub category (SIMT) with Data Parallelism
 *  Using 4 Threads each having different data with a single instruction
 * 
 * 
 */

// Result: With 4 Thread Parallel Execution
/// Execution Time Ranging From 2-5 milliseconds

import java.util.ArrayList;
import java.util.List;

public class simt {
    // Initializing a volatile variable with data type of double named 'sum'
    // Reason to use volatile is because we are dealing with multiple threads here
    // so we don't need to access the sum from any local variable we need to access
    // it directly from the memory and it's reads and writes are atomic
    private static volatile double sum = 0;

    // Utility Method To Round Off Double to n decimal places
    public static double round(double value, int places) {
        // If Places aren't above 0 then just throw and exception
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        // Using Math.pow() to take out the factor
        long factor = (long) Math.pow(10, places);
        // Multiply the value by factor
        value = value * factor;
        // Temporarily store the round off value
        long tmp = Math.round(value);
        // Return it by diving over the factor
        return (double) tmp / factor;
    }

    public static void main(String[] args) {

        // Creating Thread List To Store Threads Later On
        List<solverThread> threadsList = new ArrayList<solverThread>();
        // Defining Constraints Here (number of threads, start,end variables for
        // assigning to threads and increment which defines how much data will be given
        // to each
        // thread)
        double numberOfThreads = 4;
        double increment = 180 / numberOfThreads;
        double start = 0.00, end = start;
        // Clocking the Start Time
        long startTime = System.nanoTime();
        // Creating Threads Here using a for loop
        for (int i = 1; i <= numberOfThreads; i++) {
            // Assigning Increment to the end variable to be passed on the thread
            // Example for first thread: end will be 45+45=90 so from 45 to 90
            end += increment;
            // For Thread 1 (1,0.00,45.00)
            solverThread t = new solverThread(i, start, end);
            // Start will be incremented to the increment value
            // For First Thread it will be 0.00 then for second thead it will be 45.00
            start += increment;
            // Running the Thread
            t.start();
            // Adding the Thread to the thread list
            threadsList.add(t);
        }

        // Looping Through the ThreadList and stoppping them
        // ** although the threads will be dead soon we are waiting for their closing so
        // that we can get back the locks of the sum variable from the last thread that
        // has all sum's value so that we can print in main thread
        for (int i = 0; i < numberOfThreads; i++) {
            solverThread t = threadsList.get(i);
            try {
                t.join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        // Printing Out the sum once all the other threads have removed their locks from
        // it
        // Clocking the End Time
        long endTime = System.nanoTime();
        long execution = endTime - startTime;
        double ms = (double) execution / 1000000;
        // Calculating Execution Time Overall
        System.out.println(
                "Execution time: " + ms + " ms");
        System.out.println("Total Sum: " + sum);

    }

    // This is the Class/Blueprint of the solverThread which acts as an instrution
    static class solverThread extends Thread {
        // Defining Constraints Here
        private double startFrom;
        private double endTo;
        private int ThreadNumber;
        // Local Sum will hold the total sum of what the thread processed
        // from it's given data and then it will add it to the main sum
        private double localSum = 0;
        // This a lock Object for syncronization function
        private final Object lock = new Object();

        // This is the constructor for a thread which takes 3 params
        // 'ThreadNumber','Till where to End','From Where to Start'
        // Just for numbering, For looping Data Purposes
        public solverThread(int ThreadNumber, double start, double end) {
            if (ThreadNumber != 1) {
                this.startFrom = start + 0.01;
            } else {
                this.startFrom = start;
            }
            this.endTo = end;
            this.ThreadNumber = ThreadNumber;
        }

        // This is the run method which is the responsible for calculating
        // y based on x values as i
        @Override
        public void run() {
            // Start the loop from the starting value and end till the end value and
            // increase the step by 0.01 / increment by 0.01
            for (double i = startFrom; i <= endTo; i += 0.01) {
                i = round(i, 2);
                if (i != 0.00) {
                    localSum += 1 / Math.cos(i) + 1 / Math.sin(i);
                }
            }
            // Locking the sum variable as it's being written on
            // This will not allow any other value to overwrite it as it locks the sum
            // variable
            synchronized (lock) {
                // Debug Statement to check the localSum
                // System.out.println("Local Sum: " + localSum);
                sum += (localSum);
            }
            // Once the lock is released it prints the sum value from main memory
            // Debug / Elaboration Print
            System.out.println("Thread: " + ThreadNumber + " Sum: " + sum);
        }
    }
}
