package gene.algo;

import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;


public class Display extends JFrame implements Runnable {
    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean interruptFlag = false;
    private static Candidate g;
    //Max weight of 255, with three colors of 255 values each leads to 3 rgb value per 1 weight
    private final int COLOR_INCREMENT = (255 * 3) / 100;
    private final int STATION_SIZE = 80;
    private JPanel window, panelSolution, labelTop, labelBottom;
    private JLabel solutionLabel, solutionMetric;
    private final DrawPanel[][] solutionFloor = new DrawPanel[10][10];
    private final DecimalFormat df = new DecimalFormat("#.###");

    public Display(Candidate ca) {
        initComponents(ca);
    }

    public static void interrupt(Candidate ca) {
        for (; ; ) {
            try {
                lock.tryLock();
                interruptFlag = true;
                g = ca;
                break;
            } finally {
                lock.unlock();
            }
        }
    }

    private void initComponents(Candidate gr) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize((STATION_SIZE * 10) + STATION_SIZE, (10 * STATION_SIZE) + STATION_SIZE);

        window = new JPanel();
        window.setLayout(new BorderLayout());
        window.setSize(500, 800);

        labelTop = new JPanel();
        labelTop.setLayout(new BorderLayout());

        solutionLabel = new JLabel("Best Child DNA");
        solutionLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        solutionLabel.setBorder(new EmptyBorder(window.getHeight() / 20, window.getHeight() / 2, window.getHeight() / 20, 50));


        labelTop.add(solutionLabel, BorderLayout.LINE_START);
        window.add(labelTop, BorderLayout.PAGE_START);
        labelBottom = new JPanel();
        labelBottom.setLayout(new BorderLayout());

        solutionMetric = new JLabel("Affinity is: " + df.format(gr.getFitness()));
        solutionMetric.setFont(new Font("Times New Roman", Font.BOLD, 30));
        solutionMetric.setBorder(new EmptyBorder(window.getHeight() / 20, window.getHeight() / 2, window.getHeight() / 20, 50));

        labelBottom.add(solutionMetric, BorderLayout.LINE_START);

        window.add(labelTop, BorderLayout.PAGE_START);
        window.add(labelBottom, BorderLayout.PAGE_END);

        panelSolution = new JPanel();

        GridLayout gridFloor = new GridLayout(10, 10, 2, 2);
        panelSolution.setLayout(gridFloor);
        panelSolution.setPreferredSize(new Dimension(10 * STATION_SIZE, 10 * STATION_SIZE));
        panelSolution.setBackground(Color.WHITE);


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                DrawPanel panel = new DrawPanel();
                panel.setPreferredSize(new Dimension(STATION_SIZE, STATION_SIZE));
                solutionFloor[i][j] = panel;
                panelSolution.add(panel);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                DrawPanel panel = new DrawPanel();
                panel.setPreferredSize(new Dimension(STATION_SIZE, STATION_SIZE));
            }
        }
        window.add(panelSolution, BorderLayout.LINE_START);

        this.add(window);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    private void draw(Candidate candidate, boolean isEndSolution) {
        if (candidate != null) {
            int[][] Dna = candidate.getDna();
            for (int i = 0; i < Dna.length; i++) {
                for (int j = 0; j < Dna[i].length; j++) {
                    if (isEndSolution) {
                        DrawPanel panel = solutionFloor[i][j];
                        panel.setDrawable(new Rectangle(0, 0, panel, getColor(Dna[i][j])));
                    }
                }
            }
            if (isEndSolution) {
                solutionMetric.setText("Affinity is: " + df.format(candidate.getFitness()));
            }
        }
    }

    private Color getColor(int DNA) {
        int r, g, b = 0;

        if (DNA == 0) {
            r = 214;
            g = 229;
            b = 18;
        } else if (DNA > 0) {
            r = 170;
            g = 14;
            b = 170;
        } else {
            r = 148;
            g = 76;
            b = 34;

        }
        return new Color(r, g, b);
    }

    private boolean isInterrupted() {
        boolean r;
        for (; ; ) {
            try {
                lock.lock();
                r = interruptFlag;
                break;
            } finally {
                lock.unlock();
            }
        }
        return r;
    }

    @Override
    public void run() {
        this.setVisible(true);
        GeneAlgoInParallel.setsStart(true);
        for (; ; ) {
            try {
                if (isInterrupted()) {
                    draw(g, true);
                }
                draw(GeneAlgoInParallel.best_child, false);
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface Drawable {
        void draw(Graphics g);
    }

    public class DrawPanel extends JPanel {
        private Drawable drawable;

        public DrawPanel() {
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (drawable != null) {
                drawable.draw(g);
            }
        }
    }

    public class Rectangle implements Drawable {
        private final int x;
        private final int y;
        private final JPanel surface;
        private final Color color;

        public Rectangle(int x, int y, JPanel surface, Color color) {
            this.x = x;
            this.y = y;
            this.surface = surface;
            this.color = color;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, surface.getWidth(), surface.getHeight());
        }
    }


}