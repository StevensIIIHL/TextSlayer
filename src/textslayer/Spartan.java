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

/**
 * Represents a Spartan unit in the game.
 */
public class Spartan {
    private int health = 4;
    private int shield = 2;
    private final Team team;
    private Position position;
    private boolean hasActed = false; // Used for round order

    public Spartan(Team team) {
        this.team = team;
    }

    public int getHealth() { return health; }
    public int getShield() { return shield; }
    public Team getTeam() { return team; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public boolean isAlive() { return health > 0; }
    public boolean hasActed() { return hasActed; }
    public void setHasActed(boolean acted) { this.hasActed = acted; }

    /**
     * Increases shield by 1, up to max 2.
     */
    public void regenerateShield() {
        if (shield < 2) shield++;
    }

    /**
     * Applies damage to this Spartan, first to shield, then health.
     * Damage is reduced by 2 (damage reduction).
     */
    public void applyDamage(int damage, boolean isKnife) {
        // Knife: first reduce shield by 1
        if (isKnife && shield > 0) {
            shield--;
        }
        // Apply damage reduction
        int reduced = Math.max(0, damage - 2);
        // Apply to shield first
        int shieldDamage = Math.min(shield, reduced);
        shield -= shieldDamage;
        int healthDamage = Math.max(0, reduced - shieldDamage);
        health -= healthDamage;
    }

    @Override
    public String toString() {
        return team.getName() + " Spartan [HP: " + health + ", SH: " + shield +
                ", (" + position.getX() + "," + position.getY() + ")]";
    }
}
