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
    
}
