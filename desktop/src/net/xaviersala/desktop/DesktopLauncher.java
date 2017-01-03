package net.xaviersala.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.xaviersala.PrincesetaGame;

public class DesktopLauncher {
  public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    // Dimensions de l'escriptori
    config.title = "Princeseta";
    config.width = 800;
    config.height = 480;
    new LwjglApplication(new PrincesetaGame(), config);
  }
}
