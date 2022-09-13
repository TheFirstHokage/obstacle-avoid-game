package knight.obstacleavoidreload.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.screen.menu.MenuScreen;

public class GameScreen implements Screen {

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private GameController gameController;
    private GameRenderer gameRenderer;


    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }


    @Override
    public void show() {
//        waitMillis(800);
//        assetManager.load(AssetDescriptors.FONT);
//        waitMillis(800);
//        assetManager.load(AssetDescriptors.GAME_PLAY);
//        //before asset packer
//        waitMillis(800);
////        assetManager.load(AssetDescriptors.OBSTACLE);
////        assetManager.load(AssetDescriptors.PLAYER);
//        assetManager.finishLoading();
        gameController = new GameController(game);
        gameRenderer = new GameRenderer(game.getBatch(),assetManager,gameController);


    }


    @Override
    public void render(float delta) {

        gameController.update(delta);
        gameRenderer.render(delta);

        //could be put in update but
        //that would change screen before render is called
        if (gameController.isGameOver()){
            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
