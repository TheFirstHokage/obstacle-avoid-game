package knight.obstacleavoidreload.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import knight.obstacleavoidreload.config.GameConfig;

public class Player extends GameObjectBase {

    //position of player
//    private float x;
//    private float y;

    //player body used for collision detection-repeseents where the player is

//    private static final float BOUNDS_RADIUS = 0.4f;//world unit
//    private static final float SIZE = 2 * BOUNDS_RADIUS;

    public Player(){
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE,GameConfig.PLAYER_SIZE);
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
