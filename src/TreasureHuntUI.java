import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class TreasureHuntUI {

    private final TreasureHuntLogic LOGIC;
    private JLabel[][] mazeGrid;
    private JPanel mazePanel;
    private JLabel lives;
    private boolean gameOver = false;

    // Icons
    private final ImageIcon PLAYER_ICON = loadScaledIcon("images/player.png", 35, 35);
    private final ImageIcon TREASURE_ICON = loadScaledIcon("images/treasure.png", 28, 28);
    private final ImageIcon TRAP_ICON = loadScaledIcon("images/trap.png", 28, 28);

    // Constructor
    public TreasureHuntUI(TreasureHuntLogic logic) {
        this.LOGIC = logic;

        // Main frame
        JFrame frame = new JFrame("Treasure Hunt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(generateMazePanel(), BorderLayout.CENTER);
        frame.add(generateInfoPanel(), BorderLayout.SOUTH);

        // Handle WASD movement
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // To prevent user from moving after the game ends
                if (gameOver) return;
                char key = Character.toUpperCase(e.getKeyChar());
                if ("WASD".indexOf(key) != -1) {
                    String result = LOGIC.movePlayer(key);
                    refreshMaze();
                    if (!result.isEmpty()) {
                        displayPopUp(result);
                    }
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    // Draw the original maze
    private JPanel generateMazePanel() {
        int rows = LOGIC.getRows();
        int cols = LOGIC.getCols();
        // Main panel
        mazePanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        // To store labels (later for changing colors)
        mazeGrid = new JLabel[rows][cols];

        // Generate the initial maze
        char[][] generatedMaze = LOGIC.generateMaze();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JLabel cell = createCell(generatedMaze[r][c]);
                mazeGrid[r][c] = cell;
                mazePanel.add(cell);
            }
        }

        return mazePanel;
    }

    // Displays player info such as remaining lives
    public JPanel generateInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        lives = new JLabel("Lives: " + LOGIC.getLives());
        panel.add(lives);
        return panel;
    }

    // Display message if user wins/hits a trap
    public void displayPopUp(String result) {
        String message = "";
        if (Objects.equals(result, "trap")) {
            message = "Oops, you hit a trap!";
        } else if (Objects.equals(result, "treasure")) {
            gameOver = true;
            message = "Yay, you won!";
        } else {
            gameOver = true;
            message = "Noo, you lost:(";
        }
        JOptionPane.showMessageDialog(null, message);
    }

    // Creates one labeled square of the maze
    private JLabel createCell(char type) {
        JLabel cell = new JLabel();
        cell.setPreferredSize(new Dimension(30, 30));
        cell.setOpaque(true);
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);
        cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        updateCellAppearance(cell, type);
        return cell;
    }


    // Maps maze characters to UI colors
    private Color getColorFor(char ch) {
        return switch (ch) {
            case '.' -> Color.WHITE; // Ground
            case '#' -> Color.BLACK; // Wall
            default -> Color.GRAY;
        };
    }

    // Update maze cells
    private void updateCellAppearance(JLabel cell, char type) {
        cell.setIcon(null); // clear previous icon

        switch (type) {
            // Player
            case 'P' -> {
                cell.setIcon(PLAYER_ICON);
                cell.setBackground(Color.WHITE);
            }
            // Treasure
            case 'T' -> {
                cell.setIcon(TREASURE_ICON);
                cell.setBackground(Color.WHITE);
            }
            // Trap
            case 'X' -> {
                cell.setIcon(TRAP_ICON);
                cell.setBackground(Color.WHITE);
            }
            // If it's a wall/ground
            default -> cell.setBackground(getColorFor(type));
        }
    }

    // Refreshes all squares after movement or state changes
    public void refreshMaze() {
        // New maze
        char[][] maze = LOGIC.getCurrentState();

        for (int r = 0; r < LOGIC.getRows(); r++) {
            for (int c = 0; c < LOGIC.getCols(); c++) {
                updateCellAppearance(mazeGrid[r][c], maze[r][c]);
            }
        }
        // Update lives
        lives.setText("Lives: " + LOGIC.getLives());
        mazePanel.repaint();
    }

    // For icon sizing, without it, they don't fit
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        Image img = new ImageIcon(path).getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

}