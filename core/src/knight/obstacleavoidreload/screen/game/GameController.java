package knight.obstacleavoidreload.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.common.GameManager;
import knight.obstacleavoidreload.config.DifficultyLevel;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.entity.Background;
import knight.obstacleavoidreload.entity.Obstacle;
import knight.obstacleavoidreload.entity.Player;

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(),
            Logger.DEBUG);

    private Player player;
    private float scoreTimer;
    private int score;
    private int displayScore;
   // private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;
    private Array<Obstacle> obstacleArray = new Array<>();
    private float obstacleTimer;
    private int lives = GameConfig.LIVES_START;
    private Pool<Obstacle> obstaclePool;
    private Background background;
    private float startPlayerX ;
    private float startPlayerY ;
    private Sound hit;
    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;
    public GameController(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        init();
    }


    private void init() {

        player = new Player();
        //calc position
        float halfPlayerSize = GameConfig.PLAYER_SIZE/2f;
         startPlayerX = (GameConfig.WORLD_WIDTH- GameConfig.PLAYER_SIZE)/2f;  //GameConfig.WORLD_WIDTH/2f - halfPlayerSize;
         startPlayerY = 1 - GameConfig.PLAYER_SIZE/2f;//halfPlayerSize;
        player.setPosition(startPlayerX,startPlayerY);

        obstaclePool = Pools.get(Obstacle.class,40);
        background = new Background();
        background.setPosition(0,0);
        background.setSize(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT);
        hit = assetManager.get(AssetDescriptors.HIT_SOUND);

    }
    //updates world- making changes using an if stmt=only upd wrld if player alive

    public void update(float delta) {

        if (isGameOver()) {

            return;

        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);


        if (isPlayerTouchingObstacle()) {
            log.debug("Collision Detected player should be dead");
            lives--;
            if (isGameOver()) {
                GameManager.INSTANCE.updateHighScore(score);
                log.debug("Game Over!!");
            }else {
                restart();
            }
        }


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



        //This is player.update code
            float xSpeed = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
            }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
            }

            player.setX(player.getX() + xSpeed);
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

        float playerX = MathUtils.clamp(player.getX(),
                0,
                GameConfig.WORLD_WIDTH - player.getWidth());
        player.setPosition(playerX,player.getY());


    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle: obstacleArray) {
            obstacle.update();
        }
        createNewObstacle(delta);
        removedPassedObstacles();


    }
    private void createNewObstacle(float delta) {
        obstacleTimer += delta;


        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME){
            float min = 0;//GameConfig.OBSTACLE_SIZE/2f;
            float max = GameConfig.WORLD_WIDTH- GameConfig.OBSTACLE_SIZE; ///2f;
            float obstacleX = MathUtils.random(min,max);
            float obstacleY = GameConfig.WORLD_HEIGHT;
            Obstacle obstacle = obstaclePool.obtain();

            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX,obstacleY);
            obstacleArray.add(obstacle);
            obstacleTimer = 0f;
        }

    }

    private void removedPassedObstacles(){

        if (obstacleArray.size > 0) {
            Obstacle first = obstacleArray.first();
            //whenever obs center reaches -0.6 not 0 it is removes
            float minObstacleY = -GameConfig.OBSTACLE_SIZE;
            if (first.getY() < minObstacleY) {
                obstacleArray.removeValue(first,true);
                obstaclePool.free(first);
            }
        }

    }


    private boolean isPlayerTouchingObstacle() {
        for (Obstacle obstacle: obstacleArray) {
            if (obstacle.isNotHit() && obstacle.isPlayerTouching(player) ){
                hit.play();
                return true;
            }
        }

        return false;
    }



    private void restart() {
        obstaclePool.freeAll(obstacleArray);
        obstacleArray.clear();
        player.setPosition(startPlayerX,startPlayerY);

    }

    public Player getPlayer() {
        return player;
    }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public Array<Obstacle> getObstacleArray() {
        return obstacleArray;
    }

    public Background getBackground() {
        return background;
    }

    public boolean isGameOver(){
       // return false;
        return lives <= 0;
    }

}
