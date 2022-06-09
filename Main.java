package minesweeper;

import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        int mines = in.nextInt();
        Field game = new Field(mines);
        game.printField();
    }
}
