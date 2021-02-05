package com.github.jojo2357;

import java.util.Random;

public class Main {
    private static final Random rd = new Random(System.currentTimeMillis());

    private static GameBoard gb;

    private static int[][] vals = new int[][]{
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
    };

    /*private static int[][] comparisons = new int[][]{
            { 0, -1, 0, 0, 0},
            { 0, -1, 1, 0, 0, 0},
            { 1, 0, 0, 0, 0},
            { 0, 0, -1, 1, 1, 0},
            { 1, 0, 0, 1, 0},
            { 0, 0, 0, 0, 0, 0},
            { -1, 0, 0, 0, 0},
            { 0, 0, 0, -1, 0, 0},
            { 1, -1, 1, 0, 0},
            { 0, 0, 0, 0, -1, 0},
            { 1, 1, 0, 0, -1},
    };*/

    private static int[][] comparisons = new int[][]{
            { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0},
    };
    //arrow down is positive and arrow right is positive
    /*private static int[][] comparisons = new int[][] {
             {-1,-1, 0, 1, 0},
            { 0, 1, 0,-1, 0, 0},
             { 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
             { 0, 0, 1, 0, 0},
            { 0, 1, 0, 0, 1, 0},
             { 0, 1, 0, 0, 0},
            { 0, 0, 0,-1, 0, 0},
             { 0, 0, 0, 0, 0},
            { 0, 1, 1,-1, 1, 0},
             {-1,-1, 0, 0,-1},
    };*/

    /*private static int[][] comparisons = new int[][]{
            {0,0,0,0,1},
            {1,0,0,0,0,1},
            {0,0,0,0,0},
            {0,0,0,0,0,-1},
            {0,0,-1,0,0},
            {-1,0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0,0},
            {-1,-1,0,-1,0},
            {1,0,0,1,0,0},
            {0,0,0,1,0},
    };*/

    public static void main(String...args){
        int[] windexes = new int[10];
        gb = new GameBoard(vals, comparisons).solve();
        int tries = 0;
        while (!winnableWithOne()){
        //while (!gb.solved()){
            if (tries++ % 1000 == 0)
                System.out.println(tries - 1);
            /*recalculate(windexes, 0);
            for (int i = 0; i < 10; i++) {
                if (windexes[i] == 0)
                    break;

            }*/
            reset();
            for (int i = 0; i < 10; i++){
                int windex = rd.nextInt(60);
                int x, y;
                x = 2 * (windex / 11);
                x = x + (windex % 11 >= 5 ? 1 : 0);
                y = windex % 11 - (windex % 11 >= 5 ? 5 : 0);
                comparisons[x][y] = rd.nextInt(2) * 2 - 1;//0 => -1 and 1 => 1
            }
            try {
                //gb = new GameBoard(vals, comparisons).solve();
            }catch (Exception ignored){//IOOBE

            }
        }
        System.out.println(gb);
        System.out.println("got it in " + tries + " tries");
        /*winnableWithOne();
        GameBoard gb = new GameBoard(vals, comparisons).solve();
        System.out.println(gb.solve());
        if (gb.solved())
            System.out.println("Success");*/
    }

    /*private static void recalculate(int[] windexes, int i) {
        if (i >= 10)
            throw new RuntimeException("GG im done");
        windexes[i]++;
        if (windexes[i] >= 61 - i) {
            //windexes[i] = windexes[i + 1] + 2;
            recalculate(windexes, i + 1);
            windexes[i] = windexes[i + 1] + 1;
        }else if (i > 0 && windexes[i] >= windexes[i - 1]) {
            windexes[i] = windexes[i - 1] - 1;
            recalculate(windexes, i + 1);
        }
    }*/

    private static void reset(){
        comparisons = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
        };
    }

    private static boolean winnableWithNone(){
        gb = new GameBoard(vals, comparisons);
        return gb.solve().solved();
    }

    private static boolean winnableWithOne(){
        boolean out = false;
        for (int i = 0; i < 36; i++){
            for (int j = 1; j < 7; j++) {
                int[][] board = new int[6][6];
                for (int a = 0; a < 6; a++)
                    System.arraycopy(vals[a], 0, board[a], 0, 6);
                board[i / 6][i % 6] = j;
                try {
                    gb = new GameBoard(board, comparisons).solve();
                    if (gb.solved()) {
                        System.out.println("((" + (i % 6) + ", " + (i / 6) + "), " + j + ")");
                        out = true;
                    }
                }catch(Exception ignored){

                }
            }
        }
        return out;
    }

    /*
        private static boolean winnableWithOne(){
        for (int i = 0; i < 36; i++){
            for (int j = 1; j < 7; j++) {
                int[][] board = new int[6][6];
                for (int a = 0; a < 6; a++)
                    System.arraycopy(vals[a], 0, board[a], 0, 6);
                board[i / 6][i % 6] = j;
                try {
                    GameBoard gb = new GameBoard(board, comparisons).solve();
                    if (gb.solved()) System.out.println("Success (" + i + ", " + (j) + ")");
                }catch(Exception ignored){

                }
            }
        }
    }
     */
}
