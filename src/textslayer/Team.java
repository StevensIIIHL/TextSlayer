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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private final String name;
    private final List<Spartan> spartans;
    private int score;
    
    public Team( String name ) {
        this.name = name;
        this.spartans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            spartans.add(new Spartan(this));
        }
    } // Default
    
    public List<Spartan> getSpartans() {return spartans; }
    public int getScore() { return score; }
    public String getName() { return name; }
    
    public void incrementScore() {
        score++;
    } // increamentScore
    
    public List<Spartan> getAliveSpartans() {
        return spartans.stream().filter( Spartan::isAlive ).collect( Collectors.toList() );
    } // getAliveSpartans
} // Team
