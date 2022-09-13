package knight.obstacleavoidreload.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {

    private static final Logger log = new Logger(DebugCameraController.class.getName(), Logger.DEBUG);

    //CONSTANTS- DEFAULT CONTROLS FOR CAMERA DIRECTION
//    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
//    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
//    private static final int DEFAULT_UP_KEY = Input.Keys.W;
//    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
//
//    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA;
//    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD;
//    private static final int DEFAULT_RESET_KEY = Input.Keys.BACKSPACE;
//    private static final int DEFAULT_LOG_KEY = Input.Keys.ENTER;
//
//    private static final float DEFAULT_MOVE_SPEED = 20.0f;
//    private static final float DEFAULT_ZOOM_SPEED = 2.0f;
//    private static final float DEFAULT_MAX_ZOOM_IN =.20f;
//    private static final float DEFAULT_MAX_ZOOM_OUT= 30f;







    //attributes-position of controller hence position of camera
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;
    private DebugCameraConfig config;

    public DebugCameraController() {
        config = new DebugCameraConfig();
        log.info("cameraConfig ="+config);
    }
//public apis
    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);


    }

    //applies position of controller to camera
    public void applyTo(OrthographicCamera camera) {
        camera.zoom = zoom;
        camera.position.set(position,0);
        camera.update();//recalculates projection matrix
    }

    //called every frame- delta
    public void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = config.getMoveSpeed() *delta;
        float zoomSpeed = config.getZoomSpeed()* delta;

        if (config.isLeftPressed()) {
            moveLeft(moveSpeed);
        }else if (config.isRightPressed()) {
            moveRight(moveSpeed);
        }else if (config.isUpPressed()) {
            moveUp(moveSpeed);

        }else if (config.isDownPressed()){
            moveDown(moveSpeed);
        }
        //zoom controls
        if (config.isZoomInPressed()) {
            zoomIn(zoomSpeed);
        }else if (config.isZoomOutPressed()){
            zoomOut(zoomSpeed);
        }

        if (config.isResetPressed()) {
            resetCamera();
        }

        //
        if (config.isLogPressed()) {
            logDebug();
        }

    }






    //private methods

    private void setPosition(float x, float y) {
        position.set(x, y);
    }
    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x +xSpeed, position.y +ySpeed);

    }

    private void moveLeft(float speed) {
        moveCamera(-speed,0);
    }

    private void moveRight(float speed) {
        moveCamera(speed,0);
    }

    private void moveUp(float speed) {
        moveCamera(0,speed);
    }

    private void moveDown(float speed) {
        moveCamera(0,-speed);
    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value,config.getMaxZoomIn(),config.getMaxZoomOut());
    }


    private void zoomIn(float zoomSpeed) {
        setZoom(zoom+ zoomSpeed);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void  resetCamera() {
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void logDebug() {
        log.debug("position= "+position+" zoom ="+zoom);

    }







}
