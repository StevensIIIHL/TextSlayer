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
import java.util.*;
import java.util.List;

/**
 * Main GUI and game logic for Grid Slayer.
 */
public class GameEngine extends JFrame {
    private final int SIZE = 8;
    private final MapGrid map = new MapGrid();
    private final Team redTeam = new Team("Red");
    private final Team blueTeam = new Team("Blue");
    private final List<Spartan> turnOrder = new ArrayList<>();
    private int currentTurnIndex = 0;
    private JLabel statusLabel;
    private JButton[][] gridButtons;
    private Spartan selectedSpartan = null;

    public GameEngine() {
        setTitle("Grid Slayer - Red vs Blue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status label
        statusLabel = new JLabel("Welcome to Grid Slayer!");
        add(statusLabel, BorderLayout.NORTH);

        // Grid Panel with headers
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

        // Control Panel
        JPanel controlPanel = new JPanel();
        JButton nextTurnBtn = new JButton("Next Turn");
        nextTurnBtn.addActionListener(e -> nextTurn());
        controlPanel.add(nextTurnBtn);
        add(controlPanel, BorderLayout.SOUTH);

        // Setup game
        map.placeBuildings();
        spawnTeams();
        startNewRound();

        setSize(700, 700);
        setVisible(true);
    }

    /**
     * Spawns Red and Blue teams in their respective spawn zones.
     */
    private void spawnTeams() {
        Random rand = new Random();
        // Red: x=0-3, y=0
        for (Spartan s : redTeam.getSpartans()) {
            while (true) {
                int x = rand.nextInt(4);
                int y = 0;
                Position pos = map.getPosition(x, y);
                if (!pos.isBuilding() && pos.addSpartan(redTeam)) {
                    s.setPosition(pos);
                    break;
                }
            }
        }
        // Blue: x=4-7, y=7
        for (Spartan s : blueTeam.getSpartans()) {
            while (true) {
                int x = rand.nextInt(4) + 4;
                int y = 7;
                Position pos = map.getPosition(x, y);
                if (!pos.isBuilding() && pos.addSpartan(blueTeam)) {
                    s.setPosition(pos);
                    break;
                }
            }
        }
    }

    /**
     * Starts a new round: shield regen, resets turn order, selects random Spartan to start.
     */
    private void startNewRound() {
        // Upkeep: Regenerate shields
        for (Spartan s : redTeam.getAliveSpartans()) s.regenerateShield();
        for (Spartan s : blueTeam.getAliveSpartans()) s.regenerateShield();

        // Reset acted status and build turn order
        turnOrder.clear();
        List<Spartan> allSpartans = new ArrayList<>();
        allSpartans.addAll(redTeam.getAliveSpartans());
        allSpartans.addAll(blueTeam.getAliveSpartans());
        for (Spartan s : allSpartans) s.setHasActed(false);

        // Randomize order, alternate teams
        Collections.shuffle(allSpartans);
        boolean redTurn = new Random().nextBoolean();
        while (!allSpartans.isEmpty()) {
            Team team = redTurn ? redTeam : blueTeam;
            Optional<Spartan> next = allSpartans.stream().filter(s -> s.getTeam() == team).findFirst();
            if (next.isPresent()) {
                turnOrder.add(next.get());
                allSpartans.remove(next.get());
            }
            redTurn = !redTurn;
        }
        currentTurnIndex = 0;
        updateGrid();
        statusLabel.setText("New round! Shields regenerated. Click 'Next Turn' to begin.");
    }

    /**
     * Advances to the next Spartan's turn.
     */
    private void nextTurn() {
        if (currentTurnIndex >= turnOrder.size()) {
            checkVictoryOrNextRound();
            return;
        }
        selectedSpartan = turnOrder.get(currentTurnIndex);
        if (!selectedSpartan.isAlive()) {
            currentTurnIndex++;
            nextTurn();
            return;
        }
        statusLabel.setText(selectedSpartan.getTeam().getName() + " Spartan's turn: Select a cell to move (up to 2 cells, buildings block).");
    }

    /**
     * Handles cell click for movement and then attacks if desired.
     */
    private void cellClicked(int x, int y) {
        if (selectedSpartan == null) {
            statusLabel.setText("Click 'Next Turn' to proceed.");
            return;
        }
        Position oldPos = selectedSpartan.getPosition();
        int dist = Math.abs(oldPos.getX() - x) + Math.abs(oldPos.getY() - y);
        if (dist > 2) {
            statusLabel.setText("Can only move up to 2 cells.");
            return;
        }
        if (map.isBuilding(x, y)) {
            statusLabel.setText("Building blocks movement! Pick another cell.");
            return;
        }
        // Check team/cell limit
        Position newPos = map.getPosition(x, y);
        int teamCount = newPos.getOccupants().getOrDefault(selectedSpartan.getTeam(), 0);
        int totalCount = newPos.getOccupants().values().stream().mapToInt(Integer::intValue).sum();
        if (teamCount >= 2 || totalCount >= 4) {
            statusLabel.setText("Cell full for your team or total. Pick another cell.");
            return;
        }
        // Move Spartan
        oldPos.removeSpartan(selectedSpartan.getTeam());
        newPos.addSpartan(selectedSpartan.getTeam());
        selectedSpartan.setPosition(newPos);

        // Show move
        gridButtons[x][y].setText(selectedSpartan.getTeam().getName().charAt(0) + " moved");
        gridButtons[oldPos.getX()][oldPos.getY()].setText("");
        statusLabel.setText(selectedSpartan.getTeam().getName() + " Spartan moved to (" + x + "," + y + ")");
        selectedSpartan.setHasActed(true);

        // After move, prompt for attack or skip
        javax.swing.Timer timer = new javax.swing.Timer(800, evt -> {
            updateGrid();
            promptAttack(selectedSpartan);
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Prompts user to attack or skip. Handles attack logic.
     */
    private void promptAttack(Spartan spartan) {
        int option = JOptionPane.showOptionDialog(this,
                "Attack or skip attack to move 1 extra cell next round?",
                "Action",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Attack", "Skip"},
                "Attack");
        if (option == 0) {
            // Attack: choose weapon and target
            doAttack(spartan);
        } else {
            // Skip: could allow extra movement next round (not implemented here)
            statusLabel.setText("Attack skipped. Click 'Next Turn' for next Spartan.");
            currentTurnIndex++;
            selectedSpartan = null;
        }
    }

    /**
     * Handles attack logic: select weapon, select target, roll dice, apply damage.
     */
    private void doAttack(Spartan attacker) {
        // List possible targets in range
        List<Spartan> targets = getTargetsInRange(attacker);
        if (targets.isEmpty()) {
            statusLabel.setText("No targets in range. Click 'Next Turn'.");
            currentTurnIndex++;
            selectedSpartan = null;
            return;
        }
        // Select weapon
        String[] weapons = {"Rifle (Range 4)", "Knife (Same cell)"};
        int weapon = JOptionPane.showOptionDialog(this,
                "Choose weapon:",
                "Weapon",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                weapons,
                weapons[0]);
        if (weapon == -1) {
            statusLabel.setText("Attack cancelled. Click 'Next Turn'.");
            currentTurnIndex++;
            selectedSpartan = null;
            return;
        }
        // Select target
        String[] targetStrings = targets.stream().map(Spartan::toString).toArray(String[]::new);
        int targetIdx = JOptionPane.showOptionDialog(this,
                "Choose target:",
                "Target",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                targetStrings,
                targetStrings[0]);
        if (targetIdx == -1) {
            statusLabel.setText("Attack cancelled. Click 'Next Turn'.");
            currentTurnIndex++;
            selectedSpartan = null;
            return;
        }
        Spartan defender = targets.get(targetIdx);

        // Roll dice for accuracy
        boolean hit = new Random().nextInt(100) >= 50;
        if (!hit) {
            JOptionPane.showMessageDialog(this, "Attack missed (accuracy roll < 51%).");
            statusLabel.setText("Attack missed. Click 'Next Turn'.");
            currentTurnIndex++;
            selectedSpartan = null;
            return;
        }

        // Roll dice for attack/defense
        int attackerSuccess = rollDice(3);
        int defenderSuccess = rollDice(2);
        int netSuccess = Math.max(0, attackerSuccess - defenderSuccess);

        int damage = 0;
        boolean isKnife = (weapon == 1);
        if (isKnife) {
            // Knife: must be in same cell
            if (attacker.getPosition() != defender.getPosition()) {
                JOptionPane.showMessageDialog(this, "Knife can only attack in same cell.");
                statusLabel.setText("Knife attack failed. Click 'Next Turn'.");
                currentTurnIndex++;
                selectedSpartan = null;
                return;
            }
            // Knife: first reduce shield by 1, then apply damage
            damage = netSuccess * 2;
        } else {
            // Rifle: range 4, damage 2 per success
            int dist = Math.abs(attacker.getPosition().getX() - defender.getPosition().getX()) +
                       Math.abs(attacker.getPosition().getY() - defender.getPosition().getY());
            if (dist > 4) {
                JOptionPane.showMessageDialog(this, "Rifle out of range!");
                statusLabel.setText("Rifle attack failed. Click 'Next Turn'.");
                currentTurnIndex++;
                selectedSpartan = null;
                return;
            }
            // Building blocks line of sight (not fully implemented here)
            damage = netSuccess * 2;
        }
        // Apply damage
        defender.applyDamage(damage, isKnife);
        JOptionPane.showMessageDialog(this, "Attack hits! " + netSuccess + " successes. " +
                "Damage: " + damage + ". Defender after: " + defender);

        // Remove dead Spartan from cell
        if (!defender.isAlive()) {
            defender.getPosition().removeSpartan(defender.getTeam());
            JOptionPane.showMessageDialog(this, defender.getTeam().getName() + " Spartan eliminated!");
        }
        updateGrid();
        statusLabel.setText("Attack complete. Click 'Next Turn'.");
        currentTurnIndex++;
        selectedSpartan = null;
    }

    /**
     * Returns a list of enemy Spartans in range for the attacker.
     */
    private List<Spartan> getTargetsInRange(Spartan attacker) {
        List<Spartan> enemies = (attacker.getTeam() == redTeam ? blueTeam : redTeam).getAliveSpartans();
        List<Spartan> inRange = new ArrayList<>();
        for (Spartan s : enemies) {
            int dist = Math.abs(attacker.getPosition().getX() - s.getPosition().getX()) +
                       Math.abs(attacker.getPosition().getY() - s.getPosition().getY());
            if (dist <= 4 || attacker.getPosition() == s.getPosition()) {
                inRange.add(s);
            }
        }
        return inRange;
    }

    /**
     * Rolls n 6-sided dice, returns number of dice >= 4.
     */
    private int rollDice(int n) {
        Random rand = new Random();
        int success = 0;
        for (int i = 0; i < n; i++) {
            int roll = rand.nextInt(6) + 1;
            if (roll >= 4) success++;
        }
        return success;
    }

    /**
     * Checks for victory, or starts next round.
     */
    private void checkVictoryOrNextRound() {
        if (!redTeam.hasAliveSpartans()) {
            JOptionPane.showMessageDialog(this, "Blue Team wins!");
            System.exit(0);
        }
        if (!blueTeam.hasAliveSpartans()) {
            JOptionPane.showMessageDialog(this, "Red Team wins!");
            System.exit(0);
        }
        startNewRound();
    }

    /**
     * Updates the grid display, showing buildings and Spartans.
     */
    private void updateGrid() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Position pos = map.getPosition(x, y);
                JButton btn = gridButtons[x][y];
                String text = "";
                if (pos.isBuilding()) {
                    text = "BUILD";
                } else {
                    int redCount = pos.getOccupants().getOrDefault(redTeam, 0);
                    int blueCount = pos.getOccupants().getOrDefault(blueTeam, 0);
                    if (redCount > 0) text += "R" + redCount + " ";
                    if (blueCount > 0) text += "B" + blueCount;
                }
                gridButtons[x][y].setText(text.trim());
                
                // Highlight if this is the active Spartan's cell
            if (selectedSpartan != null &&
                selectedSpartan.getPosition().getX() == x &&
                selectedSpartan.getPosition().getY() == y) {
                btn.setBackground(Color.YELLOW); // Highlight color
                btn.setOpaque(true);
                btn.setBorderPainted(true);
            } else {
                btn.setBackground(null); // Default color
                btn.setOpaque(true);
                btn.setBorderPainted(true);
            }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameEngine::new);
    }
}
