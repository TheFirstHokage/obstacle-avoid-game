package knight.obstacleavoidreload.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.assets.AssetPaths;
import knight.obstacleavoidreload.assets.RegionNames;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.entity.Background;
import knight.obstacleavoidreload.entity.Obstacle;
import knight.obstacleavoidreload.entity.Player;
import knight.obstacleavoidreload.util.GdxUtils;
import knight.obstacleavoidreload.util.ViewportUtils;
import knight.obstacleavoidreload.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private DebugCameraController cameraController;
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private BitmapFont font;
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final GameController gameController;
    private TextureRegion playerRegion;
    private TextureRegion obstacleRegion;
    private TextureRegion backgroundRegion;//change from texture 2 txtRegion
    private final AssetManager assetManager;
    private final SpriteBatch batch;//drawn from bottom left corner


    public GameRenderer(SpriteBatch batch,AssetManager assetManager,GameController gameController) {
        this.assetManager =assetManager;
        this.gameController = gameController;
        this.batch =batch;
        init();
    }


    private void init(){
        camera = new OrthographicCamera();
        viewport= new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        shapeRenderer = new ShapeRenderer();
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT,hudCamera);
        //batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));
        cameraController = new DebugCameraController();
        cameraController.setStartPosition(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);
        //atlas is managed by asset manager
        TextureAtlas atlas = assetManager.get(AssetDescriptors.GAME_PLAY);


        playerRegion = atlas.findRegion(RegionNames.PLAYER);//assetManager.get(AssetDescriptors.PLAYER);//new Texture(Gdx.files.internal("gameplay/player.png"));
        obstacleRegion = atlas.findRegion(RegionNames.OBSTACLE);//assetManager.get(AssetDescriptors.OBSTACLE);
        backgroundRegion = atlas.findRegion(RegionNames.BACKGROUND);//assetManager.get(AssetDescriptors.BACKGROUND);

    }

    public void render(float delta) {

        batch.totalRenderCalls =0;
        //dont put inside if stmt so i can still move or control cam after player dies
        cameraController.handleDebugInput(delta);
        cameraController.applyTo(camera);

        if (Gdx.input.isTouched() && !gameController.isGameOver() ){

            Vector2 screenTouch = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));//use new kw if not it converts calculation it modifies the Vector not return new one
            System.out.println("Screen touched "+screenTouch);
            System.out.println("world touched "+worldTouch);

            Player player = gameController.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x,0,GameConfig.WORLD_WIDTH -player.getWidth());
            player.setX(worldTouch.x);

        }


        GdxUtils.clearScreen();


        renderGameplay();

        //render HUD/UI
        renderUI();


        //renders the player
        renderDebug();
        //System.out.println("total render calls ="+batch.totalRenderCalls);
    }

    private void renderGameplay(){
        viewport.apply();
         batch.setProjectionMatrix(camera.combined);//tells spritebatch about cam config
            batch.begin();

        //draw background
        Background background = gameController.getBackground();

        batch.draw(backgroundRegion,background.getX(),background.getY(),
                background.getWidth(),background.getHeight());


        //draw player
            Player player = gameController.getPlayer();
            batch.draw(playerRegion,player.getX(),player.getY(),
                    player.getWidth(),player.getHeight());

            //draw obstacle
            for(Obstacle obstacle : gameController.getObstacleArray()) {
                batch.draw(obstacleRegion,obstacle.getX(),obstacle.getY(),
                        obstacle.getWidth(),obstacle.getHeight());
            }



            batch.end();

    }

    private void renderUI() {
        //APPLIES viewport to this camera
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: "+gameController.getLives();

        glyphLayout.setText(font,livesText);
        //fonts renedered from top left corner textures r bottom left
        font.draw(batch,livesText,20,GameConfig.HUD_HEIGHT -glyphLayout.height);

        String scoreText = "SCORE: "+gameController.getDisplayScore();
        glyphLayout.setText(font,scoreText);
        font.draw(batch,scoreText,GameConfig.HUD_WIDTH - glyphLayout.width -20,
                GameConfig.HUD_HEIGHT -glyphLayout.height);

        batch.end();
    }

    private void renderDebug() {
        //simply apply a viewport to this camera
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        shapeRenderer.end();

        ViewportUtils.drawGrid(viewport,shapeRenderer);
    }

    private void drawDebug() {
        Player player = gameController.getPlayer();
        player.drawDebug(shapeRenderer);
        Array<Obstacle>obstacleArray = gameController.getObstacleArray();
        for (Obstacle obstacle: obstacleArray) {
            obstacle.drawDebug(shapeRenderer);
        }
    }


    public void resize(int width, int height) {

        viewport.update(width,height,true);
        //must do this when adding a camera
        hudViewport.update(width, height,true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        //dispose vars before init
        shapeRenderer.dispose();
        // batch.dispose();
//        font.dispose();
//        playerTexture.dispose();
//        obstacleTexture.dispose();
//        backgroundTexture.dispose();
    }
}
