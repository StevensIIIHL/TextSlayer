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

/**
 * MapGrid represents the 8x8 battlefield grid.
 * Each cell is a Position and can be queried or updated.
 */
public class MapGrid {
    public static final int SIZE = 8;
    private final Position[][] grid;

    /**
     * Constructs a new 8x8 grid of Positions.
     */
    public MapGrid() {
        grid = new Position[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                grid[x][y] = new Position(x, y);
            }
        }
    } // default

    /**
     * Checks if a team can move a Spartan to the given coordinates.
     * @param team The team attempting to move.
     * @param x The target column.
     * @param y The target row.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(Team team, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        return grid[x][y].getOccupants().getOrDefault(team, 0) < 2;
    } // isValidMove

    /**
     * Gets the Position object at the given coordinates.
     * @param x The column index.
     * @param y The row index.
     * @return The Position at (x, y).
     */
    public Position getPosition(int x, int y) {
        return grid[x][y];
    } // Position getPosition

    /**
     * Utility method for GUI: returns all Spartans at a given cell.
     * @param x The column index.
     * @param y The row index.
     * @param redTeam The Red team instance.
     * @param blueTeam The Blue team instance.
     * @return A String describing all Spartans at this cell.
     */
    public String describeCell(int x, int y, Team redTeam, Team blueTeam) {
        Position pos = grid[x][y];
        StringBuilder sb = new StringBuilder();
        for (Spartan s : redTeam.getSpartans()) {
            if (s.getPosition() == pos && s.isAlive()) {
                sb.append(s).append("\n");
            }
        }
        for (Spartan s : blueTeam.getSpartans()) {
            if (s.getPosition() == pos && s.isAlive()) {
                sb.append(s).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "Empty";
    } // describeCell
} // MapGrid

