package knight.obstacleavoidreload.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.assets.RegionNames;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.screen.game.GameScreen;
import knight.obstacleavoidreload.util.GdxUtils;

public class MenuScreen extends MenuScreenBase {

    private static final Logger log =
            new Logger(MenuScreen.class.getName(), Logger.DEBUG);

//    private final ObstacleAvoidGame game;
//    private final AssetManager assetManager;
//    private Viewport viewport;
//    private Stage stage;

    public MenuScreen(ObstacleAvoidGame game) {
      super(game);

    }

//    @Override
//    public void show() {
//        //init vars in show
//        //stage calls projection matrix
//        viewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
//        //OrthographicCamera camera= (OrthographicCamera) viewport.getCamera();
//        stage = new Stage(viewport,game.getBatch());//creates batch internally
//        Gdx.input.setInputProcessor(stage);
//
//        initUI();
//    }

//    @Override
//    public void render(float delta) {
//        GdxUtils.clearScreen();
//        stage.act();
//        stage.draw();
//    }

//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height,true);
//    }


    @Override
    protected Actor createUi() {
        Table table = new Table();
       //TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
        // TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);


        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));
      //  ImageButton playButton = createButton(uiAtlas,RegionNames.PLAY,RegionNames.PLAY_PRESSED);


        TextButton playButton = new TextButton("PLAY",uiskin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        //ImageButton highScoreButton = createButton(uiAtlas,RegionNames.HIGH_SCORE,RegionNames.HIGH_SCORE_PRESSED);
        TextButton highScoreButton = new TextButton("HIGHSCORE",uiskin);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });
        TextButton quitButton = new TextButton("QUIT HOE",uiskin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    quit();
            }
        });
        TextButton optionsButton = new TextButton("OPTIONS",uiskin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        //setup table
        Table buttonTable = new Table(uiskin);//pass skin to table to make setBackG method w string work..easy
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(RegionNames.PANEL);//tex reg drawable

        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionsButton).row();
        buttonTable.add(quitButton).row();


        buttonTable.center();
        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();
        //stage.addActor(table);
        return table;
    }


    private void play(){
        log.debug("play");
        game.setScreen(new GameScreen(game));


    }
    private void showHighScore() {
        log.debug("show highscore");
        game.setScreen(new HighScoreScreen(game));

    }
    private void showOptions() {

        log.debug("showHighScore");
        game.setScreen(new OptionsScreen(game));
    }

    private static ImageButton createButton(TextureAtlas atlas,String upName, String downName) {

        TextureRegion upRegion = atlas.findRegion(upName);
        TextureRegion downRegion = atlas.findRegion(downName);

        return new ImageButton(
                new TextureRegionDrawable(upRegion),
                new TextureRegionDrawable(downRegion)
        );

    }


    @Override
    public void hide() {
        dispose();
    }

//    @Override
//    public void dispose() {
//        stage.dispose();
//    }


    private void quit(){
        log.debug("quit");
        Gdx.app.exit();
    }
}
