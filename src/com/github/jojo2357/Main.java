package com.github.jojo2357;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Random rd = new Random(System.currentTimeMillis());
    private static GameBoard gb;
    private static Difficulties difficulty = Difficulties.EASY_ONE_NUM;
    private static int gamesToMake = 10;
    /*private static int[][] vals = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };*/
    private static int recordDepth = 0;
    public static final int boardSize = 25;
    //private static int[][] vals = new int[][]{ };
    private static int[][] comparisons = new int[boardSize * 2 - 1][boardSize];
    /*private static int[][] comparisons = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
    };*/
    private static int[][] vals = new int[][]{
            {0,1,0,3,0,0},
            {4,3,0,0,0,0},
            {0,0,4,0,0,0},
            {6,5,0,0,0,0},
            {0,6,0,0,0,0},
            {0,0,0,0,1,0},
    };
    /*private static int[][] vals = new int[][]{
        {0,0,0,0,0,0},
        {0,3,0,5,4,6},
        {0,5,0,6,3,4},
        {4,6,3,2,5,1},
        {0,2,0,4,0,0},
        {0,0,0,0,0,0},
    };*/

    /*private static int[][] comparisons = new int[][]{
            {0, 0, 0, 0, 0},
            {0, -1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {1, -1, 0, 0, 1},
            {0, 0, -1, 0, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {-1, 0, 1, -1, -1, 0},
            {0, -1, 0, -1, 0},
    };*/

    /*private static int[][] comparisons = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, -1, -1, 0, 0, 0, 0, 0, 0},
            {0, -1, -1, 0, 0, 1, 0, 0},
            {0, -1, -1, 0, 0, 0, 0, 0, 0},
            {0, -1, -1, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, -1, 0},
            {0, 0, 0, 0, -1, -1, -1, 0, 0},
            {0, -1, -1, 1, 1, 1, 0, 0},
            {0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 1, -1, -1, -1, 0},
            {0, 0, 0, 1, -1, 1, 0, 0},
            {1, -1, 1, 0, 1, 1, 1, -1, -1},
            {0, 1, 1, 1, -1, -1, -1, 0},
    };*/

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

    private static int[] pickdex = new int[boardSize * boardSize];

    public static void main(String... args) throws InterruptedException {
        int total = 0;
        for (int[] line : comparisons)
            for (int num : line)
                total = num != 0 ? total + 1 : total;
        System.out.println(total);
        int[] windexes = new int[10];
        /*reset();
        gb = new GameBoard(vals, comparisons);
        gb.solve(true);
        System.out.println(gb);*/
        initVals();
        //System.out.println(gb.solve(true));
        //gb.outputBoard("gg", true);
        //return;
        int tries = 0;
        //while (!winnableWithOne()){
        //initVals();
        System.out.println("Why u do this...");
        try{
            long lastSolve = System.currentTimeMillis();
            int f = 0;
            while (true) {
                System.out.println("Solving");
                pickdex = new int[boardSize * boardSize];
                Arrays.fill(pickdex, -1);
                for (int i = 0; i < boardSize * boardSize; i++){
                    int filldex = rd.nextInt(boardSize * boardSize);
                    while (pickdex[filldex] != -1)
                        filldex = rd.nextInt(boardSize * boardSize);
                    pickdex[filldex] = i;
                }
                if (!recursiveGuessingNumbers(new int[boardSize][boardSize], new int[boardSize * 2 - 1][boardSize], new GameBoard(new int[boardSize][boardSize], new int[2 * boardSize - 1][boardSize]))) {
                    System.out.println("Something went wrong");
                }
                System.out.println(System.currentTimeMillis() - lastSolve);
                int[][] answerSheet = new int[boardSize][boardSize];
                for (int i = 0; i < boardSize*boardSize; i++)
                    answerSheet[i/boardSize][i%boardSize] = gb.rows[i/boardSize].squares[i%boardSize].val == null ? 0 : gb.rows[i/boardSize].squares[i%boardSize].val + 1;
                for (int i = 0; i < boardSize * boardSize; i++) {
                    if (i % boardSize != boardSize - 1) {
                        try {
                            comparisons[2 * (i / boardSize)][i % boardSize] = Integer.compare(answerSheet[i / boardSize][i % boardSize], answerSheet[i / boardSize][(i % boardSize) + 1]);
                        } catch (Exception e) {
                            //System.out.println(i);
                        }
                    }
                    if (i / boardSize != boardSize - 1) {
                        comparisons[2 * (i / boardSize) + 1][i % boardSize] = Integer.compare(answerSheet[i / boardSize][i % boardSize], answerSheet[(i / boardSize) + 1][i % boardSize]);
                    }
                }
                //System.out.println(gb);
                gb = new GameBoard(answerSheet, comparisons).solve();
                //System.out.println(gb);
                int[][] refinedAnswers = gb.getBoardVals();
                int[][] refinedCompares = gb.getMandatoryCompares();
                gb = new GameBoard(refinedAnswers, refinedCompares);
                int rngGuesses = 0;
                GameBoard nb = gb;
                do {
                    GameBoard answerRefining;
                    int misses = 0;
                    do {
                        int dartboardOfLove = rd.nextInt(boardSize * boardSize);
                        while (refinedAnswers[dartboardOfLove / boardSize][dartboardOfLove % boardSize] == 0)
                            dartboardOfLove = rd.nextInt(boardSize * boardSize);
                        refinedAnswers[dartboardOfLove / boardSize][dartboardOfLove % boardSize] = 0;
                        answerRefining = new GameBoard(refinedAnswers, comparisons);
                        if (!answerRefining.solve().solved()){
                            refinedAnswers[dartboardOfLove / boardSize][dartboardOfLove % boardSize] = answerSheet[dartboardOfLove / boardSize][dartboardOfLove % boardSize];
                            misses++;
                        }else
                            misses = 0;
                    } while (answerRefining.countSolved() <= difficulty.DIFFICULTY && misses < boardSize * boardSize);
                    if (new GameBoard(refinedAnswers, refinedCompares).solve().solved()) {
                        int failedVibeChecks = 0, vibeChecks = (int)Math.pow(boardSize, 2);
                        System.out.println("Starting checks on " + f);
                        nb = gb;
                        int removals = 0;
                        while (failedVibeChecks < vibeChecks && nb.difficultyIndex < difficulty.DIFFICULTY) {
                            //System.out.println("Continuing because diff of " + gb.difficultyIndex);
                            int guessGive;
                            do {
                                guessGive = rd.nextInt(2 * (boardSize - 1) * boardSize);
                            } while (refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] == 0);
                            refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] = 0;
                            nb = new GameBoard(refinedAnswers, refinedCompares).solve();
                            if (!nb.solved() || nb.illegallySolved()) {
                                refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] = comparisons[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))];
                                //gb = new GameBoard(refinedAnswers, refinedCompares).solve();
                                failedVibeChecks++;
                            }else {
                                System.out.println("Removing " + (++removals) + " comparator " + nb.difficultyIndex);
                                failedVibeChecks = 0;
                            }
                        }
                        gb = new GameBoard(refinedAnswers, refinedCompares).solve();
                    }else
                        rngGuesses++;
                }while ((!gb.solved() || gb.illegallySolved()) && rngGuesses < boardSize * boardSize);
                if (gb.solved()) {
                    System.out.print("Exporting");
                    f++;
                    gb.outputBoard(difficulty.NAME + "_Unsolved_" + boardSize + "x" + boardSize + "_" + f + "_diffindex_" + gb.difficultyIndex, false);
                    System.out.print("...");
                    gb.outputBoard(difficulty.NAME + "_Solved_" + boardSize + "x" + boardSize + "_" + f + "_diffindex_" + gb.difficultyIndex, true);
                    System.out.println(" (" + gb.difficultyIndex + ")");
                } else {
                    System.out.println("Throwing it out");
                    GameBoard temp = new GameBoard(answerSheet, comparisons);
                    for (Row row : temp.rows)
                        System.out.println(row);
                }
                //gb.outputBoard("Unsolved_BIGCHUNGUS_" + gb.difficultyIndex, false);
                //gb.outputBoard("Solved_BIGCHUNGUS_" + gb.difficultyIndex, true);
                lastSolve = System.currentTimeMillis();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Thread.sleep(Long.MAX_VALUE);
        for (int f = 0; f < gamesToMake; ) {
            try {
                comparisons = new int[boardSize * 2 - 1][boardSize];
                int[][] answerSheet = new int[boardSize][boardSize];
                boolean[][][] acceptances = new boolean[boardSize][boardSize][boardSize];
                for (int i = 0; i < boardSize * boardSize; i++) {
                    acceptances[i / vals[0].length][i % vals[0].length] = new boolean[boardSize];
                    for (int k = 0; k < boardSize; k++)
                        acceptances[i / vals[0].length][i % vals[0].length][k] = true;
                }
                GameBoard lastBoard = gb;
                gb = new GameBoard(answerSheet, comparisons);
                for (int changedex = 0; /*changedex < boardSize * boardSize && */ !(gb.solved() || gb.illegallySolved()); /*changedex++*/ changedex = rd.nextInt(boardSize * boardSize)){
                    if (answerSheet[changedex / boardSize][changedex % boardSize] != 0)
                        continue;
                    int randomGuess;
                    while (!acceptances[changedex / boardSize][changedex % boardSize][randomGuess = rd.nextInt(boardSize)]);
                    System.out.println("Placing a " + (randomGuess + 1) + " at (" + (1 + (changedex % boardSize)) + ", " + (1 + changedex / boardSize) + ")");
                    answerSheet[changedex / boardSize][changedex % boardSize] = randomGuess + 1;
                    gb = new GameBoard(answerSheet, comparisons);
                    gb.solve(true);
                    if (gb.illegallySolved()){
                        System.out.println("hrlp");
                    }
                    for (int i = 0; i < boardSize * boardSize; i++)
                        if (gb.rows[i / boardSize].squares[i % boardSize].val != null && answerSheet[i / boardSize][i % boardSize] == 0)
                            answerSheet[i / boardSize][i % boardSize] = gb.rows[i / boardSize].squares[i % boardSize].val + 1;
                    for (int i = 0; i < boardSize * boardSize; i++)
                        System.arraycopy(gb.rows[i / boardSize].squares[i % boardSize].acceptedNumbers, 0, acceptances[i / boardSize][i % boardSize], 0, boardSize);
                    try {
                        lastBoard = gb;
                    } catch (Exception e) {
                        System.out.println("Unsolveable");
                        break;
                    }
                    if (gb.solved()) {
                        //System.out.println("DONE");
                    }
                    //System.out.println("==================");
                    //System.out.println(gb.solved());
                    //System.out.println(gb);
                    /*for (int i = 0; i < 150/*difficulty.COMPARATORS * Math.pow(boardSize / 6.0, 2)*  /; i++) {
                        int windex = rd.nextInt(((vals[0].length) * (vals.length - 1)) + ((vals[0].length - 1) * (vals.length)));
                        int x, y;
                        x = 2 * (windex / ((vals[0].length) + (vals.length - 1)));
                        x = x + (windex % ((vals[0].length) + (vals.length - 1)) >= vals[0].length - 1 ? 1 : 0);
                        y = windex % ((vals[0].length) + (vals.length - 1)) - (windex % ((vals[0].length) + (vals.length - 1)) >= vals[0].length - 1 ? vals[0].length - 1 : 0);
                        comparisons[x][y] = rd.nextInt(2) * 2 - 1;//0 => -1 and 1 => 1
                    }*/
                    /*try {
                        gb = new GameBoard(vals, comparisons).solve();
                    } catch (Exception ignored) {//IOOBE
                        //ignored.printStackTrace();
                    }*/
                }
                if (gb.illegallySolved() || !gb.solved()) {
                    /*System.out.println(gb.locateIllegal());
                    System.out.println(gb);
                    System.out.println(lastBoard.locateIllegal());
                    System.out.println(lastBoard);*/
                    System.out.println("Throwing it out because " + gb.locateIllegal());
                    continue;
                }
                for (int i = 0; i < boardSize * boardSize; i++) {
                    if (i % boardSize != boardSize - 1) {
                        try {
                            comparisons[2 * (i / boardSize)][i % boardSize] = Integer.compare(answerSheet[i / boardSize][i % boardSize], answerSheet[i / boardSize][(i % boardSize) + 1]);
                        } catch (Exception e) {
                            //System.out.println(i);
                        }
                    }
                    if (i / boardSize != boardSize - 1) {
                        comparisons[2 * (i / boardSize) + 1][i % boardSize] = Integer.compare(answerSheet[i / boardSize][i % boardSize], answerSheet[(i / boardSize) + 1][i % boardSize]);
                    }
                }
                //System.out.println(gb);
                gb = new GameBoard(answerSheet, comparisons).solve();
                //System.out.println(gb);
                int[][] refinedAnswers = new int[boardSize][boardSize];
                int[][] refinedCompares = new int[boardSize * 2 - 1][boardSize];
                gb = new GameBoard(refinedAnswers, refinedCompares);
                int rngGuesses = 0;
                while (!gb.solved() && !gb.illegallySolved() && rngGuesses < boardSize * boardSize) {
                    refinedAnswers = new int[boardSize][boardSize];
                    for (int counter = 0; counter < difficulty.GIVENS; counter++) {
                        int guessGive;
                        do {
                            guessGive = rd.nextInt(boardSize * boardSize);
                        } while (refinedAnswers[guessGive / boardSize][guessGive % boardSize] != 0);
                        refinedAnswers[guessGive / boardSize][guessGive % boardSize] = answerSheet[guessGive / boardSize][guessGive % boardSize];
                    }
                    for (int i = 0; i < (boardSize * 2 - 1) * boardSize; i++){
                        refinedCompares[i / boardSize][i % boardSize] = comparisons[i / boardSize][i % boardSize];
                    }
                    if (new GameBoard(refinedAnswers, refinedCompares).solve().solved()) {
                        int failedVibeChecks = 0, vibeChecks = (int)Math.pow(boardSize, 3);
                        System.out.println("Starting checks on " + f);
                        GameBoard nb = gb;
                        while (failedVibeChecks < vibeChecks && nb.difficultyIndex < difficulty.DIFFICULTY) {
                            //System.out.println("Continuing because diff of " + gb.difficultyIndex);
                            int guessGive;
                            do {
                                guessGive = rd.nextInt(2 * (boardSize - 1) * boardSize);
                            } while (refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] == 0);
                            refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] = 0;
                            nb = new GameBoard(refinedAnswers, refinedCompares).solve();
                            if (!nb.solved()) {
                                refinedCompares[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))] = comparisons[(guessGive % (boardSize * 2 - 1)) / (boardSize - 1 + ((guessGive % (boardSize * 2 - 1)) / boardSize)) + 2 * (guessGive / (boardSize * 2 - 1))][(guessGive % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (guessGive % (boardSize * 2 - 1)) / (boardSize - 1))];
                                //gb = new GameBoard(refinedAnswers, refinedCompares).solve();
                                failedVibeChecks++;
                            }else {
                                //System.out.println(nb.difficultyIndex);
                                failedVibeChecks = 0;
                            }
                        }
                        gb = new GameBoard(refinedAnswers, refinedCompares).solve();
                    }else
                        rngGuesses++;
                }
                if (gb.solved()) {
                    System.out.println("Exporting");
                    f++;
                    gb.outputBoard(difficulty.NAME + "_Unsolved_" + f + "_diffindex_" + gb.difficultyIndex, false);
                    gb.outputBoard(difficulty.NAME + "_Solved_" + f + "_diffindex_" + gb.difficultyIndex, true);
                } else {
                    System.out.println("Throwing it out");
                }
                /*try {
                    gb.solve();
                    //if (gb.solved())
                    System.out.println(gb.solved());
                    System.out.println(gb);
                    gb.outputBoard(difficulty.NAME + "_Unsolved_" + f + "_diffindex_" + gb.difficultyIndex, false);
                    gb.outputBoard(difficulty.NAME + "_Solved_" + f + "_diffindex_" + gb.difficultyIndex, true);
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.println("Failed to export");
                    e.printStackTrace();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Thread.sleep(5000);
        System.out.println("got it in " + tries + " tries");
        /*winnableWithOne();
        GameBoard gb = new GameBoard(vals, comparisons);//.solve();
        System.out.println(gb.solve(true));
        if (gb.solved())
            System.out.println("Success");
        System.out.println(gb.difficultyIndex);*/
    }

    private static boolean recursiveGuessingNumbers(int[][] vals, int[][] comparators, GameBoard localBoard){
        int count = 0;
        for (int i = 0; i < boardSize * boardSize; i++)
            if (vals[i / boardSize][i % boardSize] != 0)
                count++;
        if (count > recordDepth) {
            System.out.println("At depth " + count);
            recordDepth = count;
        }
        int windex = -1;
        int minNum = boardSize + 1;
        for (int i = 0; i < boardSize * boardSize; i++){
            if (localBoard.rows[i / boardSize].squares[i % boardSize].val == null && localBoard.rows[i / boardSize].squares[i % boardSize].countPossibilities() < minNum){
                minNum = localBoard.rows[i / boardSize].squares[i % boardSize].countPossibilities();
                windex = i;
            }
        }
        /*while (vals[windex / boardSize][windex % boardSize] != 0)
            windex = rd.nextInt(boardSize * boardSize);*/
        boolean[] permisibles = new boolean[boardSize];
        System.arraycopy(localBoard.rows[windex / boardSize].squares[windex % boardSize].acceptedNumbers, 0, permisibles, 0, boardSize);
        int startingNum, currentGuess;
        startingNum = currentGuess = rd.nextInt(boardSize) + 1;
        boolean starting = true;
        do {
            if (!starting && startingNum == (currentGuess = (currentGuess % boardSize) + 1))
                return false;
            starting = false;
            if (permisibles[currentGuess - 1]){
                //System.out.println("Trying a " + currentGuess + " at (" + ((windex % boardSize) + 1) + ", " + (1 + (windex / boardSize)) + ")");
                vals[windex / boardSize][windex % boardSize] = currentGuess;
                localBoard = new GameBoard(vals, comparators).solve();
                if (localBoard.solved()) {
                    gb = localBoard;
                    return true;
                }
            }
        } while(!permisibles[currentGuess - 1] || localBoard.illegallySolved() || !recursiveGuessingNumbers(localBoard.getBoardVals(), comparators, localBoard));
        return true;
    }

    private static boolean recursiveGuessingComparators(int[][] comparators, GameBoard localBoard){
        int count = 0;
        for (int i = 0; i < 2 * (boardSize - 1) * boardSize; i++)
            if (comparators[(i % (boardSize * 2 - 1)) / (boardSize - 1 + ((i % (boardSize * 2 - 1)) / boardSize)) + 2 * (i / (boardSize * 2 - 1))][(i % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (i % (boardSize * 2 - 1)) / (boardSize - 1))] != 0)
                count++;
        if (count >= Math.pow(2 * (boardSize - 1) * boardSize, 1.0/2.0)){
            System.out.println("Beginning numeric recursion...");
            recordDepth = 0;
            return recursiveGuessingNumbers(localBoard.getBoardVals(), comparators, localBoard);
//            int guessCation = 0;
//            int[][] answers = localBoard.getBoardVals();
//            while (answers[guessCation / boardSize][guessCation % boardSize] == 0)
//                guessCation = rd.nextInt(boardSize * boardSize);
//            boolean[] accepts = new boolean[boardSize];
//            System.arraycopy(localBoard.rows[guessCation / boardSize].squares[guessCation % boardSize].acceptedNumbers, 0, accepts, 0, boardSize);
//            int start, current;
//            start = current = rd.nextInt(boardSize) + 1;
//            boolean firstTry = true;
//            do {
//                if (!firstTry && (current = (current % boardSize) + 1) == start)
//                    return false;
//                firstTry = false;
//                if (accepts[current - 1]){
//                    answers[guessCation / boardSize][guessCation % boardSize] = current;
//                    localBoard = new GameBoard(answers, comparators).solve();
//                    if (localBoard.solved())
//                        return true;
//                }
//            }while (!accepts[current - 1] || (localBoard.illegallySolved() || !recursiveGuessingComparators(comparators, localBoard)));
//            return true;
        }
        if (count > recordDepth) {
            System.out.println("At depth " + count);
            recordDepth = count;
        }
        int localGuessPlace = 0;
        while (comparators[(localGuessPlace % (boardSize * 2 - 1)) / (boardSize - 1 + ((localGuessPlace % (boardSize * 2 - 1)) / boardSize)) + 2 * (localGuessPlace / (boardSize * 2 - 1))][(localGuessPlace % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (localGuessPlace % (boardSize * 2 - 1)) / (boardSize - 1))] != 0)
            localGuessPlace = rd.nextInt(2 * boardSize * (boardSize - 1));
        //boolean[] allowals = localBoard.rows[localGuessPlace / boardSize].squares[localGuessPlace % boardSize].acceptedNumbers;
        int startingGuess, currentGuess;
        startingGuess = currentGuess = rd.nextInt(2) * 2 - 1;
        boolean madeChange;
        boolean firstTry = true;
        int[][] nums = localBoard.getBoardVals();
        do {
            if (!firstTry && (currentGuess = currentGuess == - 1 ? 1 : -1) == startingGuess)
                return false;
            firstTry = false;
            //if (madeChange = allowals[currentGuess]){
                comparators[(localGuessPlace % (boardSize * 2 - 1)) / (boardSize - 1 + ((localGuessPlace % (boardSize * 2 - 1)) / boardSize)) + 2 * (localGuessPlace / (boardSize * 2 - 1))][(localGuessPlace % (boardSize * 2 - 1)) - (boardSize - 1) * Math.min(1, (localGuessPlace % (boardSize * 2 - 1)) / (boardSize - 1))] = currentGuess;
                localBoard = new GameBoard(nums, comparators).solve();
                if (localBoard.solved()) {
                    gb = localBoard;
                    return true;
                }
            //}else {
                //System.out.print("");
            //}

        } while (/*!madeChange || */localBoard.illegallySolved() || !recursiveGuessingComparators(localBoard.getMandatoryCompares(), localBoard));
        return true;
    }

    private static void initVals() {
        vals = new int[boardSize][boardSize];
        comparisons = new int[boardSize * 2 - 1][boardSize];
    }

    /*private static int[][] comparisons = new int[][]{
            { -1, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0},
            { -1, 1, 0, 0, -1},
            { 0, 0, 0, 1, 0, 0},
            { 1, 0, 0, 1, 0},
            { 0, 0, 1, 0, 0, 0},
            { 1, 0, 0, -1, 0},
            { 1, 0, 0, 0, 0, 0},
            { 1, 0, -1, 1, 0},
            { 0, 1, 0, 0, -1, 0},
            { 0, 1, 0, 0, 0},
    };*/
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

    private static void reset() {
        comparisons = new int[boardSize * 2 - 1][boardSize];
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

    private static boolean winnableWithNone() {
        try {
            gb = new GameBoard(vals, comparisons);
            return gb.solve().solved();
        } catch (Exception ignored) {
            System.out.println("Too hard");
            //ignored.printStackTrace();
        }
        return false;
    }
    
    private static boolean winnableWithOne(int[][] valS, int[][] compareS){
        boolean out = false;
        boolean[][][] acceptances = new boolean[valS.length][valS[0].length][valS.length];
        try {
            gb.solve();
        } catch (Exception ignored) {
            System.out.println("Cannot be solved");
            return false;
        }
        for (int i = 0; i < valS.length * valS[0].length; i++) {
            acceptances[i / valS[0].length][i % valS[0].length] = gb.rows[i / valS[0].length].squares[i % valS[0].length].acceptedNumbers;
        }
        for (int i = 0; i < valS.length * valS[0].length; i++) {
            //System.out.println("Testing windex " + i);
            for (int j = 1; j < valS.length + 1; j++) {
                /*if (!acceptances[i / valS.length][i % valS[0].length][j - 1])
                    continue;*/
                int[][] board = new int[valS.length][valS[0].length];
                for (int a = 0; a < valS.length; a++)
                    System.arraycopy(valS[a], 0, board[a], 0, valS[0].length);
                board[i / valS.length][i % valS[0].length] = j;
                try {
                    GameBoard old = gb;
                    gb = new GameBoard(board, compareS).solve();
                    if (gb.solved()) {
                        System.out.println("((" + (i % valS[0].length) + ", " + (i / valS.length) + "), " + j + ")");
                        out = true;
                    } else {
                        gb = old;
                    }
                } catch (Exception ignored) {
                    //System.out.println(gb);
                    //System.out.println("bad guess dw");
                    //System.out.println(gb);
//ignored.printStackTrace();
                }
            }
        }
        if (out)
            System.out.println("OMGOMGOM I DID IT");
        return out;
    }

    private static boolean winnableWithOne() {
        boolean out = false;
        boolean[][][] acceptances = new boolean[vals.length][vals[0].length][vals.length];
        try {
            gb.solve();
        } catch (Exception ignored) {
            System.out.println("Cannot be solved");
            return false;
        }
        for (int i = 0; i < vals.length * vals[0].length; i++) {
            acceptances[i / vals[0].length][i % vals[0].length] = gb.rows[i / vals[0].length].squares[i % vals[0].length].acceptedNumbers;
        }
        for (int i = 0; i < vals.length * vals[0].length; i++) {
            //System.out.println("Testing windex " + i);
            for (int j = 1; j < vals.length + 1; j++) {
                /*if (!acceptances[i / vals.length][i % vals[0].length][j - 1])
                    continue;*/
                int[][] board = new int[vals.length][vals[0].length];
                for (int a = 0; a < vals.length; a++)
                    System.arraycopy(vals[a], 0, board[a], 0, vals[0].length);
                board[i / vals.length][i % vals[0].length] = j;
                try {
                    GameBoard old = gb;
                    gb = new GameBoard(board, comparisons).solve();
                    if (gb.solved()) {
                        System.out.println("((" + (i % vals[0].length) + ", " + (i / vals.length) + "), " + j + ")");
                        out = true;
                    } else {
                        gb = old;
                    }
                } catch (Exception ignored) {
                    //System.out.println(gb);
                    //System.out.println("bad guess dw");
                    //System.out.println(gb);
//ignored.printStackTrace();
                }
            }
        }
        if (out)
            System.out.println("OMGOMGOM I DID IT");
        return out;
    }

    private enum Difficulties {
        EASY_NO_NUMS("Easy", 20, 0),
        EASY_ONE_NUM("Easy", 2000, 300);

        public final String NAME;
        public final int DIFFICULTY;
        public final int GIVENS;

        Difficulties(String name, int targetDiff, int giveaways) {
            NAME = name;
            DIFFICULTY = targetDiff;
            GIVENS = giveaways;
        }
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
ignored.printStackTrace();
                }
            }
        }
    }
     */
}
