package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class BigIntegerFibonacci {
    public static MyBigInteger bigFibLoop(int xSmall)                              // looping fibonacci function
    {
        MyBigInteger x = new MyBigInteger(Integer.toString(xSmall));
        MyBigInteger a = new MyBigInteger("0");
        MyBigInteger b = new MyBigInteger("1");
        MyBigInteger temp = new MyBigInteger();

        if (x.Value().length() <= 1)                                                //checks if x is 0 or 1
        {
            return x;
        } else {

            for (int i = 0; i <= x.ConvertToInt('2') ; i++)                            //loops through until x is reached.
            {
                temp = a.Plus(b);                                       // sets the values for the next loop
                a.SetValue(b.Value());;
                b.SetValue(temp.Value());
            }
        }
        return b;
    }

    public static MyBigInteger bigFibMatrix(int xSmall) {
        MyBigInteger x = new MyBigInteger(Integer.toString(xSmall));
        MyBigInteger[][] exponentialMatrix = {{new MyBigInteger("1"), new MyBigInteger("1")},
                {new MyBigInteger("1"), new MyBigInteger("0")}};
        MyBigInteger[][] resultMatrix = {{new MyBigInteger("1"), new MyBigInteger("1")},
                {new MyBigInteger("1"), new MyBigInteger("0")}};
        MyBigInteger val = new MyBigInteger("2");
        long y = Long.parseLong(x.Value()) - 2;
        if (x.Value.equals("0")) {
            return x;
        } else {
            while (y > 0) {
                if ((y % 2) == 1) {
                    resultMatrix = bigMultiplicationMatrix(exponentialMatrix, exponentialMatrix, 2, 2, 2, 2);
                }
                exponentialMatrix = bigMultiplicationMatrix(exponentialMatrix, exponentialMatrix, 2, 2, 2, 2);
                y = y / 2;
            }
        }
        return resultMatrix[0][0];
    }

    public static MyBigInteger[][] bigMultiplicationMatrix(MyBigInteger[][] m1, MyBigInteger[][] m2, int row, int row2, int col, int col2) {
        MyBigInteger[][] resultMatrix = new MyBigInteger[row][col2];
        MyBigInteger result = new MyBigInteger();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col2; j++) {
                result.SetValue("0");
                for (int k = 0; k < col; k++) {
                    result = result.Plus(m1[row][k].Times(m2[k][col2]));
                }
            }

            resultMatrix[row][col2] = new MyBigInteger(result.Value());
        }

        return resultMatrix;
    }


    /** Get CPU time in nanoseconds since the program(thread) started. */
    /**
     * from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime
     **/
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;

    }

    public static void ResultsTableArithmetic() {
        long N = 1;
        long prevN = 0;
        long stop;
        int X1, X2;
        int changingN;
        float doublRatio;
        float expDoublRatio;
        long startTime = 0;
        long endTime = 0;
        long stopWatch = 0;
        long prevTime = 0;
        int counter = 0;
        String one, two;
        String NA = "NA";
        double maxTime = Math.pow(2, 33);
        boolean run = true;

        System.out.printf("Plus()\n");
        System.out.printf("| %20s| %20s| %15s| %17s| %25s|\n", "N(input size)", "X1,X2(input value)", "Time", "Doubling Ratio", "Expected Doubling Ratio");
        while (run) {
            if(counter > 61){
                run = false;
            }
            System.out.printf("| %20s", N);
            Random randNum = new Random();
            X1 = randNum.nextInt(69);
            X2 = randNum.nextInt(69);
            one = Integer.toString(X1);
            two = Integer.toString(X2);
            System.out.printf("| %17s,%2s", one, two);
            MyBigInteger A = new MyBigInteger(one);
            MyBigInteger B = new MyBigInteger(two);
            if (stopWatch < maxTime) {
                startTime = getCpuTime();
                A.Plus(B);
                endTime = getCpuTime();
                stopWatch = endTime - startTime;
                System.out.printf("| %15d", stopWatch);
                if (prevTime == 0) {
                    System.out.printf("| %17s| %25s|\n", NA, NA);
                } else {
                    doublRatio = (float) stopWatch / (float) prevTime;
                    expDoublRatio = (float) (N / prevN);
                    System.out.printf("| %17.2f| %25.2f|\n", doublRatio, expDoublRatio);
                }
            }
            if (stopWatch > maxTime) {
                run = false;
            }
            prevTime = stopWatch;
            prevN = N;
            N = N * 2;
            counter++;

        }
        run = true;
        N = 1;
        prevN = 0;
        prevTime = 0;
        counter = 0;
        System.out.printf("Times()\n");
        System.out.printf("| %20s| %20s| %15s| %17s| %25s|\n", "N(input size)", "X1,X2(input value)", "Time", "Doubling Ratio", "Expected Doubling Ratio");
        while (run) {
            if(counter > 61){
                run = false;
            }
            System.out.printf("| %20s", N);
            Random randNum = new Random();
            X1 = randNum.nextInt(69);
            X2 = randNum.nextInt(69);
            one = Integer.toString(X1);
            two = Integer.toString(X2);
            System.out.printf("| %17s,%2s ", one, two);
            MyBigInteger A = new MyBigInteger(one);
            MyBigInteger B = new MyBigInteger(two);
            if (stopWatch < maxTime) {
                startTime = getCpuTime();
                A.Times(B);
                endTime = getCpuTime();
                stopWatch = endTime - startTime;
                System.out.printf("| %15d", stopWatch);
                if (prevTime == 0) {
                    System.out.printf("| %17s| %25s|\n", NA, NA);
                } else {
                    doublRatio = (float) stopWatch / (float) prevTime;
                    expDoublRatio = (float) (N / prevN);
                    System.out.printf("| %17.2f| %25.2f|\n", doublRatio, expDoublRatio);
                }
            }
            if (stopWatch > maxTime) {
                run = false;
            }
            prevTime = stopWatch;
            prevN = N;
            N = N * 2;
            counter++;

        }
    }
    public static void fibResults(){
        int N;
        int x;
        int y;
        int resultTime = 0;
        long startTime = 0;
        long endTime = 0;
        long maxRunTime = 24000000L;
        long endFibRecurRunTime = 0;
        int maxFibRecurLoops = 100;
        MyBigInteger bigResult = new MyBigInteger();
        long[] fibLoopTime = new long[93];                          //Keeps the times for fibLoop
        long[] fibMatrixTime = new long[93];                        //Keeps the times for fibMatrix
        int numberLoops = 69000;                                    // the number of loops for to run


        System.out.printf("FibLoop\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {                                  //Nested loops that will run through FibLoop
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {
                for (y = 0; y < numberLoops; y++) {                 //runs until max loops are reached
                    startTime = getCpuTime();
                    bigResult = bigFibLoop(x);                        //Measures the time the function take
                    endTime = getCpuTime();
                    fibLoopTime[x] += endTime - startTime;
                }
                for(int i = 0; i < bigResult.Value.length(); i++) {
                    resultTime = bigResult.ConvertToInt(bigResult.Value.charAt(i));
                }
                fibLoopTime[x] = fibLoopTime[x] / numberLoops;
                System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibLoopTime[x]);
                if (x != 0 && x % 2 == 0) {                         //Only prints ratios if an even number
                    float doubling = (float) fibLoopTime[x] / fibLoopTime[x / 2];
                    double expectedDoubling = 2;
                    System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                } else {
                    System.out.printf(" %10s| %10s|\n", "NA", "NA");
                }
            }
        }
        System.out.printf("FibMatrix\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {                                  //Nested loops that will run through FibMatrix
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {
                for (y = 0; y < numberLoops; y++) {                 //runs until max loops are reached
                    startTime = getCpuTime();
                    bigResult = bigFibMatrix(x);                      //Measures the time the function take
                    endTime = getCpuTime();
                    fibMatrixTime[x] += endTime - startTime;
                }
                for(int i = 0; i < bigResult.Value.length(); i++) {
                    resultTime = bigResult.ConvertToInt(bigResult.Value.charAt(i));
                }
                fibMatrixTime[x] = fibMatrixTime[x] / numberLoops;
                System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibMatrixTime[x]);
                if (x != 0 && x % 2 == 0) {                         //Only prints ratios if an even number
                    float doubling = (float) fibMatrixTime[x] / fibMatrixTime[x / 2];
                    double expectedDoubling = (Math.log(N)/Math.log(2)) / (Math.log(N/2)/Math.log(2));
                    System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                } else {
                    System.out.printf(" %10s| %10s|\n", "NA", "NA");
                }
            }
        }
    }
}