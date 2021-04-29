package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * GameOver class is the gameover screen, that is set when the player loses (TowerOfLife.score == 0).
 * <p>
 * The class contains a background and the stage, which has the following buttons: language, sound, music and back.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */


public class GameOver implements Screen {
    Main host;
    SpriteBatch batch;
    private Stage stage;
    float width = 400;
    float height = 150;
    boolean isPressed = false;
    private Texture menuBg;
    Sound tap;
    Sound startGame;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;

    /**
     * Constructor for GameOver class.
     * This constructor creates screen background, batch for the text and stage for the following buttons:
     * resume - starts the game again, go to TowerOfLife screen;
     * quit - go to MainMenu screen.
     *
     * @param host is the Main object that extends Game class.
     */

    public GameOver(final Main host) {
        batch = host.theGame.hudbatch;
        this.host = host;
        stage = new Stage(new FitViewport(TowerOfLife.WORLD_WIDTH * 100, TowerOfLife.WORLD_HEIGHT * 100));
        Gdx.input.setInputProcessor(stage);
        tap = Gdx.audio.newSound(Gdx.files.internal("menutap.mp3"));
        startGame = Gdx.audio.newSound(Gdx.files.internal("startgame.mp3"));
        menuBg = new Texture(Gdx.files.internal("menuBackground.png"));
        Skin mySkin = new Skin(Gdx.files.internal("skin1/glassy-ui.json"));

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 140;
        fontParameter.borderWidth = 4f;
        fontParameter.borderColor = Color.GRAY;
        fontParameter.color = Color.YELLOW;
        font = fontGenerator.generateFont(fontParameter);

        TowerOfLife.score = 0;

        Button resume = new TextButton(host.getLevelText("retry"), mySkin, "default");
        resume.setSize(width, height);
        resume.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 850);
        resume.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    startGame.play();
                }

                if (!isPressed) {
                    isPressed = true;
                    host.createGame = true;
                    if (TowerOfLife.musicOn) {
                        host.theGame.musicTimerDone = false;
                    }
                }
                return true;
            }
        });
        stage.addActor(resume);

        Button quit = new TextButton(host.getLevelText("quit"), mySkin, "default");
        quit.setSize(width, height);
        quit.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 650);
        quit.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    tap.play();
                }
                host.setScreen(new MainMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(quit);
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void show() {
    }

    /**
     * Mandatory method for classes implementing the screen. Renders all the Textures, batch and stage used in the class.
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
        batch.begin();
        font.draw(batch, host.getLevelText("gameover"), 600, TowerOfLife.WORLD_HEIGHT * 200 - 400, 600f, 1, false);
        font.draw(batch, host.theGame.lastScore + " " + host.getLevelText("endpoints"), 600, TowerOfLife.WORLD_HEIGHT * 200 - 600, 600f, 1, false);
        batch.end();
        host.theGame.gamePlayed = true;
        if (isPressed) {
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
     * Mandatory method in classes that implement Screen. Dispose of the batch and stage used in the class.
     */

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        font.dispose();
        menuBg.dispose();
        startGame.dispose();
        tap.dispose();
        fontGenerator.dispose();
    }
}