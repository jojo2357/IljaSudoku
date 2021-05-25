package com.github.jojo2357;

import java.util.Arrays;

public abstract class BaseHolder {
    public final Square[] squares;
    protected final GameBoard gb;

    public boolean isDirty = false;

    protected BaseHolder(GameBoard gameBoard) {
        gb = gameBoard;
        squares = new Square[gb.height];
    }

    public void updateAll(boolean stackTrace, int limit) {
        isDirty = false;
        for (Square square : squares)
            square.updateAcceptance(stackTrace, limit);
        if (!isDirty)
            doExpensiveStuff();
    }

    public void doExpensiveStuff(){
        //detect doubles
        for (int i = 0; i < squares.length; i++){
            int count = 0;
            int[] nums = new int[2];
            for (int j = 0; j < squares[i].acceptedNumbers.length; j++)
                if (squares[i].acceptedNumbers[j]) {
                    count++;
                    if (count > 2)
                        break;
                    nums[count - 1] = j;
                }
            if (count != 2)
                continue;
            for (int j = i + 1; j < squares.length; j++){
                int cont = 0;
                int[] bums = new int[2];
                for (int l = 0; l < squares[j].acceptedNumbers.length; l++)
                    if (squares[j].acceptedNumbers[l]) {
                        cont++;
                        if (cont > 2)
                            break;
                        bums[cont - 1] = l;
                    }
                if (cont != 2)
                    continue;
                if (nums[0] == bums[0] && nums[1] == bums[1])
                    for (int f = 0; f < squares.length; f++)
                        if (f != i && f != j){
                            if (squares[f].acceptedNumbers[nums[0]]) {
                                squares[f].acceptedNumbers[nums[0]] = false;
                                squares[f].markAllDirty();
                            }
                            if (squares[f].acceptedNumbers[nums[1]]) {
                                squares[f].acceptedNumbers[nums[1]] = false;
                                squares[f].markAllDirty();
                            }
                        }
            }
        }
        for (int i = 0; i < squares.length; i++){
            for (int num1 = 0; num1 < squares[i].acceptedNumbers.length; num1++) {
                if (!squares[i].acceptedNumbers[num1])
                    continue;
                for (int num2 = num1 + 1; num2 < squares[i].acceptedNumbers.length; num2++){
                    if (!squares[i].acceptedNumbers[num2])
                        continue;
                    int winber = -1;
                    for (int j = 0; j < squares.length; j++){
                        if (j == i)
                            continue;
                        if (squares[j].acceptedNumbers[num1] ^ squares[j].acceptedNumbers[num2]) {
                            winber = -1;
                            break;
                        }
                        if (!squares[j].acceptedNumbers[num1] && !squares[j].acceptedNumbers[num2])
                            continue;
                        if (winber != -1) {
                            winber = -1;
                            break;
                        }else
                            winber = j;
                    }
                    if (winber != -1){
                        int sq1ct = squares[i].countPossibilities(), sq2ct = squares[winber].countPossibilities();
                        Arrays.fill(squares[i].acceptedNumbers, false);
                        squares[i].acceptedNumbers[num1] = true;
                        squares[i].acceptedNumbers[num2] = true;
                        Arrays.fill(squares[winber].acceptedNumbers, false);
                        squares[winber].acceptedNumbers[num1] = true;
                        squares[winber].acceptedNumbers[num2] = true;
                        if (sq1ct > squares[i].countPossibilities()){
                            squares[i].markAllDirty();
                        }
                        if (sq2ct > squares[winber].countPossibilities()){
                            squares[winber].markAllDirty();
                        }
                    }
                }
            }
        }
        if (!isDirty) {
            /*int unsolved = 0;
            for (int i = 0; i < squares.length; i++)
                if (squares[i].val == null)
                    unsolved++;
            for (int tupleSize = unsolved - 1; tupleSize >= 3; tupleSize--){
                for (int tupleStarter = 0; tupleStarter < squares.length; tupleStarter++) {
                    if (squares[tupleStarter].val != null || squares[tupleStarter].countPossibilities() > tupleSize)
                        continue;
                    int tupledex = 0;
                    int[] tuple = new int[tupleSize];
                    Arrays.fill(tuple, -1);
                    int[] userdexes = new int[tupleSize];
                    int userdex = 0;
                    userdexes[userdex++] = tupleStarter;
                    for (int j = 0; j < squares.length; j++)
                        if (squares[tupleStarter].acceptedNumbers[j])
                            tuple[tupledex++] = j;
                    SuperWrapper data;
                    if ((data = tryTupleStuff(tuple, tupledex, userdex, userdexes)).valid){
                        for (int i = 0; i < squares.length; i++){
                            if (has(data.winners, i))
                                continue;
                            for (int removal : data.tuple) {
                                squares[i].row.isDirty |= squares[i].acceptedNumbers[removal];
                                squares[i].box.isDirty |= squares[i].acceptedNumbers[removal];
                                squares[i].column.isDirty |= squares[i].acceptedNumbers[removal];
                                squares[i].acceptedNumbers[removal] = false;
                            }
                        }
                    }
                }
            }*/
        }
    }

