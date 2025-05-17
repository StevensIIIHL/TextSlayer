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
 * Represents a cell on the grid.
 */
public class Position {
    private final int x, y;
    private final Map<Team, Integer> occupants = new HashMap<>();
    private boolean isBuilding = false;

    public Position(int x, int y) {
        this.x = x; this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isBuilding() { return isBuilding; }
    public void setBuilding(boolean b) { isBuilding = b; }

    public boolean addSpartan(Team team) {
        int current = occupants.getOrDefault(team, 0);
        int total = occupants.values().stream().mapToInt(Integer::intValue).sum();
        if (current < 2 && total < 4) {
            occupants.put(team, current + 1);
            return true;
        }
        return false;
    }

    public void removeSpartan(Team team) {
        occupants.computeIfPresent(team, (k, v) -> v > 0 ? v - 1 : 0);
    }

    public Map<Team, Integer> getOccupants() { return occupants; }
}
