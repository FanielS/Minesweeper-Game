package minesweeper;

import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        int mines = in.nextInt();
        Field game = new Field(mines, true);
        game.printFrontField();

        while (game.status) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            int y = in.nextInt() - 1;
            int x = in.nextInt() - 1;

            switch (game.checkCell(x, y)) {
                case "found X":
                case "remove miss":
                case "missed":
                    game.printFrontField();
                    break;
                case "found Number":
                    System.out.println("There is a number here!");
            }
            game.isGameOver();
        }
        System.out.println("Congratulations! You found all mines!");
    }
}
