package knight.obstacleavoidreload.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import knight.obstacleavoidreload.ObstacleAvoidGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.fullscreen = true;
//		config.vSyncEnabled =true;
		new LwjglApplication(new ObstacleAvoidGame(), config);
	}
}
