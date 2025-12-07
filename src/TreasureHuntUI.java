import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TreasureHuntUI {

    private final TreasureHuntLogic LOGIC;
    private JLabel[][] mazeGrid;
    private JPanel mazePanel;
    private JLabel lives;

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
                    LOGIC.movePlayer(key);
                    refreshMaze();
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
        cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cell.setBackground(getColorFor(type));
        return cell;
    }

    // Maps maze characters to UI colors
    private Color getColorFor(char ch) {
        return switch (ch) {
            case '.' -> Color.WHITE; // Neutral
            case '#' -> Color.BLACK; // Wall
            case 'X' -> Color.RED; // Trap
            case 'P' -> Color.GREEN; // Player
            case 'T' -> Color.ORANGE; // Treasure
            default -> Color.GRAY;
        };
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
                mazeGrid[r][c].setBackground(getColorFor(maze[r][c]));
            }
        }
        // Update lives
        lives.setText("Lives: " + LOGIC.getLives());
        mazePanel.repaint();
    }
}