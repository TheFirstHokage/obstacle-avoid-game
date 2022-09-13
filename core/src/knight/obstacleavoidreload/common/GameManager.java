package knight.obstacleavoidreload.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import knight.obstacleavoidreload.ObstacleAvoidGame;
import knight.obstacleavoidreload.config.DifficultyLevel;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String DIFFICULTY_KEY = "difficulty";
    private static final String HIGH_SCORE_KEY = "highscore";
    private Preferences PREFS;
    private int highscore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;



    private GameManager(){
        //used for saving highscores, saved game staes,difficulty settings
        //its a hasmap w strings as keys
        //saved as .prefs file
        //call flush after changes it flushes changes to file

        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getSimpleName());
        highscore = PREFS.getInteger(HIGH_SCORE_KEY,0);
        String difficultyName = PREFS.getString(DIFFICULTY_KEY,DifficultyLevel.MEDIUM.name());
        difficultyLevel  = DifficultyLevel.valueOf(difficultyName);

    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getHighScoreString() {
        //return string bc we need to display a string
        return String.valueOf(highscore);

    }



    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
        if (difficultyLevel == newDifficultyLevel){
            return;
        }
        difficultyLevel = newDifficultyLevel;
        PREFS.putString(DIFFICULTY_KEY,difficultyLevel.name());
        PREFS.flush();

    }

    public void updateHighScore(int score ) {

        if (score < highscore ) {
            return;
        }
        highscore = score;
        //saves it in mem which i dont want
        PREFS.putInteger(HIGH_SCORE_KEY,highscore);
        PREFS.flush();

    }
}
