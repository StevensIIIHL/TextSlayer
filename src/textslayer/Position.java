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

import java.util.HashMap;
import java.util.Map;

/**
 * Position represents a cell on the map grid.
 * It tracks how many Spartans from each team are present in the cell.
 */

public class Position {
    private int x;
    private int y;
    private Map<Team, Integer> occupants = new HashMap<>();

    /**
     * Constructs a Position at the given (x, y) coordinates.
     * @param x The column index.
     * @param y The row index.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    } // Position

    /**
     * Attempts to add a Spartan from the given team to this position.
     * Only 2 Spartans from the same team are allowed in one cell.
     * @param team The team of the Spartan to add.
     * @return True if the Spartan was added, false if the cell is full for that team.
     */
    public boolean addSpartan(Team team) {
        int current = occupants.getOrDefault(team, 0);
        if (current < 2) {
            occupants.put(team, current + 1);
            return true;
        }
        return false;
    } // addSpartan

    /**
     * Removes a Spartan from the given team from this position.
     * Does nothing if no such Spartan is present.
     * @param team The team of the Spartan to remove.
     */
    public void removeSpartan(Team team) {
        occupants.computeIfPresent(team, (k, v) -> v > 0 ? v - 1 : 0);
    } // removeSpartan

    /** @return A map of teams to the number of their Spartans in this cell. */
    public Map<Team, Integer> getOccupants() { return occupants; }

    /** @return The column index of this position. */
    public int getX() { return x; }

    /** @return The row index of this position. */
    public int getY() { return y; }
} // Positon

