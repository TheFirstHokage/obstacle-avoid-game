package knight.obstacleavoidreload;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import knight.obstacleavoidreload.screen.game.GameScreen;
import knight.obstacleavoidreload.screen.loading.LoadingScreen;

public class ObstacleAvoidGame extends Game {

	private AssetManager assetManager;
	private SpriteBatch batch;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		assetManager.getLogger().setLevel(Logger.DEBUG);
		setScreen(new LoadingScreen(this));

	}


	@Override
	public void dispose() {
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
