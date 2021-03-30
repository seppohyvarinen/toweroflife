package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenu implements Screen {
    Main host;
    SpriteBatch batch;
    private Stage stage;
    float width = 200;
    float height = 100;
    boolean isPressed = false;


    public MainMenu(final Main host) {

        this.host = host;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

        Button settings = new TextButton("Settings", mySkin, "default");
        settings.setSize(width, height);
        settings.setPosition(170, 300);
        stage.addActor(settings);

        Button play = new TextButton("Play", mySkin, "default");
        play.setSize(width, height);
        play.setPosition(170, 500);

        play.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {


            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!isPressed) {
                    isPressed = true;
                    host.createGame = true;
                }

                return true;
            }
        });
        stage.addActor(play);
    }

    @Override
    public void show() {
        // Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.draw();
        if (isPressed) {
            host.changeNow = true;
        }
    }

    @Override
    public void resize(int width, int height) {

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