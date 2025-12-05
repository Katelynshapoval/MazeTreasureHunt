import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Create logic
        TreasureHuntLogic logic = new TreasureHuntLogic();

        // Create UI
        SwingUtilities.invokeLater(() -> {
            new TreasureHuntUI(logic);
        });
    }
}