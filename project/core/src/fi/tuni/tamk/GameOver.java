package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


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


    public GameOver(final Main host) {
        this.host = host;
        //batch = new SpriteBatch();
        stage = new Stage(new FitViewport(TowerOfLife.WORLD_WIDTH * 100, TowerOfLife.WORLD_HEIGHT * 100));
        Gdx.input.setInputProcessor(stage);
        tap = Gdx.audio.newSound(Gdx.files.internal("menutap.mp3"));
        startGame = Gdx.audio.newSound(Gdx.files.internal("startgame.mp3"));

        menuBg = new Texture(Gdx.files.internal("menuBackground.png"));
        Skin mySkin = new Skin(Gdx.files.internal("skin1/glassy-ui.json"));

        Label text = new Label("You lose! \nGame Over", mySkin, "black");
        text.setFontScale(4f, 4);
        //text.setSize(width, height);
        text.setPosition(Gdx.graphics.getWidth() / 2, 1250);
        stage.addActor(text);

        Button resume = new TextButton(host.getLevelText("resume"), mySkin, "default");
        resume.setSize(width, height);
        resume.setPosition(Gdx.graphics.getWidth() / 2, 850);

        resume.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn)
                    startGame.play();

                if (!isPressed) {
                    isPressed = true;
                    host.createGame = true;
                }
                //host.setScreen(new TowerOfLife(host));
                return true;
            }
        });
        stage.addActor(resume);

        Button quit = new TextButton(host.getLevelText("quit"), mySkin, "default");
        quit.setSize(width, height);
        quit.setPosition(Gdx.graphics.getWidth() / 2, 650);
        quit.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (TowerOfLife.soundOn)
                    tap.play();
                host.setScreen(new MainMenu(host));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(quit);
    }

    @Override
    public void show() {
        // Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(menuBg, 0, -6f, menuBg.getWidth() * (9 / 15f), menuBg.getHeight() * (9 / 15f));
        stage.getBatch().end();
        stage.getViewport().apply();
        stage.draw();
        if (isPressed) {
            host.changeNow = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}