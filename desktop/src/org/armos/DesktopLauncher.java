package org.armos;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.armos.SpaceAttack;

import static org.armos.Constants.HEIGHT;
import static org.armos.Constants.WIDTH;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WIDTH, HEIGHT);
		config.setTitle("Space Attack");
		config.setResizable(false);
		new Lwjgl3Application(new SpaceAttack(), config);
	}
}
