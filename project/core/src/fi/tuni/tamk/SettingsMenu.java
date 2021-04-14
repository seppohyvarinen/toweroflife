package fi.tuni.tamk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import java.util.Locale;

public class SettingsMenu implements Screen {

    Main host;
    SpriteBatch batch;
    private Stage stage;
    float width = 400;
    float height = 150;
    boolean isPressed = false;
    private Texture menuBg;


    public SettingsMenu(final Main host) {
        this.host = host;
        //batch = new SpriteBatch();
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
                if (host.locale.equals(new Locale("fi_FI"))) {
                    host.locale = new Locale("en_US");
                } else {
                    host.locale = new Locale("fi_FI");
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

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(menuBg, 0, -6f, menuBg.getWidth() * (9 / 15f), menuBg.getHeight() * (9 / 15f));
        stage.getBatch().end();
        stage.getViewport().apply();
        stage.draw();
        /*if (isPressed) {
            host.changeNow = true;
        }*/
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
