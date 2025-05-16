/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textslayer;

/**
 *
 * @author sinn3
 */

/*

Classes to implement

Position Class (to keep track of position on the map) Should I add height? 3 dimentional arrays?

*/

public class Spartan {

    private int health = 4;
    private int shield = 2;
    private final Team team;
    private Position position;
    
    public Spartan(Team team) {
        this.team = team;
    }
    
    // Getters and Setters
    public int getHealth() { return health; }
    public int getShield() { return shield; }
    public Team getTeam() {return team; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    
    /*
    keep track of damage in Spartan class?
    */
    public void takeDamage( int damage ) {
        // Apply damage to shield first, then health
        int shieldDamage = Math.min(sheild, damage);
        shield -= shieldDamage;
        health -= Math.max(0, damage - shieldDamage);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    @Override
    public String toString() {
        return team.getName() + " Spartan [Health: " + health + ", Shield: " + shield +
           ", Position: (" + position.getX() + "," + position.getY() + ")]";
    }
    
}
