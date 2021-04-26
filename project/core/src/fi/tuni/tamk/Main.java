package fi.tuni.tamk;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;


/**
 * Main class. Handles the switching of different game screens such as main menu, settings, main game and minigame.
 * <p>
 * The main class extends Game to achieve it's functionality. The class also handles the changing of Locale (the language used)
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class Main extends Game {
    SpriteBatch batch;
    TowerOfLife theGame;
    MainMenu mainMenu;
    boolean changeNow = false;
    boolean createGame = false;
    public static int[] highscore = new int[10];
    public Locale locale = new Locale("en_US");
    public static FileHandle file;
    static boolean musicCheck = false;


    /**
     * Mandatory method in classes extending the Game. Works as a constructor for the Main class.
     * <p>
     * Initializes the menu screen and the game screen. Sets the main menu as default screen.
     * Checks if the highscores.txt exists. If not, the creates it and fills in with zeros.
     */

    @Override
    public void create() {
        batch = new SpriteBatch();

        theGame = new TowerOfLife(this);
        mainMenu = new MainMenu(this);
        file = Gdx.files.local("highscore.txt");

        try {
            String test = file.readString();
        } catch (Exception e) {
            String myString = "";
            for (int i = 0; i < 10; i++) {
                myString = myString + String.valueOf(0) + "\n";
                Main.file.writeString(myString, false);
            }
        }
        setScreen(mainMenu);
    }

    /**
     * Mandatory method in classes extending the Game.
     * <p>
     * Renders the different classes Screens. Uses super.render() to render whichever screen that is set to render.
     */

    @Override
    public void render() {
        if (changeNow) {
            setScreen(theGame);
            changeNow = false;
        }
        if (createGame) {
            theGame = new TowerOfLife(this);
            createGame = false;
        }
        super.render();
    }

    /**
     * Mandatory method in classes extending the Game. Disposes of the Spritebatch used in the class.
     */

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Method that checks the Locale and finds the correct properties file for the corresponding Locale.
     */

    public String getLevelText(String key) {
        I18NBundle myBundle =
                I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        return myBundle.get(key);
    }
}
