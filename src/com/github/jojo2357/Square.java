package com.github.jojo2357;

import java.util.Arrays;

public class Square {
    public final boolean[] acceptedNumbers = new boolean[]{true, true, true, true, true, true};
    public final Connection[] connections = new Connection[4];
    public final Row row;
    public final Column column;
    public final Box box;
    public Integer val;
    private final int loc;

    public Square(Row ro, Column colum, Box boks, Integer vahl, int loc) {
        if (vahl < 0)
            vahl = null;
        row = ro;
        column = colum;
        box = boks;
        val = vahl;
        this.loc = loc;
    }

    public Square (Row ro, Column colum, Box boks){
        this(ro, colum, boks, null, -1);
    }

    public boolean updateAcceptance(){
        boolean[] previousVals = new boolean[6];
        System.arraycopy(acceptedNumbers, 0, previousVals, 0, 6);
        if (val != null)
            for (int i = 0; i < 6; i++)
                acceptedNumbers[i] = val == i;
        for (Square square : row.squares)
            if (square != this && square.val != null) {
                acceptedNumbers[square.val] = false;
                if (lastNumber() != null)
                    val = lastNumber();
            }
        for (Square square : box.squares)
            if (square != this && square.val != null){
                acceptedNumbers[square.val] = false;
                if (lastNumber() != null)
                    val = lastNumber();
            }
        for (Square square : column.squares)
            if (square != this && square.val != null){
                acceptedNumbers[square.val] = false;
                if (lastNumber() != null)
                    val = lastNumber();
            }
        if (val == null)
            for (Connection connection : connections)
                if (connection != null && connection.biggerTile != null){
                    if (connection.other(this).val != null){
                        if (connection.biggerTile == this){
                            for (int i = 0; i <= connection.other(this).val; i++){
                                acceptedNumbers[i] = false;
                            }
                        }else{
                            for (int i = connection.other(this).val + 1; i < 6; i++){
                                acceptedNumbers[i] = false;
                            }
                        }
                    }else{
                        if (connection.biggerTile == this){
                            for (int i = 0; i < connection.other(this).minPossible() + 1; i++){
                                acceptedNumbers[i] = false;
                            }
                        }else{
                            for (int i = connection.other(this).maxPossible(); i < 6; i++){
                                acceptedNumbers[i] = false;
                            }
                        }
                    }
                }
        for (int i = 0; i < 6; i++){
            if (!acceptedNumbers[i])
                continue;
            boolean foundElsewhere = false;
            for (Square square : row.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                for (int j = 0; j < 6; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
            foundElsewhere = false;
            for (Square square : column.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                for (int j = 0; j < 6; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
            foundElsewhere = false;
            for (Square square : box.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                for (int j = 0; j < 6; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
        }
        if (val == null){
            if (loc == 32)
                System.out.print("");
            Box queryBox;
            if (row.squares[5].box == box)
                queryBox = row.squares[0].box;
            else
                queryBox = row.squares[5].box;
            boolean amIUp = box.squares[0] == this || box.squares[1] == this || box.squares[2] == this;
            for (int i = 0; i < 6; i++){
                if (!acceptedNumbers[i])
                    continue;
                boolean upRowCan = false;
                boolean downRowCan = false;
                for (int j = 0; j < 3; j++){
                    upRowCan |= queryBox.squares[j].acceptedNumbers[i];
                    downRowCan |= queryBox.squares[j + 3].acceptedNumbers[i];
                }
                if (upRowCan ^ downRowCan){

                    acceptedNumbers[i] = (amIUp != upRowCan);
                }
            }
        }
        if (lastNumber() != null)
            val = lastNumber();
        if (!Arrays.equals(previousVals, acceptedNumbers)){
            for (Square connection : box.squares){
                connection.updateAcceptance();
            }
            for (Square connection : row.squares){
                connection.updateAcceptance();
            }
            for (Square connection : column.squares){
                connection.updateAcceptance();
            }
            return true;
        }
        return false;
    }

    public Integer lastNumber(){
        Integer out = null;
        for (int i = 0; i < 6; i++){
            if (acceptedNumbers[i])
                if (out == null)
                    out = i;
                else
                    return null;
        }
        return out;
    }

    public int minPossible(){
        for (int i = 0; i < 6; i++)
            if (acceptedNumbers[i])
                return i;
        return -1;
    }

    public int maxPossible(){
        for (int i = 5; i >= 0; i--)
            if (acceptedNumbers[i])
                return i;
        return -1;
    }
}
