package knight.obstacleavoidreload.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import knight.obstacleavoidreload.assets.AssetPaths;
import knight.obstacleavoidreload.config.DifficultyLevel;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.entity.Obstacle;
import knight.obstacleavoidreload.entity.Player;
import knight.obstacleavoidreload.util.GdxUtils;
import knight.obstacleavoidreload.util.ViewportUtils;
import knight.obstacleavoidreload.util.debug.DebugCameraController;

@Deprecated
public class GameScreenOld implements Screen {


    private static final Logger log = new Logger(GameScreenOld.class.getName(), Logger.DEBUG);


    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private DebugCameraController cameraController;
    private Array<Obstacle> obstacleArray = new Array<>();
    private float obstacleTimer;
    private int lives = GameConfig.LIVES_START;
    //private boolean isAlive = true;

    ///ADDING HUD TO SCREEN
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    //set to final, in each frame set text to glayout
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private float scoreTimer;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.HARD;

    //change create to show
    @Override
    public void show () {
     camera = new OrthographicCamera();
     viewport= new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
     shapeRenderer = new ShapeRenderer();


     hudCamera = new OrthographicCamera();
     hudViewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT,hudCamera);
     batch = new SpriteBatch();
     font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));

     player = new Player();


     //calc position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
        float startPlayerY = 1;

        player.setPosition(startPlayerX,startPlayerY);
        cameraController = new DebugCameraController();
        cameraController.setStartPosition(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);

    }

    //add param
    @Override
    public void render (float delta) {
        //dont put inside if stmt so i can still move or control cam after player dies
        cameraController.handleDebugInput(delta);
        cameraController.applyTo(camera);
        //updates world- making changes using an if stmt=only upd wrld if player alive


            update(delta);


        GdxUtils.clearScreen();

        //render HUD/UI
        renderUI();


        //renders the player
        renderDebug();


    }

    @Deprecated
    public void update(float delta) {

        if (isGameOver()) {
            log.debug("Game Over!!");
            return;

        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);


        if (isPlayerTouchingObstacle()) {
            log.debug("Collision Detected player should be dead");
            lives--;
        }



    }

    private boolean isGameOver(){
        return lives <= 0;
    }

    private boolean isPlayerTouchingObstacle() {
        for (Obstacle obstacle: obstacleArray) {
            if (obstacle.isNotHit() && obstacle.isPlayerTouching(player) ){
                return true;
            }
        }

        return false;
    }




    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1,5);
            scoreTimer = 0.0f;
        }


    }


    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score,displayScore +(int) (60*delta));
        }

    }

    private void updatePlayer() {
        //log.debug("playerX = "+player.getX()+ "PlayerY= "+player.getY());
        //player.update();
        blockPlayerFromLeavingWorld();
    }

    private void blockPlayerFromLeavingWorld() {
        //description: if the center reaches 5.6 it means the edge of circle has
        // touched the world width so set the position to that so it cant move further
        //float playerX = player.getX();
//        if (playerX < player.getWidth() / 2f){
//            playerX = player.getWidth() /2f;
//        }else if (playerX > GameConfig.WORLD_WIDTH - player.getWidth()/2f) {
//            playerX = GameConfig.WORLD_WIDTH- player.getWidth()/2f;
//        }

        float playerX = MathUtils.clamp(player.getX(),player.getWidth()/2f,GameConfig.WORLD_WIDTH - player.getWidth()/2f);
        player.setPosition(playerX,player.getY());


    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle: obstacleArray) {
            obstacle.update();
        }
        createNewObstacle(delta);
    }
    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME){
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min,max);
            float obstacleY = GameConfig.WORLD_HEIGHT;
        Obstacle obstacle = new Obstacle();
        obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
        obstacle.setPosition(obstacleX,obstacleY);
        obstacleArray.add(obstacle);
        obstacleTimer = 0f;
        }

    }

    private void renderUI() {
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: "+lives;

        glyphLayout.setText(font,livesText);
        //fonts renedered from top left corner textures r bottom left
        font.draw(batch,livesText,20,GameConfig.HUD_HEIGHT -glyphLayout.height);

        String scoreText = "SCORE: "+displayScore;
        glyphLayout.setText(font,scoreText);
        font.draw(batch,scoreText,GameConfig.HUD_WIDTH - glyphLayout.width -20,
                GameConfig.HUD_HEIGHT -glyphLayout.height);

        batch.end();
    }

    private void renderDebug() {

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        shapeRenderer.end();

        ViewportUtils.drawGrid(viewport,shapeRenderer);
    }

    private void drawDebug() {
       player.drawDebug(shapeRenderer);
        for (Obstacle obstacle: obstacleArray) {
            obstacle.drawDebug(shapeRenderer);
        }
    }



    @Override
    public void dispose () {
        //dispose vars before init
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();

    }


    @Override
    public void resize(int width, int height) {

        viewport.update(width,height,true);
        //must do this when adding a camera
        hudViewport.update(width, height,true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //called when switching screens
    @Override
    public void hide() {
dispose();
    }
}
