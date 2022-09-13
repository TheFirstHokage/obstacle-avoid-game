package knight.obstacleavoidreload.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.assets.AssetPaths;
import knight.obstacleavoidreload.assets.RegionNames;
import knight.obstacleavoidreload.common.GameManager;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.util.GdxUtils;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log = new Logger(HighScoreScreen.class.getName(),Logger.DEBUG);

//    private final ObstacleAvoidGame game;
//    private final AssetManager assetManager;
//    private Viewport viewport;
//    private Stage stage;


    public HighScoreScreen(ObstacleAvoidGame game) {
      super(game);

    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        //simply getting atlas files that contain textures(assets)
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        //TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
       // BitmapFont font = assetManager.get(AssetDescriptors.FONT);


        //get certain images from regions in atlas
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        //        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);
//        TextureRegion backRegion = uiAtlas.findRegion(RegionNames.BACK);
//        TextureRegion backPressedRegion = uiAtlas.findRegion(RegionNames.BACK_PRESSED);


       //Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        //background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //hi score txt
        Label highscoreText = new Label("HIGHSCORE",uiSkin);

        //hi score label
        String highScoreScreen =GameManager.INSTANCE.getHighScoreString();
        Label highscoreLabel = new Label(highScoreScreen, uiSkin);

        //back to home -- forces button to change color when pressed
//        ImageButton backButton = new ImageButton(
//                new TextureRegionDrawable(backRegion),
//                new TextureRegionDrawable(backPressedRegion)
//        );
        TextButton backButton = new TextButton("BACK",uiSkin);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();// what do you want to happen when button changes
                //change listener listens for change
            }
        });

        //setup tables

        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(20);
        contentTable.setBackground(RegionNames.PANEL);  //(new TextureRegionDrawable(panelRegion));

        contentTable.add(highscoreText).row();
        contentTable.add(highscoreLabel).row();
        contentTable.add(backButton);
        contentTable.center();
        table.add(contentTable);//adding table to table
        table.center();
        table.setFillParent(true);
        table.pack();
        //stage.addActor(table);
        return table;
    }


    private void back(){
        log.debug("back");
        game.setScreen(new MenuScreen(game));
    }



//    @Override
//    public void render(float delta) {
//        GdxUtils.clearScreen();
//        stage.act();
//        stage.draw();
//    }

//    @Override
//    public void resize(int width, int height) {
//    viewport.update(width, height,true);
//    }
//
//    @Override
//    public void show() {
//        //init vars in show
//        viewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
//        stage = new Stage(viewport, game.getBatch());
//        Gdx.input.setInputProcessor(stage);
//
//        createUi();
//
//
//    }






    @Override
    public void hide() {
        dispose();
    }

//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
}
