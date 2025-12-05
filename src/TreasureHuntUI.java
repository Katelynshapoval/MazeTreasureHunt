import javax.swing.*;
import java.awt.*;

public class TreasureHuntUI {
    //Constants
    private final TreasureHuntLogic LOGIC;

    // Constructor
    public TreasureHuntUI(TreasureHuntLogic logic) {
        this.LOGIC = logic;

        // Main frame
        JFrame frame = new JFrame("MasterMind");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        System.out.println(LOGIC.generateMaze().toString());
        frame.setSize(400, 600);

        frame.setVisible(true);
    }

    public JPanel generateMaze() {
        int rows = LOGIC.getRows();
        int cols = LOGIC.getCols();
        JPanel maze = new JPanel(new GridLayout(rows, cols, 10, 10));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                continue;
            }

        }
        return maze;
    }
}
