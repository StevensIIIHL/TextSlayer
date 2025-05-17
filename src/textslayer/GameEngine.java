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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameEngine extends JFrame {
    private final int SIZE = 8;
    private final MapGrid map = new MapGrid();
    private final Team redTeam = new Team("Red");
    private final Team blueTeam = new Team("Blue");
    private Team currentTeam;
    private int round = 1;
    private JLabel statusLabel;
    private JButton[][] gridButtons;

    public GameEngine() {
        setTitle("Spartan War - Red vs Blue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status label
        statusLabel = new JLabel("Welcome to Spartan War!");
        add(statusLabel, BorderLayout.NORTH);

        // Grid Panel
        JPanel gridPanel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1));
        gridButtons = new JButton[SIZE][SIZE];

        // Top-left empty cell for headers
        gridPanel.add(new JLabel(" "));

        // Column headers
        for (int x = 0; x < SIZE; x++) {
            JLabel label = new JLabel("" + x, SwingConstants.CENTER);
            gridPanel.add(label);
        }

        // Row headers and grid buttons
        for (int y = 0; y < SIZE; y++) {
            JLabel rowLabel = new JLabel("" + y, SwingConstants.CENTER);
            gridPanel.add(rowLabel);
            for (int x = 0; x < SIZE; x++) {
                JButton btn = new JButton();
                btn.setMargin(new Insets(2, 2, 2, 2));
                btn.setFont(new Font("Monospaced", Font.PLAIN, 10));
                final int fx = x, fy = y;
                btn.addActionListener(e -> cellClicked(fx, fy));
                gridButtons[x][y] = btn;
                gridPanel.add(btn);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Start game logic
        initializePositions();
        determineFirstTurn();
        updateGrid();

        setSize(600, 600);
        setVisible(true);
    }

    private void initializePositions() {
        for (Spartan s : redTeam.getSpartans()) {
            s.setPosition(map.getPosition(0, 0));
            map.getPosition(0, 0).addSpartan(redTeam);
        }
        for (Spartan s : blueTeam.getSpartans()) {
            s.setPosition(map.getPosition(SIZE - 1, SIZE - 1));
            map.getPosition(SIZE - 1, SIZE - 1).addSpartan(blueTeam);
        }
    }

    private void determineFirstTurn() {
        int redRoll = (int) (Math.random() * 8) + 1;
        int blueRoll = (int) (Math.random() * 8) + 1;
        if (redRoll >= blueRoll) {
            currentTeam = redTeam;
        } else {
            currentTeam = blueTeam;
        }
        statusLabel.setText("First turn: " + currentTeam.getName() + " team. Click a cell to move.");
    }

    private void updateGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Position pos = map.getPosition(x, y);
                String text = "";
                int redCount = pos.getOccupants().getOrDefault(redTeam, 0);
                int blueCount = pos.getOccupants().getOrDefault(blueTeam, 0);
                if (redCount > 0) text += "R" + redCount + " ";
                if (blueCount > 0) text += "B" + blueCount;
                gridButtons[x][y].setText(text.trim());
            }
        }
    }

    // Example: handle cell click (expand with real move/attack logic)
    private void cellClicked(int x, int y) {
        JOptionPane.showMessageDialog(this, "Cell clicked: (" + x + ", " + y + ")");
        // Here you would implement move/attack selection dialogs and update state
        // After updating state, call updateGrid() and update statusLabel
    }

    public static void main(String[] args) {
        // Use Swing thread
        SwingUtilities.invokeLater(GameEngine::new);
    }
}

