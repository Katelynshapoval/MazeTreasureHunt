import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        int maze_rows = 10;
        int maze_cols = 15;

        // Create logic
        TreasureHuntLogic logic = new TreasureHuntLogic(maze_rows, maze_cols);

        // Create UI
        SwingUtilities.invokeLater(() -> {
            new TreasureHuntUI(logic);
        });
    }
}