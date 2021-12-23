/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flipgame2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;

/**
 *
 * @author john1
 */
public class FlipGame extends javax.swing.JFrame {
    public static taskBoard taskBoard = null;
    public static BitSet bitz = new BitSet(9);
    private int count = 0;
    
    public static BitSet random_bitset(){
        BitSet bit = new BitSet();
        Random rand = new Random();
        for(int i = 0;i<9;i++){
            if(rand.nextBoolean())
                bit.flip(i);
        }
        return bit;
    }
    
    public void Color_buttons(BitSet bitz){
        for(int i = 0 ; i < 9 ; i++ ){
            if(i == 0){
                if(bitz.get(i))
                    button1.setBackground(Color.black);
            }
            if(i == 1){
                if(bitz.get(i))
                    button2.setBackground(Color.black);
            }
            if(i == 2){
                if(bitz.get(i))
                    button3.setBackground(Color.black);
            }
            if(i == 3){
                if(bitz.get(i))
                    button4.setBackground(Color.black);
            }
            if(i == 4){
                if(bitz.get(i))
                    button5.setBackground(Color.black);
            }
            if(i == 5){
                if(bitz.get(i))
                    button6.setBackground(Color.black);
            }
            if(i == 6){
                if(bitz.get(i))
                    button7.setBackground(Color.black);
            }
            if(i == 7){
                if(bitz.get(i))
                    button8.setBackground(Color.black);
            }
            if(i == 8){
                if(bitz.get(i))
                    button9.setBackground(Color.black);
            }
        }
    }
    public FlipGame() {
        initComponents();
        bitz = random_bitset();
        Color_buttons(bitz);
        button1.addActionListener(e -> {
            if(bitz.get(0))
                button1.setBackground(Color.WHITE);
            else
                button1.setBackground(Color.BLACK);
            bitz.flip(0);
            if(bitz.get(1))
                button2.setBackground(Color.WHITE);
            else
                button2.setBackground(Color.BLACK);
            bitz.flip(1);
            if(bitz.get(3))
                button4.setBackground(Color.WHITE);
            else
                button4.setBackground(Color.BLACK);
            bitz.flip(3);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button2.addActionListener(e -> {
            if(bitz.get(1))
                button2.setBackground(Color.WHITE);
            else
                button2.setBackground(Color.BLACK);
            bitz.flip(1);
            if(bitz.get(0))
                button1.setBackground(Color.WHITE);
            else
                button1.setBackground(Color.BLACK);
            bitz.flip(0);
            if(bitz.get(2))
                button3.setBackground(Color.WHITE);
            else
                button3.setBackground(Color.BLACK);
            bitz.flip(2);
            if(bitz.get(4))
                button5.setBackground(Color.WHITE);
            else
                button5.setBackground(Color.BLACK);
            bitz.flip(4);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
    button3.addActionListener(e -> {
            if(bitz.get(2))
                button3.setBackground(Color.WHITE);
            else
                button3.setBackground(Color.BLACK);
            bitz.flip(2);
            if(bitz.get(1))
                button2.setBackground(Color.WHITE);
            else
                button2.setBackground(Color.BLACK);
            bitz.flip(1);
            if(bitz.get(5))
                button6.setBackground(Color.WHITE);
            else
                button6.setBackground(Color.BLACK);
            bitz.flip(5);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button4.addActionListener(e -> {
            if(bitz.get(3))
                button4.setBackground(Color.WHITE);
            else
                button4.setBackground(Color.BLACK);
            bitz.flip(3);
            if(bitz.get(0))
                button1.setBackground(Color.WHITE);
            else
                button1.setBackground(Color.BLACK);
            bitz.flip(0);
            if(bitz.get(4))
                button5.setBackground(Color.WHITE);
            else
                button5.setBackground(Color.BLACK);
            bitz.flip(4);
            if(bitz.get(6))
                button7.setBackground(Color.WHITE);
            else
                button7.setBackground(Color.BLACK);
            bitz.flip(6);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button5.addActionListener(e -> {
            if(bitz.get(4))
                button5.setBackground(Color.WHITE);
            else
                button5.setBackground(Color.BLACK);
            bitz.flip(4);
            if(bitz.get(1))
                button2.setBackground(Color.WHITE);
            else
                button2.setBackground(Color.BLACK);
            bitz.flip(1);
            if(bitz.get(5))
                button6.setBackground(Color.WHITE);
            else
                button6.setBackground(Color.BLACK);
            bitz.flip(5);
            if(bitz.get(7))
                button8.setBackground(Color.WHITE);
            else
                button8.setBackground(Color.BLACK);
            bitz.flip(7);
            if(bitz.get(3))
                button4.setBackground(Color.WHITE);
            else
                button4.setBackground(Color.BLACK);
            bitz.flip(3);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button6.addActionListener(e -> {
            if(bitz.get(5))
                button6.setBackground(Color.WHITE);
            else
                button6.setBackground(Color.BLACK);
            bitz.flip(5);
            if(bitz.get(4))
                button5.setBackground(Color.WHITE);
            else
                button5.setBackground(Color.BLACK);
            bitz.flip(4);
            if(bitz.get(2))
                button3.setBackground(Color.WHITE);
            else
                button3.setBackground(Color.BLACK);
            bitz.flip(2);
            if(bitz.get(8))
                button9.setBackground(Color.WHITE);
            else
                button9.setBackground(Color.BLACK);
            bitz.flip(8);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });

        button7.addActionListener(e -> {
            if(bitz.get(6))
                button7.setBackground(Color.WHITE);
            else
                button7.setBackground(Color.BLACK);
            bitz.flip(6);
            if(bitz.get(3))
                button4.setBackground(Color.WHITE);
            else
                button4.setBackground(Color.BLACK);
            bitz.flip(3);
            if(bitz.get(7))
                button8.setBackground(Color.WHITE);
            else
                button8.setBackground(Color.BLACK);
            bitz.flip(7);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button8.addActionListener(e -> {
            if(bitz.get(7))
                button8.setBackground(Color.WHITE);
            else
                button8.setBackground(Color.BLACK);
            bitz.flip(7);
            if(bitz.get(6))
                button7.setBackground(Color.WHITE);
            else
                button7.setBackground(Color.BLACK);
            bitz.flip(6);
            if(bitz.get(4))
                button5.setBackground(Color.WHITE);
            else
                button5.setBackground(Color.BLACK);
            bitz.flip(4);
            if(bitz.get(8))
                button9.setBackground(Color.WHITE);
            else
                button9.setBackground(Color.BLACK);
            bitz.flip(8);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        button9.addActionListener(e -> {
            if(bitz.get(8))
                button9.setBackground(Color.WHITE);
            else
                button9.setBackground(Color.BLACK);
            bitz.flip(8);
            if(bitz.get(5))
                button6.setBackground(Color.WHITE);
            else
                button6.setBackground(Color.BLACK);
            bitz.flip(5);
            if(bitz.get(7))
                button8.setBackground(Color.WHITE);
            else
                button8.setBackground(Color.BLACK);
            bitz.flip(7);
            count++;
            jFormattedTextField1.setText("Count : " + count);
            System.out.println("bits : "+ bitz.toString()+ " length : " +bitz.length());
        });
        AutoSolve.addActionListener(e -> {
            taskBoard = new taskBoard();
            ThreadedEchoServer serve = new ThreadedEchoServer(bitz);
            serve.run();
            List<Integer> moves = taskBoard.solution;
            System.out.println("moves to solve the game : " + moves.toString());




            //List<Thread> threads = new ArrayList<Thread>();
            /*ArrayList<MyRecursiveAction> subtasks = new ArrayList<>();
            if(!bitz.isEmpty()){
                if(Solution.getFound())
                    Solution.setFound(false);
                for (int l = 0; l < 9; l++) {
                    ArrayList<Integer> moves = new ArrayList<>();
                    moves.add(l);// adds new move to list of moves
                    BitSet temp = flipBitz(l); //make it so that it didn't repeat previose moves
                    subtasks.add(new MyRecursiveAction(temp, moves)); // makes new child with new bitset cording to the move made.
                }
                ForkJoinTask.invokeAll(subtasks);

                synchronized (Solution) {
                    List<Integer> moves = Solution.getMoves();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("moves to solve the game : " + moves.toString());
                }
             */   //make it so that it solves the actual puzzle visually.
            for(int i = 0; i < taskBoard.solution.size(); i++ ){
               int biting = taskBoard.solution.get(i);
                if(biting == 0) {
                    button1.setBackground(Color.RED);
                }if(biting == 1) {
                    button2.setBackground(Color.RED);
                }if(biting == 2) {
                    button3.setBackground(Color.RED);
                }if(biting == 3) {
                    button4.setBackground(Color.RED);
                }if(biting == 4) {
                    button5.setBackground(Color.RED);
                }if(biting == 5) {
                    button6.setBackground(Color.RED);
                }if(biting == 6) {
                    button7.setBackground(Color.RED);
                }if(biting == 7) {
                    button8.setBackground(Color.RED);
                }if(biting == 8) {
                    button9.setBackground(Color.RED);
                }
            }
       // }
           System.out.println("solution found");
        });
    }
    private BitSet flipBitz(int biting){ //test and make sure the flipsing of the n=bits works.
        BitSet bit = (BitSet)bitz.clone();
        ///you didn't add a feature that would make it not repeat the same move again.
        if(biting == 0) {
            bit.flip(0);
            bit.flip(1);
            bit.flip(3);
        }
        if(biting == 1) {
            bit.flip(0);
            bit.flip(1);
            bit.flip(2);
            bit.flip(4);
        }
        if(biting == 2) {
            bit.flip(1);
            bit.flip(2);
            bit.flip(5);
        }
        if(biting == 3) {
            bit.flip(0);
            bit.flip(3);
            bit.flip(4);
            bit.flip(6);
        }
        if(biting == 4) {
            bit.flip(1);
            bit.flip(3);
            bit.flip(4);
            bit.flip(5);
            bit.flip(7);
        }
        if(biting == 5) {
            bit.flip(2);
            bit.flip(4);
            bit.flip(5);
            bit.flip(8);
        }
        if(biting == 6) {
            bit.flip(3);
            bit.flip(6);
            bit.flip(7);
        }
        if(biting == 7) {
            bit.flip(4);
            bit.flip(6);
            bit.flip(7);
            bit.flip(8);
        }if(biting == 8) {
            bit.flip(5);
            bit.flip(7);
            bit.flip(8);
        }
        return bit;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button9 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        button1 = new javax.swing.JButton();
        button2 = new javax.swing.JButton();
        AutoSolve = new javax.swing.JButton();
        button3 = new javax.swing.JButton();
        button4 = new javax.swing.JButton();
        button5 = new javax.swing.JButton();
        button6 = new javax.swing.JButton();
        button7 = new javax.swing.JButton();
        button8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button9.setMaximumSize(new java.awt.Dimension(72, 72));
        button9.setMinimumSize(new java.awt.Dimension(72, 72));
        button9.setPreferredSize(new java.awt.Dimension(72, 72));

        jFormattedTextField1.setText("Count 0");

        button1.setMaximumSize(new java.awt.Dimension(72, 72));
        button1.setMinimumSize(new java.awt.Dimension(72, 72));
        button1.setPreferredSize(new java.awt.Dimension(72, 72));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setMaximumSize(new java.awt.Dimension(72, 72));
        button2.setMinimumSize(new java.awt.Dimension(72, 72));
        button2.setPreferredSize(new java.awt.Dimension(72, 72));

        AutoSolve.setText("AutoSolve");

        button3.setMaximumSize(new java.awt.Dimension(72, 72));
        button3.setMinimumSize(new java.awt.Dimension(72, 72));
        button3.setPreferredSize(new java.awt.Dimension(72, 72));

        button4.setMaximumSize(new java.awt.Dimension(72, 72));
        button4.setMinimumSize(new java.awt.Dimension(72, 72));
        button4.setPreferredSize(new java.awt.Dimension(72, 72));

        button5.setMaximumSize(new java.awt.Dimension(72, 72));
        button5.setMinimumSize(new java.awt.Dimension(72, 72));
        button5.setPreferredSize(new java.awt.Dimension(72, 72));

        button6.setMaximumSize(new java.awt.Dimension(72, 72));
        button6.setMinimumSize(new java.awt.Dimension(72, 72));
        button6.setPreferredSize(new java.awt.Dimension(72, 72));

        button7.setMaximumSize(new java.awt.Dimension(72, 72));
        button7.setMinimumSize(new java.awt.Dimension(72, 72));
        button7.setPreferredSize(new java.awt.Dimension(72, 72));

        button8.setMaximumSize(new java.awt.Dimension(72, 72));
        button8.setMinimumSize(new java.awt.Dimension(72, 72));
        button8.setPreferredSize(new java.awt.Dimension(72, 72));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AutoSolve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AutoSolve)
                        .addGap(21, 21, 21)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(button7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FlipGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FlipGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FlipGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FlipGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FlipGame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AutoSolve;
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JButton button3;
    private javax.swing.JButton button4;
    private javax.swing.JButton button5;
    private javax.swing.JButton button6;
    private javax.swing.JButton button7;
    private javax.swing.JButton button8;
    private javax.swing.JButton button9;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    // End of variables declaration//GEN-END:variables
}
