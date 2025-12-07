import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TreasureHuntUI {

    private final TreasureHuntLogic LOGIC;
    private JLabel[][] mazeGrid;
    private JPanel mazePanel;
    private JLabel lives;

    private final ImageIcon PLAYER_ICON = loadScaledIcon("images/player.png", 35, 35);
    private final ImageIcon TREASURE_ICON = loadScaledIcon("images/treasure.png", 28, 28);
    private final ImageIcon TRAP_ICON = loadScaledIcon("images/trap.png", 28, 28);

    public TreasureHuntUI(TreasureHuntLogic logic) {
        this.LOGIC = logic;

        JFrame frame = new JFrame("Treasure Hunt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(drawMaze(), BorderLayout.CENTER);
        frame.add(infoPanel(), BorderLayout.SOUTH);

        // Handle WASD movement
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = Character.toUpperCase(e.getKeyChar());
                if ("WASD".indexOf(key) != -1) {
                    String result = LOGIC.movePlayer(key);
                    refreshMaze();
                    if (!result.isEmpty()) {
                        String message = "";
                        if (result == "trap") {
                            message = "Oops, you hit a trap!";
                        } else if (result == "treasure") {
                            message = "Yay, you won!";
                        }
                        JOptionPane.showMessageDialog(null, message);
                    }
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    // Creates the maze grid UI and populates it from the logic
    private JPanel drawMaze() {
        int rows = LOGIC.getRows();
        int cols = LOGIC.getCols();

        mazePanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        mazeGrid = new JLabel[rows][cols];

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

    private void updateCellAppearance(JLabel cell, char type) {
        cell.setIcon(null); // clear previous icon

        switch (type) {
            case 'P' -> {
                cell.setIcon(PLAYER_ICON);
                cell.setBackground(Color.WHITE);
            }
            case 'T' -> {
                cell.setIcon(TREASURE_ICON);
                cell.setBackground(Color.WHITE);
            }
            case 'X' -> {
                cell.setIcon(TRAP_ICON);
                cell.setBackground(Color.WHITE);
            }
            default -> cell.setBackground(getColorFor(type));
        }
    }


    // Displays player info such as remaining lives
    public JPanel infoPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        lives = new JLabel("Lives: " + LOGIC.getLives());
        panel.add(lives);
        return panel;
    }

    // Refreshes all squares after movement or state changes
    public void refreshMaze() {
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

    private ImageIcon loadScaledIcon(String path, int width, int height) {
        Image img = new ImageIcon(path).getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

}