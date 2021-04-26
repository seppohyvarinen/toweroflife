package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

/**
 * SettingsMenu class is the settings menu of the game.
 * <p>
 * The class contains a background and the stage, which has the following buttons: language, sound, music and back.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class SettingsMenu implements Screen {

    Main host;
    private Stage stage;
    float width = 400;
    float height = 150;
    private Texture menuBg;

    /**
     * Constructor for SettingsMenu class. This constructor creates menu background and 4 buttons:
     * language - changes the language in the game by changing the Main.locale;
     * sound - turns on/off sounds in the game;
     * music - turns on/off sounds in the game;
     * back - go to MainMenu screen.
     *
     * @param host is the Main object that extends Game class.
     */

    public SettingsMenu(final Main host) {
        this.host = host;
        stage = new Stage(new FitViewport(TowerOfLife.WORLD_WIDTH * 100, TowerOfLife.WORLD_HEIGHT * 100));
        Gdx.input.setInputProcessor(stage);
        menuBg = new Texture(Gdx.files.internal("menuBackground.png"));
        Skin mySkin = new Skin(Gdx.files.internal("skin1/glassy-ui.json"));

        Button language = new TextButton(host.getLevelText("language"), mySkin, "default");
        language.setSize(width, height);
        language.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 1050);
        stage.addActor(language);
        language.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (host.locale.equals(new Locale("fi", "FI"))) {
                    host.locale = new Locale("en", "US");
                } else {
                    host.locale = new Locale("fi", "FI");
                }
                host.setScreen(new SettingsMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Button sound;
        if (TowerOfLife.soundOn)
            sound = new TextButton(host.getLevelText("sound") + " off", mySkin, "default");
        else
            sound = new TextButton(host.getLevelText("sound") + " on", mySkin, "default");
        sound.setSize(width, height);
        sound.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 850);
        sound.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    TowerOfLife.soundOn = false;
                } else {
                    TowerOfLife.soundOn = true;
                }
                host.setScreen(new SettingsMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(sound);

        Button music;
        if (TowerOfLife.musicOn)
            music = new TextButton(host.getLevelText("music") + " off", mySkin, "default");
        else
            music = new TextButton(host.getLevelText("music") + " on", mySkin, "default");
        music.setSize(width, height);
        music.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 650);
        music.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.musicOn) {
                    TowerOfLife.musicOn = false;
                } else {
                    TowerOfLife.musicOn = true;
                }
                host.setScreen(new SettingsMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(music);

        Button back = new TextButton(host.getLevelText("back"), mySkin, "default");
        back.setSize(width, height);
        back.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 450);
        back.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                host.setScreen(new MainMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(back);
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void show() {
    }

    /**
     * Mandatory method for classes implementing the screen. Renders all the Textures and stage used in the class.
     *
     * @param delta is the deltatime, or elapsed time.
     */

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(menuBg, 0, -6f, menuBg.getWidth() * (9 / 15f), menuBg.getHeight() * (9 / 15f));
        stage.getBatch().end();
        stage.getViewport().apply();
        stage.draw();
    }

    /**
     * Mandatory method in classes that implement Screen. Handles stage scaling for different devices.
     */

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void pause() {
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void resume() {
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void hide() {
    }

    /**
     * Mandatory method in classes that implement Screen. Dispose of the stage used in the class.
     */

    @Override
    public void dispose() {
        stage.dispose();
    }
}