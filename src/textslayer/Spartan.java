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

public class Spartan {

    private int health = 4;
    private int shield = 2;
    private final Team team;
    private Position position;
    
    public Spartan(Team team) {
        this.team = team;
    } // Default
    
    // Getters and Setters
    public int getHealth() { return health; }
    public int getShield() { return shield; }
    public Team getTeam() {return team; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    
    public void takeDamage( int damage ) {
        // Apply damage to shield first, then health
        int shieldDamage = Math.min(shield, damage);
        shield -= shieldDamage;
        health -= Math.max(0, damage - shieldDamage);
    } // takeDamage()
    
    public boolean isAlive() {
        return health > 0;
    } // isAlive()
    
    @Override
    public String toString() {
        return team.getName() + " Spartan [Health: " + health + ", Shield: " + shield +
           ", Position: (" + position.getX() + "," + position.getY() + ")]";
    } // toString()
} // Spartan
