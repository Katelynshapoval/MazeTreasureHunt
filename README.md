# Treasure Hunt Game (Java, Swing)

A simple maze-based treasure-hunting game built using Java and Swing.  
The player moves using W, A, S, D to navigate a randomly generated maze containing:

- Walls  
- Traps  
- Player  
- Treasure  

Your goal: find the treasure before losing all your lives.

---

## Features

### Random Maze Generation
- The maze layout, traps, player position, and treasure are generated randomly each game.
- Borders are always walls to ensure valid movement.

### Player Movement (W/A/S/D)
- W → Move Up  
- A → Move Left  
- S → Move Down  
- D → Move Right  
- Movement is blocked by walls.

### Game Events
- Stepping on a trap reduces lives.
- Reaching the treasure ends the game with a win.
- Losing all lives ends the game with a loss.
- Pop-up dialogs notify the player when important events occur.

### GUI (Swing)
- Maze displayed using a grid of `JLabel` components.
- Player, treasure, and trap icons rendered with scaled PNG images.
- Lives counter displayed below the maze.


## Project Structure

