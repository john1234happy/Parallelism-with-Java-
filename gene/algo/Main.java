package gene.algo;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static gene.algo.GeneAlgoInParallel.run;

public class Main {
    public static void display() {
        Candidate ca = new Candidate();
        Display di = new Display(ca);
        Executor ex = Executors.newSingleThreadExecutor();
        ex.execute(di);
    }

    public static void main(String[] args) throws Exception {
        GeneAlgoInParallel.TARGET_LEN = 5;
        GeneAlgoInParallel.MAX_THREADS = 32;
        Random rand = new Random();
        GeneAlgoInParallel.TARGET = 0;
        int data = 0;

        display();

        for (int j = 0; j < GeneAlgoInParallel.TARGET_LEN; j++) {
            data = (rand.nextInt(95) + 31);
            GeneAlgoInParallel.TARGET += data;
        }

        int runs_per_thread_iter = 1;
        Long start, end;

        for (GeneAlgoInParallel.NUM_THREADS = 32; GeneAlgoInParallel.NUM_THREADS <= GeneAlgoInParallel.MAX_THREADS; GeneAlgoInParallel.NUM_THREADS++) {
            int sum = 0;
            Double totalTime = (double) 0;

            for (int i = 0; i < runs_per_thread_iter; i++) {
                System.out.print("\n\n\n\n\n\n\n: -------------New test Running------------- : \n\n\n\n\n\n");
                System.out.println(": -------------Run #: " + i + "------------- :");
                start = System.nanoTime();
                sum += run();
                end = System.nanoTime();
                totalTime += (end - start) / 1000.0;

                System.out.print("\n\n\n\n\n\n\n: -------------test done----------------- : \n\n\n\n\n\n");

            }
            System.out.println("THREADS, # GENERATIONS (%" + runs_per_thread_iter + " avg)");
            double avg_generations = (double) sum / runs_per_thread_iter;
            double avg_elapsed_time = totalTime / runs_per_thread_iter;
            //System.out.println(GeneAlgoInParallel.NUM_THREADS + "," + avg_generations);
            System.out.println("number of threads : " + GeneAlgoInParallel.NUM_THREADS + " Average number of number of generations : " + avg_generations + " Average time : " + avg_elapsed_time);
        }

    }
}
