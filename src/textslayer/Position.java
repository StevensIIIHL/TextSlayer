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

public class Position {
    private int x;
    private int y;
    private Map<Team, Integer> occupants = new HashMap<>();

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    } // Default

    public boolean addSpartan(Team team) {
        int current = occupants.getOrDefault(team, 0);
        if (current < 2) {
            occupants.put(team, current + 1);
            return true;
        }
        return false;
    } // addSpartan

    public void removeSpartan(Team team) {
        occupants.computeIfPresent(team, (k, v) -> v > 0 ? v - 1 : 0);
    } // removeSpartan

    public Map<Team, Integer> getOccupants() { return occupants; }
    public int getX() { return x; }
    public int getY() { return y; }
} // Position
