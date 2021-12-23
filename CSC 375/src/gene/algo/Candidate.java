package gene.algo;

import java.util.Arrays;
import java.util.Comparator;

import static gene.algo.GeneAlgoInParallel.TARGED;
import static java.lang.Math.abs;

class Candidate implements Comparator<Candidate>, Comparable<Candidate> {

    private int[][] dna = new int[10][10];
    private float fitness;

    public Candidate() {
        this.dna = null;
        this.fitness = 0;
    }

    public Candidate(int[][] dna, float fitness) {
        this.dna = dna;
        this.fitness = fitness;
    }

    public void print() {
        System.out.println(this.getFitness() + " --> ");
        for (int i = 0; i < dna.length; i++) {
            System.out.println(Arrays.toString(dna[i]));
        }
    }

    public int[][] getDna() {
        return this.dna;
    }

    public void setDna(int[][] dna) {
        this.dna = dna;
    }

    public float getFitness() {
        return this.fitness;
    }

    public float getDistanceFromTargetFitness() {
        float fit = 0;

        if (fitness < 0) {
            fit = fitness - TARGED;
            fit = abs(fit);
        } else {
            fit = fitness - TARGED;
            fit = abs(fit);
        }

        return fit;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    // Overriding the compareTo method
    @Override
    public int compareTo(Candidate c) {

        if (c.getFitness() < this.fitness) return 1;
        else if (c.getFitness() > this.fitness) return -1;
        return 0;
    }

    // Overriding the compare method to sort the age
    @Override
    public int compare(Candidate c1, Candidate c2) {
        return c1.compareTo(c2);
    }
}
