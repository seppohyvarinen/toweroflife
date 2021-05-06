package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * MainMenu class is the main menu of the game.
 * <p>
 * The class contains a background and the stage, which has the following buttons: play, settings and highscores.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class MainMenu implements Screen {
    Main host;
    SpriteBatch batch;
    private Stage stage;
    float width = 400;
    float height = 150;
    boolean isPressed = false;


    /**
     * Constructor for MainMenu class. This constructor creates menu background and 3 buttons:
     * play - start of the game, go to TowerOfLife screen;
     * settings - go to settings menu screen;
     * highscores - go to highscores screen.
     *
     * @param host is the Main object that extends Game class.
     */

    public MainMenu(final Main host) {
        this.host = host;

        stage = new Stage(new FitViewport(TowerOfLife.WORLD_WIDTH * 100, TowerOfLife.WORLD_HEIGHT * 100));
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("ToLskin/ToLskin.json"));

        Button play = new TextButton(host.getLevelText("play"), mySkin, "default");
        play.setSize(width, height);
        play.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 1250);
        play.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    Resources.startGame.play();
                }
                if (!isPressed) {
                    host.resources.menuBgm.stop();
                    isPressed = true;
                    host.createGame = true;
                }
                return true;
            }
        });
        stage.addActor(play);

        Button settings = new TextButton(host.getLevelText("settings"), mySkin, "default");
        settings.setSize(width, height);
        settings.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 1050);
        settings.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    host.resources.tap.play();
                }
                host.setScreen(new SettingsMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(settings);

        Button highscore = new TextButton(host.getLevelText("highscore"), mySkin, "default");
        highscore.setSize(width, height);
        highscore.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 850);
        highscore.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    host.resources.tap.play();
                }
                host.setScreen(new Highscore(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(highscore);

        Button tutorial = new TextButton("Tutorial", mySkin, "default");
        tutorial.setSize(width, height);
        tutorial.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 650);
        tutorial.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    host.resources.tap.play();
                }
                host.setScreen(new Tutorial(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(tutorial);
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
        if (TowerOfLife.musicOn) {
            Resources.menuBgm.play();
            Resources.menuBgm.setLooping(true);
        }
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(host.resources.mainMenuBg, 0, -6f, host.resources.menuBg.getWidth() * (9 / 15f), host.resources.menuBg.getHeight() * (9 / 15f));
        stage.getBatch().end();
        stage.getViewport().apply();
        stage.draw();
        if (isPressed) {
            Resources.menuBgm.stop();
            host.changeNow = true;
        }
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
        host.resources.tap.dispose();
        host.resources.startGame.dispose();
        host.resources.menuBg.dispose();
        host.dispose();

    }
}