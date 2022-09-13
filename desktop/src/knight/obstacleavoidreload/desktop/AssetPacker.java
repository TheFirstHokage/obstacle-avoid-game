package knight.obstacleavoidreload.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {


    //sends  desktop assets to android directory
    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug =DRAW_DEBUG_OUTLINE;



        //copied images folder and pasted into desktop
        //assets-raw then made assetPacker class
        //to create and atlas file and png in android
        TexturePacker.process(settings,RAW_ASSETS_PATH+"/gameplay",
                ASSETS_PATH+"/gameplay","gameplayAssets");

        TexturePacker.process(settings,RAW_ASSETS_PATH+"/ui",
                            ASSETS_PATH+"/ui","ui");

        TexturePacker.process(settings,RAW_ASSETS_PATH+"/skin",
                    ASSETS_PATH+"/ui","uiskin");

//        TexturePacker.process(settings,RAW_ASSETS_PATH+"/default",
//                ASSETS_PATH+"/ui","uiskin.json");
    }

}