    private static class SuperWrapper {
        private final int[] tuple;
        private final int[] winners;
        private final boolean valid;

        private SuperWrapper(int[] tuple, int[] winners, boolean valid) {
            this.tuple = tuple;
            this.winners = winners;
            this.valid = valid;
        }
    }

    public SuperWrapper tryTupleStuff(int[] tuple, int tupledex, int userdex, int[] userdexes){
        if (userdexes[userdexes.length - 1] != 0){
            return new SuperWrapper(tuple, userdexes, tuple[tuple.length - 1] != -1);
        }
        for (int i = userdexes[userdex - 1] + 1; i < squares.length; i++){
            if (squares[i].val != null)
                continue;
            int[] savedTuple = new int[tuple.length];
            System.arraycopy(tuple, 0, savedTuple, 0, tuple.length);
            int[] savedUserdexes = new int[tuple.length];
            System.arraycopy(userdexes, 0, savedUserdexes, 0, userdexes.length);
            int mydex = tupledex;
            try {
                boolean hadAny = false;
                for (int j = 0; j < squares.length; j++) {
                    if (squares[i].acceptedNumbers[j]){
                        if (has(savedTuple, j))
                            hadAny = true;
                        else
                            savedTuple[mydex++] = j;
                    }

                }
                if (!hadAny)
                    continue;
                savedUserdexes[userdex] = i;
                SuperWrapper out;
                if ((out = tryTupleStuff(savedTuple, mydex, userdex + 1, savedUserdexes)).valid) {
                    userdexes = savedUserdexes;
                    tuple = savedTuple;
                    return out;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
        return new SuperWrapper(null, null, false);
    }

    private static boolean has(int[] arr, int num){
        for (int noom : arr)
            if (noom == num)
                return true;
            return false;
    }

    public boolean hasValue(int val) {
        for (Square square : squares) {
            if (square != null && square.val != null && square.val == val) return true;
        }
        return false;
    }

    public boolean isSolved() {
        boolean out = true;
        for (int i = 0; i < gb.width; i++)
            out &= hasValue(i);
        return out;
    }

    public boolean isIllegallySolved() {
        for (int i = 0; i < squares.length - 1; i++) {
            for (int j = i + 1; j < squares.length; j++) {
                if (squares[i] != null && squares[j] != null && squares[i].val != null && squares[j].val != null && squares[i].val.equals(squares[j].val))
                    return true;
            }
        }
        return false;
    }

    /*public boolean solveYourself(){
        for (Square square : squares)
            square.updateAcceptance();
        boolean has6 = false;
        for (Square square : squares)
            if ()
    }*/

    abstract Square getSquare(int index);

    public int indexOf(Square other) {
        for (int i = 0; i < gb.width; i++)
            if (squares[i] == other)
                return i;
        return -1;
    }
}
