import java.util.Random;

public class TreasureHuntLogic {
    private final int ROWS;
    private final int COLS;
    private char[][] maze;
    private int playerRow;
    private int playerCol;
    private int lives;

    // Constructor
    public TreasureHuntLogic(int rows, int cols, int lives) {
        this.ROWS = rows;
        this.COLS = cols;
        this.lives = lives;
    }

    // Get rows
    public int getRows() {
        return ROWS;
    }

    // Get cols
    public int getCols() {
        return COLS;
    }

    // A maze with walls, player and a treasure
    public char[][] generateMaze() {
        maze = new char[ROWS][COLS];
        Random rand = new Random();

        // Generating an empty maze
        for (int r = 0; r < ROWS; r++) {
            int trapCol = rand.nextInt(COLS - 1) + 1;
            for (int c = 0; c < COLS; c++) {
                char pos = ' ';
                if (checkIfWall(r, c)) {
                    pos = '#';
                } else if (trapCol == c) {
                    pos = 'X';
                } else {
                    pos = '.';
                }
                maze[r][c] = pos;
            }
        }
        // Add treasure
        int[] treasurePos = getRandomEmptyPosition();
        maze[treasurePos[0]][treasurePos[1]] = 'T';

        // Add player
        int[] playerPos = getRandomEmptyPosition();
        maze[playerPos[0]][playerPos[1]] = 'P';

        playerRow = playerPos[0];
        playerCol = playerPos[1];

        return maze;
    }

    // Move player (W/A/S/D)
    public String movePlayer(char direction) {
        int newRow = playerRow;
        int newCol = playerCol;

        String result = "";

        switch (direction) {
            case 'W' -> newRow--;
            case 'S' -> newRow++;
            case 'A' -> newCol--;
            case 'D' -> newCol++;
        }

        char curPos = maze[newRow][newCol];
        if (curPos != '#') {
            if (curPos == 'X') {
                lives--;
                result = "trap";
            } else if (curPos == 'T') {
                result = "treasure";
            }
            // Clear old position
            maze[playerRow][playerCol] = '.';
            // Update new position
            playerRow = newRow;
            playerCol = newCol;
            maze[playerRow][playerCol] = 'P';
        }

        return result;
    }


    // Check if the coordinates correspond to a wall
    private boolean checkIfWall(int row, int col) {
        return row == 0 || row == ROWS - 1 || col == 0 || col == COLS - 1;
    }

    // Get a random position for player/treasure positioning
    private int[] getRandomEmptyPosition() {
        int row, col;
        Random rand = new Random();
        do {
            row = rand.nextInt(ROWS - 1) + 1; // 1 to ROWS-1
            col = rand.nextInt(COLS - 1) + 1; // 1 to COLS-1
        } while (maze[row][col] != '.');
        return new int[]{row, col};
    }

    // Get current maze
    public char[][] getCurrentState() {
        return maze;
    }

    // Get current lives
    public int getLives() {
        return lives;
    }

}
