package minesweeper;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Faniel S. Abraham
 * version 1.1
 */

public class Field {
    char[][] fieldBack = new char[9][9];
    char[][] fieldFront = new char[9][9];
    private final int mines;
    boolean status;
    int count = 0;

    public Field(int mines, boolean status) {
        this.mines = mines;
        this.status = status;
        for (char[] chars : fieldBack) {
            Arrays.fill(chars, '.');
        }

        for (char[] chars : fieldFront) {
            Arrays.fill(chars, '.');
        }

        //add mines at random cells
        int numberOfMines = 0;
        while (numberOfMines < mines) {
            Random random = new Random();
            int mineX = random.nextInt(9);
            int mineY = random.nextInt(9);

            if (fieldBack[mineX][mineY] != 'X') {
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

    public boolean check(int i, int j) {
        if (i >= 0 && i < fieldBack.length && j >= 0 && j < fieldBack[i].length) {

            return true;
        }
        System.out.println("Coordinates should be from 1 to 9");
        return true;
    }

    void printField() {
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

    public void move(int i, int j, String s) {  // needs to be fixed too, if you write wrong option return

        if ("mine".equals(s)) {
            moveMine(i, j);

        } else if ("free".equals(s)) {
            moveFree(i, j);
        } else {
            System.out.println("Wrong option |write free or mine");
        }
    }

    public void moveMine(int i, int j) {
        if (fieldFront[i][j] == '*') {

            fieldFront[i][j] = '.';
            count--;
            return;
        } else {
            fieldFront[i][j] = '*';

        }

        if (fieldBack[i][j] == 'X') {
            fieldFront[i][j] = '*';
            if (fieldFront[i][j] == '*') {
                count++;
            }
        }

        checkWin();
    }


    public void moveFree(int i, int j) {
        if (fieldBack[i][j] > 0) {
            fieldFront[i][j] = fieldBack[i][j];
        }
        if (fieldBack[i][j] == 'X') {
            System.out.println("you stepped on a mine and failed!");
            status = false;
            printField();
        }
        if (fieldBack[i][j] == '.') {

            floodFill(i, j);

        }

    }

    // check free spaces 
    void floodFill(int x, int y) {
        if (x < 0 || y < 0 || y > 8 || x > 8 || fieldBack[x][y] < 9) return;
        if (fieldBack[x][y] != '.') {
            fieldFront[x][y] = fieldBack[x][y];
        }
        if (fieldBack[x][y] == '*') {
            fieldFront[x][y] = '/';
        }
        if (fieldBack[x][y] == '.') {
            fieldBack[x][y] = '/';
            fieldFront[x][y] = '/';
            floodFill(x + 1, y);
            floodFill(x - 1, y);
            floodFill(x, y - 1);
            floodFill(x, y + 1);
            floodFill(x + 1, y + 1);
            floodFill(x - 1, y - 1);
            floodFill(x - 1, y + 1);
            floodFill(x + 1, y - 1);

        }
    }

    public void checkWin() {

        if (this.count == this.mines) {
            System.out.println("Congratulations! You found all mines!");
            printField();
            status = false;
        }
    }
}
