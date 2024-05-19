package com.java.utils;

import com.java.piece.PieceBrute;
import com.java.piece.decorator.Decorator;
import com.java.geometry.IntCoordinates;
import com.java.piece.decorator.Translation;

/**
 * Utils packages for debugging
 */

public class Debug {

    // TODO switch to false when prototype is finished.
    private static boolean debugging = true;

    /**
     * @return boolean true if the program is in debugging mode
     */
    public static boolean isDeubgging() {
        return debugging;
    }

    public static void printBooleanArray(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printShapePieces(PieceBrute p) {
        IntCoordinates fixturePoint = p.getFixturePoint();
        System.out.println("Fixture point is : " + fixturePoint.getX() + " " + fixturePoint.getY());
        System.out.println("position is x : " + p.getPos().getX() + " y : " + p.getPos().getY());
        boolean[][] shape = p.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                // System.out.println(" i = " + i + " j = " + j + " fixturePoint.getX() = " +
                // fixturePoint.getX() + " fixturePoint.getY() = " + fixturePoint.getY());
                if (fixturePoint.getX() == (j) && fixturePoint.getY() == (i)) {
                    System.out.print("X");
                } else {
                    System.out.print(shape[i][j] == true ? "O" : ".");
                }
            }
            System.out.println();
        }
    }

    public static void printDecorator(Decorator d) {
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
        System.out.println("Position is : " + d.getLast().getPos().getX() + " " + d.getLast().getPos().getY());
        System.out.println("Shape is : ");
        boolean[][] shape = d.getLast().getShape();
        IntCoordinates fixturePoint = d.getLast().getFixturePoint();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                // System.out.println(" i = " + i + " j = " + j + " fixturePoint.getX() = " +
                // fixturePoint.getX() + " fixturePoint.getY() = " + fixturePoint.getY());
                if (d.getActionPoint().getX() == (j) && d.getActionPoint().getY() == (i)) {
                    System.out.print("X");
                } else {
                    System.out.print(shape[i][j] ? "O" : ".");
                }
            }
            System.out.println();
        }
        // printBooleanArray(d.getShape());
    }

    private static int debugMessages = 0;

    /**
     * Toggles the debugging mode
     */
    public static void toggleDebugging() {
        debugging = !debugging;
    }

    /**
     * @param message the message to print to the terminal
     */
    public static void out(String message) {
        if (debugging) {
            System.out.println("DEBUG > " + debugMessages + " : " + message);
            debugMessages++;
        }
    }

}
