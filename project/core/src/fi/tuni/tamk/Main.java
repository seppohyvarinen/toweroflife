package fi.tuni.tamk;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

// Extends Game!
public class Main extends Game {
    SpriteBatch batch;
    TowerOfLife theGame;
    MainMenu mainMenu;
    SettingsMenu settingsMenu;
    boolean changeNow = false;
    boolean createGame = false;
   public static int[] highscore = new int[10];
    public Locale locale = new Locale("en_US");


    @Override
    public void create() {
        batch = new SpriteBatch();
        theGame = new TowerOfLife(this);
        mainMenu = new MainMenu(this);
        //settingsMenu = new SettingsMenu(this);

        for (int i = 0; i < highscore.length; i++) {
            highscore[i] = 0;
        }


        setScreen(mainMenu);

    }


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
        super.render();  // Level1:sen tai Level2:sen render
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public String getLevelText(String key) {

        I18NBundle myBundle =
                I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        return myBundle.get(key);
    }
}
