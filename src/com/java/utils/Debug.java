package com.java.utils;

import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Translation;

/**
 * Utils packages for debugging purposes.
 */
public class Debug {
    private static final boolean debugging = false;
    private static int debugMessages = 0;

    /**
     * Print the message to the terminal.
     * @param message the message to print to the terminal
     */
    public static void out(String message) {
        if (debugging) {
            System.out.println("DEBUG > " + debugMessages + " : " + message);
            debugMessages++;
        }
    }

    /**
     * Print the decorator to the terminal.
     * @param d the decorator to print to the terminal
     */
    public static void printDecorator(Decorator d) {
        if (debugging) {
            System.out.println("Decorator is : " + d.getClass().getName());
            if (d.getClass().getName().equals("com.java.piece.decorator.Translation")) {
                System.out.println("Direction is : ");
                boolean[] directions = ((Translation) d).getDirections();
                for (int i = 0; i < directions.length; i++) {
                    System.out.print(directions[i] ? new String[]{"North", "East", "South", "West"}[i] : " ");
                }
                System.out.println();
            }
            System.out.println("Action point is : " + d.getActionPoint().getX() + " " + d.getActionPoint().getY());
            System.out.println("Position is : " + d.getPiece().getPos().getX() + " " + d.getPiece().getPos().getY());
            System.out.println("Shape is : ");
            boolean[][] shape = d.getPiece().getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (d.getActionPoint().getX() == (j) && d.getActionPoint().getY() == (i)) {
                        System.out.print("X");
                    } else {
                        System.out.print(shape[i][j] ? "O" : ".");
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * @return boolean true if the program is in debugging mode
     */
    public static boolean isDebugging() {
        return debugging;
    }
}
