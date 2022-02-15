package View;

import Game.Clock;
import Game.Main;
import Menus.Menu;
import Moves.Move;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * GUI.GUI represents method for graphical methods called on start.
 *
 * @author TimotejBarbus
 */
public class GUI extends Application {
    public static int WIDTH = 8;
    public static int HEIGHT = 8;
    public static int TILESIZE = 62;
    public static int CLOCK = 50;
    public static int MENU = 50;
    public static Group backgroundGroup = new Group();
    public static Group menuGroup = new Group();
    public static Group clockGroup = new Group();
    public static Group tileGroup = new Group();
    public static Group figuresGroup = new Group();
    public static Group checkMateGroup = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Calls all graphical methods required for start of the game.
     *
     * @return returns GUI.GUI
     */
    public Parent generateGUI() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILESIZE, HEIGHT * TILESIZE + CLOCK + MENU);

        root.getChildren().addAll(backgroundGroup);
        root.getChildren().addAll(menuGroup);
        root.getChildren().addAll(clockGroup);
        root.getChildren().addAll(tileGroup);
        root.getChildren().addAll(figuresGroup);
        root.getChildren().addAll(checkMateGroup);

        Background.generateBackground();
        Menu.generateMenu();
        Clock.generateClock();
        Tile.generateBoard();
        Figure.generateFigures();

        return root;
    }

    /**
     * Creates window for application.
     * Creates event when closing this window.
     *
     * @param stage represents window of application
     */
    public void start(Stage stage) {
        Scene scene = new Scene(generateGUI());
        stage.setScene(scene);

        stage.setTitle("Timotej_Barbuš_Chess");
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            if(Move.inGame) {
                Menu.clock.stop();
            }
            if(Main.serverCreated) {
                Main.server.stop();
            }
            System.out.println("Game ends.");
        });
    }

}

