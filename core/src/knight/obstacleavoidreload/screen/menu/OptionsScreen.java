package knight.obstacleavoidreload.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.assets.AssetDescriptors;
import knight.obstacleavoidreload.assets.RegionNames;
import knight.obstacleavoidreload.common.GameManager;
import knight.obstacleavoidreload.config.DifficultyLevel;
import knight.obstacleavoidreload.config.GameConfig;
import knight.obstacleavoidreload.screen.menu.MenuScreenBase;

/**
 * Created by goran on 3/09/2016.
 */
public class OptionsScreen extends MenuScreenBase {

    private static final Logger log = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    //Class that manages grp of btns
    //to enforce min and max num of checked btns
    //Checkbox is also btn it extends TextButton so it works out
    //it enables radio button functionality

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    //private Image checkMark;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }


    @Override
    protected Actor createUi() {
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        easy = checkBox(DifficultyLevel.EASY.name(), uiSkin);
        Label label = new Label("DIFFICULTY",uiSkin);
        //easy = new CheckBox(DifficultyLevel.EASY.name(),uiSkin);
        medium = checkBox(DifficultyLevel.MEDIUM.name(), uiSkin);//new CheckBox(DifficultyLevel.MEDIUM.name(),uiSkin);
        hard = checkBox(DifficultyLevel.HARD.name(), uiSkin);//new CheckBox(DifficultyLevel.HARD.name(),uiSkin);

        checkBoxGroup = new ButtonGroup<>(easy,medium,hard);
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        checkBoxGroup.setChecked(difficultyLevel.name());
        TextButton backButton = new TextButton("BACK",uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);


        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);
        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();


        //TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
        //        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
//        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
//
//        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
//        Image background = new Image(backgroundRegion);
//
//        Label label = new Label("DIFFICULTY", labelStyle);
//        label.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 180, Align.center);
//
//        final ImageButton easy = createButton(uiAtlas, RegionNames.EASY);
//        easy.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 90, Align.center);
//        easy.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                checkMark.setY(easy.getY() + 25);
//                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
//            }
//        });
//
//        final ImageButton medium = createButton(uiAtlas, RegionNames.MEDIUM);
//        medium.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2, Align.center);
//        medium.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                checkMark.setY(medium.getY() + 25);
//                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
//            }
//        });
//
//        final ImageButton hard = createButton(uiAtlas, RegionNames.HARD);
//        hard.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 90, Align.center);
//        hard.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                checkMark.setY(hard.getY() + 25);
//                GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
//            }
//        });
//
//        TextureRegion checkMarkRegion = uiAtlas.findRegion(RegionNames.CHECK_MARK);
//        checkMark = new Image(new TextureRegionDrawable(checkMarkRegion));
//        checkMark.setPosition(medium.getX() + 50, medium.getY() + 35, Align.center);
//
//        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
//
//        if (difficultyLevel.isEasy()) {
//            checkMark.setY(easy.getY() + 25);
//        } else if (difficultyLevel.isHard()) {
//            checkMark.setY(hard.getY() + 25);
//        }
//
//        ImageButton back = new ImageButton(
//                new TextureRegionDrawable(uiAtlas.findRegion(RegionNames.BACK)),
//                new TextureRegionDrawable(uiAtlas.findRegion(RegionNames.BACK_PRESSED))
//        );
//
//        back.setPosition(GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 180, Align.center);
//        back.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                back();
//            }
//        });
//
//        table.addActor(background);
//        table.addActor(label);
//        table.addActor(easy);
//        table.addActor(medium);
//        table.addActor(hard);
//        table.addActor(checkMark);
//        table.addActor(back);

        return table;
    }

    private void back() {
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }


    private void difficultyChanged() {

        CheckBox checked = checkBoxGroup.getChecked();
        if (checked == easy) {
            log.debug("easy");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);

        }else if (checked == medium) {
            log.debug("medium");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
        }else if (checked== hard) {
            log.debug("hard");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
        }
    }


    private static CheckBox checkBox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(10);
        checkBox.getLabelCell().pad(5);
        return checkBox;
    }

    private static ImageButton createButton(TextureAtlas atlas, String regionName) {
        TextureRegion region = atlas.findRegion(regionName);
        return new ImageButton(new TextureRegionDrawable(region));
    }
}
