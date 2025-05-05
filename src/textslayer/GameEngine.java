/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textslayer;

/**
 *
 * @author sinn3
 */



public class GameEngine {
    
    private int round = 1;

    public void startGame() {
        //initializePositions();
        //determineFirstTurn();
        gameLoop();
    }
    
    private void gameLoop() {
        while (!gameOver()) {
            System.out.println("\n=== Round " + round + " ===");
            //map.display();
            round++;
        }
        declareWinner();
    }
    
    private boolean gameOver() {
        //implement team class
        return Team.RED.getScore() >=4 || Team.Blue.getScore() >= 4;
    } 
    
    private void declareWinner() {
        Team winner = Team.RED.getScore() >= ? Team.RED : Team.BLUE;
        // print winner
    }

    public static void main(String[] args) {
        new GameEngine().startGame();
    }
}
