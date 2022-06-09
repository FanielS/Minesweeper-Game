package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    char[][] field = new char[9][9];
    private final int mines;

    public Field(int mines) {
        this.mines = mines;
        for (char[] chars : field) {
            Arrays.fill(chars, '.');
        }

        // add mines at random cells
        int numberOfMines = 0;
        while (numberOfMines < mines) {
            Random random = new Random();
            int mineX = random.nextInt(9);
            int mineY = random.nextInt(9);

            if (field[mineX][mineY] !='X') {
                field[mineX][mineY] = 'X';
                numberOfMines++;
            }
        }

        // Providing hint on the number of mines around the empty cells
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
               if (field[i][j] != 'X') {
                   int hint = countMines(i, j);

                   if (hint != 0) {
                       field[i][j] = Character.forDigit(hint, 10);
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
                    if (field[row + i][col + j] == 'X') {
                        hint++;
                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }
            }
        }
        return hint;
    }

    void printField() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (char[] arr : field) {
            for (char chr : arr) {
                System.out.print(chr);
            }
            System.out.println();
        }
    }


}
