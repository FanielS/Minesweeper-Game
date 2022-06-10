package minesweeper;

import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        int mines = in.nextInt();
        Field game = new Field(mines, true);
        game.printField();

            while (game.status) {
                game.printField();

                try {
                    System.out.println("Set/unset mines marks or claim a cell as free:");

                    String[] c = in.nextLine().split(" ");

                    if (game.check(Integer.parseInt(c[1]) - 1, Integer.parseInt(c[0]) - 1)) {
                        game.move(Integer.parseInt(c[1]) - 1, Integer.parseInt(c[0]) - 1, c[2]);
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERROR : Incorrect Input");
                }

            }
        }
    }
