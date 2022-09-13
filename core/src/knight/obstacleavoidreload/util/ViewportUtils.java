package knight.obstacleavoidreload.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {
    private static final int DEFAULT_CELL_SIZE = 1;
    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer) {

        drawGrid(viewport, shapeRenderer,DEFAULT_CELL_SIZE);
     }

     public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cellSize) {
         if (viewport == null) {
             throw new IllegalArgumentException("viewport required");
         }
         if (shapeRenderer == null) {
             throw new IllegalArgumentException("renderer required ");
         }
         if (cellSize < DEFAULT_CELL_SIZE) {
                cellSize = DEFAULT_CELL_SIZE;


         }
         Color oldColor = new Color(shapeRenderer.getColor());

         int worldWidth = (int)viewport.getWorldWidth();
         int worldHeight = (int) viewport.getWorldHeight();
         int doubleWorldWidth = worldWidth*2;
         int doubleWorldHeight = worldHeight *2;
         shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
         shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
         shapeRenderer.setColor(Color.WHITE);


         //draws vertical lines
         for (int x= -doubleWorldWidth; x < doubleWorldWidth; x+= cellSize) {
             shapeRenderer.line(x,-doubleWorldHeight,x,doubleWorldHeight);

         }
         //draw horizontal lines
         for (int y= -doubleWorldHeight; y < doubleWorldHeight;y+= cellSize){
                shapeRenderer.line(-doubleWorldWidth,y,doubleWorldWidth,y);
         }
         //draw xy axis lines
         shapeRenderer.setColor(Color.RED);
         shapeRenderer.line(0,-doubleWorldHeight,0,doubleWorldHeight);
         shapeRenderer.line(-doubleWorldWidth,0,doubleWorldWidth,0);

         //draw world bounds
         shapeRenderer.setColor(Color.GREEN);
         shapeRenderer.line(0,worldHeight,worldWidth,worldHeight);
         shapeRenderer.line(worldWidth,0,worldWidth,worldHeight);
         shapeRenderer.end();
         shapeRenderer.setColor(oldColor);




      }

      public static void debugPixelPerUnit(Viewport viewport) {
        if (viewport == null) {
            throw new IllegalArgumentException("viewport reqiured");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth/worldWidth;
        float yPPU = screenHeight/ worldHeight;


        log.debug("x ppu: "+xPPU+", y ppu: "+yPPU);



      }

}
