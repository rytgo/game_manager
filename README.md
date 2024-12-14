# Game Manager

## Overview

This project implements a Game Manager using JavaFX to manage two fully functional games: Blackjack and Snake. The Game Manager incorporates persistent storage for user accounts and high scores, leveraging Object-Oriented Programming (OOP) principles and graphical frontends. It allows users to create accounts, log in, and view high scores, ensuring data persistence across sessions. The project also provides a unified toolbar accessible across the application.

## Design

The project follows OOP principles:

- **Encapsulation**: All game logic and data are encapsulated in their respective classes.
    
- **Abstraction**: Separate responsibilities for classes like `GameManager`, `HighScoresManager`, and game-specific logic (e.g., `Blackjack`, `SnakeGame`).
    
- **Polymorphism and Inheritance**: Shared functionality among players (human, computer, and dealer) is implemented through inheritance.

### Important Classes and Structure
#### Game Manager

- **MainMenu**: Displays the main menu, high scores, and provides access to the games.
    
- **HighScoresManager**: Handles loading, saving, and updating high scores.
    
- **LoginManager**: Manages user account creation and login validation.

#### Blackjack

- **Blackjack**: Core game logic, including dealing cards, managing turns, and calculating results.
    
- **BlackjackUI**: Frontend for the Blackjack game, handling user interaction and game state display.
    
- **Deck**: Represents a standard 52-card deck, with shuffle and deal functionality.
    
- **Player**: Base class for human and computer players.
    
- **Dealer**: Specialized `Player` with unique rules.

#### Snake

- **SnakeGame**: Core game logic for Snake, including movement, food generation, and collision detection.
    
- **Snake**: Represents the snake, handling movement and growth.
    
- **Food**: Handles food generation and positioning on the grid.
## Installation Instructions

To run the Game Manager, follow these steps:

1. **Prerequisites**: Ensure that you have a Java Development Kit (JDK) installed on your machine. Java 8 or later is required to run this project.
    
2. **Cloning the Project**: Clone the project from the repository or download the source code.
    
    `git clone https://github.com/rytgo/game_manager`
    
3. **Open the Project** using your desired text editor or IDE.

4. **Build the Project**: In our case, VSCode will automatically build the project when you save files.
    
5. **Running the Project**: Run the `App` class to start the application.
    
    

## Usage

### Game Manager

1. Launch the application.
    
2. Create an account or log in using existing credentials.
    
3. View high scores and select a game to play.
    

### Blackjack

1. From the Game Manager, select "Play Blackjack."
    
2. Start a new game or load a previously saved game using a save state string.
    
3. Follow the on-screen prompts to place bets, hit, or stand.
    
4. The game ends when a winner is determined. Progress can be saved and resumed later.
    

### Snake Game

1. From the Game Manager, select "Play Snake."
    
2. Use arrow keys to control the snake and collect food.
    
3. Avoid collisions with walls and the snake’s body.
    
4. Pause the game with the `ESC` key and resume as needed.
    
5. The game ends when the snake collides. The final score is displayed, and high scores are updated if applicable.

## Contributions

Each team member contributed to the overall design and testing of the system. Project integration and members assisted each other between all classes, but the description is a broad overview.

This project was developed as a collaborative effort among the following team members:

- **[Uyen Pham]**: Development of the Blackjack game, integration of logic, and its UI. Significant amount of bug fixing and code refactoring. 
- **[Kundyz Serzhankyzy]**: Responsible for development of the Snake game, including its logic of snake movement, food, collisions, and displaying scores. Wrote JUnit tests.
- **[Ryan Tran]**: Worked on login logic, game manager, its UI, high score logic, and encryption.
