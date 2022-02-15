package View;

import View.GUI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * View.Background represents method for background of application GUI.GUI.
 *
 * @author TimotejBarbus
 */
public class Background{

    public Background() {

    }

    /**
     *  Creates background behind menu and clock.
     */
    public static void generateBackground() {
        Rectangle background = new Rectangle();
        background.setWidth(GUI.TILESIZE * 8);
        background.setHeight(GUI.TILESIZE * 8 + GUI.MENU + GUI.CLOCK);
        background.setFill(Color.rgb(24, 59, 125));
        GUI.backgroundGroup.getChildren().add(background);
    }
}
