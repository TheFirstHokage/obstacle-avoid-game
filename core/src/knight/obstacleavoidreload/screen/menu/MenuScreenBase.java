package knight.obstacleavoidreload.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.util.GdxUtils;

public abstract class MenuScreenBase extends ScreenAdapter {

    //changing from priv. to protected
    //so child class have access
    protected final ObstacleAvoidGame game;
    protected final AssetManager assetManager;


    private Viewport viewport;
    private Stage stage;


    public MenuScreenBase(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }


    @Override
    public void show() {
        //init vars in show
        //stage calls projection matrix
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        //OrthographicCamera camera= (OrthographicCamera) viewport.getCamera();
        stage = new Stage(viewport, game.getBatch());//creates batch internally
        Gdx.input.setInputProcessor(stage);

       // initUI();
        stage.addActor(createUi());
    }

    protected abstract Actor createUi();

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
