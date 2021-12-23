package gene.algo;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;


public class GeneAlgoInParallel {
    public static int NUM_THREADS;
    public static int MAX_THREADS;
    public static Candidate best_child;

    public static int GENESIZE = 32;
    public static int TARGET = 0;
    public static int TARGED = 0;
    public static int TARGET_LEN;

    public static int MUTATION_PROB = 100;
    public static int FIT_PARENT_PROB = 100;

    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean start = false;

    public static float getFitness(int[][] dna) {
        float fitness = 0;
        if (lock.tryLock()) {

            for (int i = 0; i < dna.length; i++) {
                for (int j = 0; j < dna[i].length; j++) {
                    if (dna[i][j] != 0) {

                        for (int k = 0; k < dna.length; k++) {

                            for (int l = 0; l < dna[k].length; l++) {

                                if (dna[k][l] != 0 && !(i == k && j == l)) {

                                    double distance = Math.pow((i - k), 2) + Math.pow((j - l), 2);

                                    double totalValue = (dna[i][j] + dna[k][l]) * 5;

                                    double metric = (totalValue / distance);

                                    fitness += metric;
                                }
                            }
                        }
                    }
                }
            }
            lock.unlock();
        }

        return fitness;
    }

    public static Candidate mutate(Candidate parent1, Candidate parent2) {
        //put locks here
        int[][] childDna = parent1.getDna();
        float childFitness = 0;
        Random rand = new Random();

        try {
            lock.lock();
            // Crossover
            for (int i = 0; i < GENESIZE / 2; i++) {
                int x1 = rand.nextInt(10);
                int y1 = rand.nextInt(10);

                childDna[x1][y1] = parent2.getDna()[x1][y1];
            }
            // Mutation
            for (int i = 0; i < GENESIZE / 8; i++) {
                if (rand.nextInt(100) < MUTATION_PROB) {
                    int xToMutate = rand.nextInt(10);
                    int yToMutate = rand.nextInt(10);
                    int x = 0;
                    int r = rand.nextInt(100);

                    if (r < 50) {
                        x = rand.nextInt(10);
                    } else if (r > 50) {
                        x = rand.nextInt(10);
                        x = x * -1;
                    }
                    childDna[xToMutate][yToMutate] = x;
                }
            }
            childFitness = getFitness(childDna);
        } finally {
            lock.unlock(); // unlocks here
        }
        return new Candidate(childDna, childFitness);
    }

    public static Candidate getRandParent(List<Candidate> genepool) {

        List<Candidate> genelist = genepool;
        List<Candidate> bestgene = new ArrayList<>();
        int randIndex = 0;
        Random rand = new Random();
        Candidate OOF = null;

        try {
            lock.lock();
            Collections.sort(genelist);

            if (TARGED > 0) {
                bestgene = genelist.subList(genelist.size() / 2, genelist.size());
            } else if (TARGED < 0) {
                bestgene = genelist.subList(0, genelist.size() / 2);
            } else {
                bestgene = genelist.subList((genelist.size() / 2) - (genelist.size() / 4), (genelist.size() / 2) + (genelist.size() / 4));
            }

            if (rand.nextInt(100) < FIT_PARENT_PROB) {
                randIndex = Math.round(rand.nextFloat() * rand.nextFloat() * ((bestgene.size() / 2) - 1));

                // figure out a way to get the best two parents try divideing the genepool in to part and finding the best from each
            } else {
                randIndex = Math.round(rand.nextInt(bestgene.size() / 2) + bestgene.size() / 2);
            }
            if (bestgene != null) {
                OOF = bestgene.get(randIndex);
            }
        } finally {
            lock.unlock();
        }
        return OOF;
    }


    public static List<Candidate> seedPopulation() {

        List<Candidate> genepool = new ArrayList<Candidate>();

        Random r = new Random();

        for (int i = 0; i < GENESIZE; i++) {
            int[][] dna = new int[10][10];
            int j = 0;

            while (j < GENESIZE - 6) {
                int x = r.nextInt(dna.length);
                int y = r.nextInt(dna[0].length);

                if (dna[x][y] == 0) {
                    j++;
                    int z = 0;
                    int g = r.nextInt(100);

                    if (g < 50) {
                        z = r.nextInt(10);
                    } else if (g > 50) { // set r.nextInt at r and check both neg and pos agiant it.
                        z = r.nextInt(10);
                        z = z * -1;
                    }
                    dna[x][y] = z;
                }
            }
            float fitness = getFitness(dna);
            genepool.add(new Candidate(dna, fitness));
        }
        return genepool;
    }

    public static int run() throws Exception {
        GeneAlgoInParallel.best_child = new Candidate(null, 0);
        int generation = 0;
        List<Candidate> genepool = seedPopulation();

        while (true) {
            generation++;
            try {
                lock.lock();
                Collections.sort(genepool);
            } finally {
                lock.unlock();
            }
            System.out.println(generation + " " + genepool.get(genepool.size() - 1).getFitness() + " ");
            print(genepool);

            if (genepool.get(genepool.size() - 1).getFitness() < TARGED + 1 && genepool.get(genepool.size() - 1).getFitness() > TARGED - 1) {
                System.out.println("Found target at generation " + generation);
                break;
            }
            MyThread[] threads = new MyThread[GeneAlgoInParallel.NUM_THREADS];
            //Run the threads
            for (int i = 0; i < GeneAlgoInParallel.NUM_THREADS; i++) {
                MyThread temp = new MyThread("" + i, genepool);
                temp.start();
            }
            if (GeneAlgoInParallel.best_child.getDistanceFromTargetFitness() < genepool.get(GENESIZE - 1).getDistanceFromTargetFitness()) {
                genepool.set(GENESIZE - 1, GeneAlgoInParallel.best_child);
                Display.interrupt(GeneAlgoInParallel.best_child);
            }
        }
        return generation;
    }

    public static void print(List<Candidate> genepool) {
        try {
            lock.lock();
            for (int p = 0; p < genepool.size(); p++) {
                int[][] dna = genepool.get(p).getDna();
                System.out.println(":--------------------------------------:");

                for (int i = 0; i < dna.length; i++) {
                    System.out.println(Arrays.toString(dna[i]));
                }
                System.out.println(":-------------              ------------:");
                System.out.println(":------fitness :" + genepool.get(p).getFitness() + ":--------:");
                System.out.println(":--------------------------------------:");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void setsStart(boolean a) {
        for (; ; ) {
            try{
                lock.lock();
                start = a;
                break;
            }finally {
                lock.unlock();
            }
        }
    }
}



