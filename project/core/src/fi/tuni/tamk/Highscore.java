package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
 * Highscore class is the screen with the game highscores.
 * <p>
 * The class contains a background, batch with the text and the stage, which has the back button.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class Highscore implements Screen {
    Main host;
    SpriteBatch batch;
    private Stage stage;
    float width = 400;
    float height = 150;
    int camHeight = 3200;
    int camWidth = 1800;
    public static OrthographicCamera scoreCam;
    boolean isPressed = false;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;

    /**
     * Constructor for Highscores class.
     * This constructor creates screen background, batch for the text and stage for quit button.
     *
     * @param host is the Main object that extends Game class.
     */

    public Highscore(final Main host) {
        batch = host.theGame.batch;
        this.host = host;
        scoreCam = new OrthographicCamera();
        scoreCam.setToOrtho(false, camWidth, camHeight);
        stage = new Stage(new FitViewport(TowerOfLife.WORLD_WIDTH * 100, TowerOfLife.WORLD_HEIGHT * 100));
        Gdx.input.setInputProcessor(stage);
        Skin mySkin = new Skin(Gdx.files.internal("ToLskin/ToLskin.json"));

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 140;
        fontParameter.borderWidth = 3f;
        fontParameter.borderColor = Color.GRAY;
        fontParameter.color = Color.YELLOW;
        font = fontGenerator.generateFont(fontParameter);

        Button reset = new TextButton(host.getLevelText("reset"), mySkin, "default");
        reset.setSize(width, height);
        reset.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 350);
        reset.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    host.resources.tap.play();
                }
                String myString = "";
                for (int i = 0; i < 10; i++) {
                    myString = myString + String.valueOf(0) + "\n";
                    Main.file.writeString(myString, false);
                }
                host.setScreen(new Highscore(host));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(reset);

        Button quit = new TextButton(host.getLevelText("back"), mySkin, "default");
        quit.setSize(width, height);
        quit.setPosition(TowerOfLife.WORLD_WIDTH * 100 / 2 - width / 2, 150);
        quit.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn) {
                    host.resources.tap.play();
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
        batch.setProjectionMatrix(scoreCam.combined);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(host.resources.menuBg, 0, -6f, host.resources.menuBg.getWidth() * (9 / 15f), host.resources.menuBg.getHeight() * (9 / 15f));
        stage.getBatch().end();
        stage.getViewport().apply();
        stage.draw();

        batch.begin();
        font.draw(batch, host.getLevelText("highscore"), 0, 3000, 1800, 1, false);
        font.draw(batch, Main.file.readString(), 0, 2800, 1800, 1, false);
        batch.end();

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
        host.resources.tap.dispose();
        host.resources.startGame.dispose();
        font.dispose();
        fontGenerator.dispose();
    }
}