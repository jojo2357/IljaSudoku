package com.github.jojo2357;

public class Main {
    private static int[][] vals = new int[][]{
            {0, 0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 6},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
    };

    //arrow down is positive and arrow right is positive
    private static int[][] comparisons = new int[][]{
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
    };

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
        GameBoard gb = new GameBoard(vals, comparisons).solve();
        System.out.println(gb.solve());
    }
}
