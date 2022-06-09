package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    char[][] fieldBack = new char[9][9];
    char[][] fieldFront = new char[9][9];
    private final int mines;
    boolean status;
    int wrongMarks;
    int minesCheck;

    public Field(int mines, boolean status) {
        this.mines = mines;
        int minesCheck = mines;
        this.status = status;
        for (char[] chars : fieldBack) {
            Arrays.fill(chars, '.');
        }

        for (char[] chars : fieldFront) {
            Arrays.fill(chars, '.');
        }

        // add mines at random cells
        int numberOfMines = 0;
        while (numberOfMines < mines) {
            Random random = new Random();
            int mineX = random.nextInt(9);
            int mineY = random.nextInt(9);

            if (fieldBack[mineX][mineY] !='X') {
                fieldBack[mineX][mineY] = 'X';
                numberOfMines++;
            }
        }

        // Providing hint on the number of mines around the empty cells
        for (int i = 0; i < fieldBack.length; i++) {
            for (int j = 0; j < fieldBack[i].length; j++) {
               if (fieldBack[i][j] != 'X') {
                   int hint = countMines(i, j);

                   if (hint != 0) {
                       fieldBack[i][j] = Character.forDigit(hint, 10);
                       fieldFront[i][j] = Character.forDigit(hint, 10);
                   }
               }
            }
        }
    }

    int countMines(int row, int col) {
        int hint = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (fieldBack[row + i][col + j] == 'X') {
                        hint++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }
            }
        }
        return hint;
    }

    void printFrontField() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        int i = 1;
        for (char[] arr : fieldFront) {
            System.out.print(i++ + "|");
            for (char chr : arr) {
                System.out.print(chr);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    String checkCell(int row, int col) {
       if (fieldBack[row][col] == 'X') {
           fieldBack[row][col] = '*';
           fieldFront[row][col] = '*';
           minesCheck--;
           return "found X";
       } else if (fieldBack[row][col] >= 1 && fieldBack[row][col] <= mines) {
           return "found Number";
        } else if (fieldBack[row][col] == '*') {
           fieldBack[row][col] = '.';
           fieldFront[row][col] = '.';
           wrongMarks--;
           return "remove miss";
       }  else {
           fieldBack[row][col] = '*';
           fieldFront[row][col] = '*';
           wrongMarks++;
           return "missed";
       }
    }

    void isGameOver() {
        if (minesCheck == 0 && wrongMarks == 0) {
            status = false;
        }
    }
}
