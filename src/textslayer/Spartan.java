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
 * Spartan represents a single soldier in the game.
 * Each Spartan belongs to a team, has health, shield, and a position on the map.
 */

public class Spartan {
    private int health = 4;
    private int shield = 2;
    private final Team team;
    private Position position;

    /**
     * Constructs a Spartan belonging to the given team.
     * @param team The team this Spartan belongs to.
     */
    public Spartan(Team team) {
        this.team = team;
    }

    // Getters and Setters

    /** @return The Spartan's current health. */
    public int getHealth() { return health; }

    /** @return The Spartan's current shield. */
    public int getShield() { return shield; }

    /** @return The Spartan's team. */
    public Team getTeam() { return team; }

    /** @return The Spartan's current position. */
    public Position getPosition() { return position; }

    /** Sets the Spartan's position. */
    public void setPosition(Position position) { this.position = position; }

    /**
     * Applies rifle damage (1 point) to this Spartan.
     * Damage is absorbed by shield first, then health.
     * @param damage The amount of damage (should be 1 for rifle).
     */
    public void takeRifleDamage(int damage) {
        int shieldDamage = Math.min(shield, damage);
        shield -= shieldDamage;
        health -= Math.max(0, damage - shieldDamage);
    }

    /**
     * Applies knife damage to this Spartan.
     * Knife first depletes 1 shield, then deals 1 damage to shield or health.
     */
    public void takeKnifeDamage() {
        if (shield > 0) shield--; // deplete 1 shield
        if (shield > 0) {
            shield--; // knife damage to remaining shield
        } else {
            health--; // knife damage to health if shield is gone
        }
    }

    /** @return True if the Spartan is alive (health > 0). */
    public boolean isAlive() {
        return health > 0;
    }

    /** @return A readable string representation of this Spartan. */
    @Override
    public String toString() {
        return team.getName() + " Spartan [Health: " + health + ", Shield: " + shield +
                ", Position: (" + position.getX() + "," + position.getY() + ")]";
    }
}

