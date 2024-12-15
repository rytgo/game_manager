module com.test {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires java.compiler;

    exports com.game;
    exports com.game.SnakeGame;
    exports com.game.blackjack;
}
