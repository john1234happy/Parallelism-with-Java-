package gene.algo;

import java.util.List;

import static gene.algo.GeneAlgoInParallel.TARGED;

class MyThread extends Thread {
    List<Candidate> genepool;

    MyThread(String name, List<Candidate> genepool) {
        super(name);
        this.genepool = genepool;
    }

    public void run() {
        Candidate parent1 = GeneAlgoInParallel.getRandParent(this.genepool);
        Candidate parent2 = GeneAlgoInParallel.getRandParent(this.genepool);

        Candidate child = GeneAlgoInParallel.mutate(parent1, parent2);

        synchronized (GeneAlgoInParallel.best_child) {

            if (GeneAlgoInParallel.best_child.getFitness() == 0) {
                GeneAlgoInParallel.best_child = child;
            } else if (child.getDistanceFromTargetFitness() < GeneAlgoInParallel.best_child.getDistanceFromTargetFitness()) {
                GeneAlgoInParallel.best_child = child;
            }
        }
    }
}