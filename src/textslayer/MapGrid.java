/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textslayer;

/**
 *  Harold Stevens
 *  5/15/2025
 *  Text Slayer
 */

public class MapGrid {
    private static final int SIZE = 8;
    private final Position[][] grid;

    public MapGrid() {
        grid = new Position[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                grid[x][y] = new Position(x, y);
            }
        }
    } // Default

    public boolean isValidMove( Team team, int x, int y ) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        return grid[x][y].getOccupants().getOrDefault(team, 0) < 2;
    } // isValidMove

    public void display() {
        System.out.println( "\nCurrent Battlefield:" );
        for ( int y = 0; y < SIZE; y++ ) {
            for ( int x = 0; x < SIZE; x++ ) {
                Position pos = grid[x][y];
                String red = pos.getOccupants().getOrDefault( Team.RED, 0 ) > 0 ? 
                    "R" + pos.getOccupants().get( Team.RED ) : "";
                String blue = pos.getOccupants().getOrDefault( Team.BLUE, 0 ) > 0 ? 
                    "B" + pos.getOccupants().get( Team.BLUE ) : "";
                System.out.printf("[%-5s]", red + " " + blue);
            }
            System.out.println();
        }
    } // display

    public Position getPosition( int x, int y ) {
        return grid[x][y];
    } // getPosition
} // MapGrid
