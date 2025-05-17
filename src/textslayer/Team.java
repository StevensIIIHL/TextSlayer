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

/**
 * Team represents a team of Spartans (Red or Blue).
 * Each team has a name, a list of Spartans, and a score.
 */
public class Team {
    private final String name;
    private final List<Spartan> spartans;
    private int score;

    /**
     * Constructs a team with the given name and 4 Spartans.
     * @param name The name of the team ("Red" or "Blue").
     */
    public Team(String name) {
        this.name = name;
        this.spartans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            spartans.add(new Spartan(this));
        }
    } // default

    /** @return The list of Spartans on this team. */
    public List<Spartan> getSpartans() { return spartans; }

    /** @return The team's current score. */
    public int getScore() { return score; }

    /** @return The team's name. */
    public String getName() { return name; }

    /** Increments the team's score by 1. */
    public void incrementScore() {
        score++;
    } // incrementScore

    /**
     * @return A list of Spartans on this team that are still alive.
     */
    public List<Spartan> getAliveSpartans() {
        return spartans.stream().filter(Spartan::isAlive).collect(Collectors.toList());
    } // getAliveSpartans
} // Team
