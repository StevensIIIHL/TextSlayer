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
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {
    private final MapGrid map = new MapGrid();
    private Team currentTeam;
    private int round = 1;
    private final Scanner scanner = new Scanner(System.in);

    public void startGame() {
        initializePositions();
        determineFirstTurn();
        gameLoop();
    } // startGame()
    
    private void initializePositions() {
        // Initial positioning logic
        for (Spartan s : Team.RED.getSpartans()) {
            s.setPosition(map.getPosition(0, 0));
            map.getPosition(0, 0).addSpartan(Team.RED);
        }
        for (Spartan s : Team.BLUE.getSpartans()) {
            s.setPosition(map.getPosition(7, 7));
            map.getPosition(7, 7).addSpartan(Team.BLUE);
        }
    } // initializePositions()
    
    private void determineFirstTurn() {
        int redRoll = ThreadLocalRandom.current().nextInt(1, 9);
        int blueRoll = ThreadLocalRandom.current().nextInt(1, 9);
        currentTeam = (redRoll > blueRoll) ? Team.RED : Team.BLUE;
        System.out.println("First turn goes to: " + currentTeam.getName());
    } // determineFirstTurn()
    
    private void gameLoop() {
        while (!gameOver()) {
            System.out.println("\n=== Round " + round + " ===");
            map.display();
            processRound();
            round++;
            
            for (Spartan s : Team.RED.getSpartans()) {
                System.out.println(s);
            }
            for (Spartan s : Team.BLUE.getSpartans()) {
                System.out.println(s);
            }
        }
        declareWinner();
    } // gameLoop()
    
    private void processRound() {
        // Activation order logic
        List<Spartan> activationOrder = new ArrayList<>();
        activationOrder.addAll(currentTeam.getAliveSpartans());
        activationOrder.addAll((currentTeam == Team.RED) ? 
            Team.BLUE.getAliveSpartans() : Team.RED.getAliveSpartans());
        
        for (Spartan s : activationOrder) {
            if (s.isAlive()) {
                takeTurn(s);
            }
        }
        currentTeam = (currentTeam == Team.RED) ? Team.BLUE : Team.RED;
    } // processRound()
    
    private void takeTurn( Spartan spartan ) {
        System.out.println( "\n" + spartan.getTeam().getName() + " Spartan's turn");
        move( spartan );
        attack( spartan );
    } // takeTurn()
    
    private void move( Spartan spartan ) {
        System.out.print( "Enter new X, Y coordinates to move (0 - 7): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        
        if ( map.isValidMove( spartan.getTeam(), x, y )) {
            spartan.getPosition().removeSpartan( spartan.getTeam() );
            spartan.setPosition( map.getPosition( x, y ));
            map.getPosition(x, y).addSpartan( spartan.getTeam() );
        }
    }
    
    private void attack( Spartan attacker ) {
        System.out.print( "Attack with (1) Rifle or (2) Knife: ");
        int choice = scanner.nextInt();
        
        if ( choice == 1 ) {
            rifleAttack( attacker );
        } else if ( choice == 2 ) {
            knifeAttack( attacker );
        }
    }
    
    private boolean gameOver() {
        //implement team class
        return Team.RED.getScore() >=4 || Team.Blue.getScore() >= 4;
    } // gameOver()
    
    private void declareWinner() {
        Team winner = Team.RED.getScore() >= ? Team.RED : Team.BLUE;
        // print winner
    } // declareWinner()

    public static void main(String[] args) {
        new GameEngine().startGame();
    } // main()
} // GameEngine
