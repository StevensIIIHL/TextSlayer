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
import java.util.*;

/**
 * Represents the 8x8 grid and manages buildings.
 */
public class MapGrid {
    public static final int SIZE = 8;
    private final Position[][] grid;

    public MapGrid() {
        grid = new Position[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                grid[x][y] = new Position(x, y);
    }

    public Position getPosition(int x, int y) {
        return grid[x][y];
    }

    public boolean isValidMove(Team team, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        Position pos = grid[x][y];
        return !pos.isBuilding() && pos.addSpartan(team); // test add, will revert if not used
    }

    public void setBuilding(int x, int y) {
        grid[x][y].setBuilding(true);
    }

    public boolean isBuilding(int x, int y) {
        return grid[x][y].isBuilding();
    }

    /**
     * Randomly places two non-overlapping 2x2 buildings, not on first row/col.
     */
    public void placeBuildings() {
        Random rand = new Random();
        int buildingsPlaced = 0;
        while (buildingsPlaced < 3) {
            int x = rand.nextInt(SIZE - 2) + 1; // 1 to 6
            int y = rand.nextInt(SIZE - 2) + 1; // 1 to 6
            // Check overlap
            boolean overlap = false;
            for (int dx = 0; dx < 2; dx++)
                for (int dy = 0; dy < 2; dy++)
                    if (grid[x + dx][y + dy].isBuilding())
                        overlap = true;
            if (!overlap) {
                for (int dx = 0; dx < 2; dx++)
                    for (int dy = 0; dy < 2; dy++)
                        grid[x + dx][y + dy].setBuilding(true);
                buildingsPlaced++;
            }
        }
    }
}
