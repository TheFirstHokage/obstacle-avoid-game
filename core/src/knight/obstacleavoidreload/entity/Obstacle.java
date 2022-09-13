package knight.obstacleavoidreload.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

import knight.obstacleavoidreload.config.DifficultyLevel;
import knight.obstacleavoidreload.config.GameConfig;


//implement poolable for any obj i want to pool
//and i must have no args constructor when using pool
//use this when i will need to spawn lots of objects
//that i can just reuse
public class Obstacle extends GameObjectBase implements Pool.Poolable {



    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;
    // body used for collision detection-repesents where the obs is
//    private static final float BOUNDS_RADIUS = 0.3f;//world unit
//    public static final float SIZE = 2 * BOUNDS_RADIUS;
    public Obstacle(){
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE,GameConfig.OBSTACLE_SIZE);

    }

    public void update() {

       //setPosition(getX(),getY() - ySpeed);
        setY(getY() - ySpeed);
    }





    public boolean isPlayerTouching(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds,getBounds());
        hit = overlaps;
        return overlaps;

    }


    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }


    @Override
    public void reset() {
        hit = false;
    }


//    public float getWidth() {
//        return SIZE;
//    }
//
//    public float getHeight(){
//
//        return SIZE;
//    }
}


