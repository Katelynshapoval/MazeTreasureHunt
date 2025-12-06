import javax.swing.*;
import java.awt.*;

public class TreasureHuntUI {
    //Constants
    private final TreasureHuntLogic LOGIC;
    private JLabel[][] mazeGrid;

    // Constructor
    public TreasureHuntUI(TreasureHuntLogic logic) {
        this.LOGIC = logic;

        // Main frame
        JFrame frame = new JFrame("MasterMind");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Maze
        frame.add(drawMaze(), BorderLayout.NORTH);
        // Info
        frame.add(infoPanel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public JPanel drawMaze() {
        int rows = LOGIC.getRows();
        int cols = LOGIC.getCols();
        JPanel maze = new JPanel(new GridLayout(rows, cols, 10, 10));
        maze.setSize(500, 500);
        char[][] generatedMaze = LOGIC.generateMaze();
        mazeGrid = new JLabel[LOGIC.getRows()][LOGIC.getCols()];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JLabel cell = new JLabel();
                cell.setPreferredSize(new Dimension(30, 30));
                cell.setOpaque(true);
                switch (generatedMaze[r][c]) {
                    case '.' -> cell.setBackground(Color.WHITE);
                    case '#' -> cell.setBackground(Color.BLACK);
                    case 'X' -> cell.setBackground(Color.RED);
                    case 'P' -> cell.setBackground(Color.GREEN);
                    case 'T' -> cell.setBackground(Color.ORANGE);
                }

                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                mazeGrid[r][c] = cell;
                maze.add(cell);
            }

        }
        return maze;
    }

    public JPanel infoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout());
        JLabel hearts = new JLabel("3");
        infoPanel.add(hearts);
        return infoPanel;
    }
}
