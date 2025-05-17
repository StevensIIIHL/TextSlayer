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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a team of Spartans.
 */
public class Team {
    private final String name;
    private final List<Spartan> spartans = new ArrayList<>();

    public Team(String name) {
        this.name = name;
        for (int i = 0; i < 4; i++) {
            spartans.add(new Spartan(this));
        }
    }

    public String getName() { return name; }
    public List<Spartan> getSpartans() { return spartans; }

    public List<Spartan> getAliveSpartans() {
        return spartans.stream().filter(Spartan::isAlive).collect(Collectors.toList());
    }

    public boolean hasAliveSpartans() {
        return getAliveSpartans().size() > 0;
    }
}
