package knight.obstacleavoidreload.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.screen.game.GameScreen;
import knight.obstacleavoidreload.screen.menu.MenuScreen;
import knight.obstacleavoidreload.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH /2f;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private boolean changeScreen;
    private float progress;
    private float waitTime = 0.75f;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }


    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT,camera);
        renderer = new ShapeRenderer();
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.UI);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.HIT_SOUND);
    }

    @Override
    public void render(float delta) {
        update(delta);
        GdxUtils.clearScreen();
        viewport.apply();//when using multiple vps use apply
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        renderer.end();

            if (changeScreen) {
                game.setScreen(new MenuScreen(game));
            }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
    }



    @Override
    public void hide() {
        //screens dont dispose they hide
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private static void waitMillis(long mil) {
        try {
            Thread.sleep(mil);
        }catch (InterruptedException e) {

        }
    }

    private void update(float delta){
      // waitMillis(400);
       //progress is btwn 0 and 1
       progress = assetManager.getProgress();

       // update returns true if all assets are loaded
       if (assetManager.update()) {
           waitTime -= delta;
           if (waitTime <= 0) {
              changeScreen = true;// game.setScreen(new GameScreen(game));//when setting new screen hide is called on old screen and show on new
           }
       }
    }

    private void draw() {
        float progressBarX  = (GameConfig.HUD_WIDTH -PROGRESS_BAR_WIDTH)/2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT)/2f;

    renderer.rect(progressBarX,progressBarY,
            progress*PROGRESS_BAR_WIDTH,PROGRESS_BAR_HEIGHT);

    }



}
