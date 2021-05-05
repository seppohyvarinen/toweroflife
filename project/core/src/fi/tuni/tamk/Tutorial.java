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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Tutorial class serves as a tutorial for Tower Of Life.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class Tutorial implements Screen {

    int page;
    SpriteBatch batch;
    Main host;
    boolean langCheck = false;
    float width = 400;
    float height = 150;
    int camHeight = 1800;
    int camWidth = 1050;
    Viewport viewport;
    public static OrthographicCamera tutCam;

    /**
     * Constructor for the Tutorial. Initializes the page that's being viewed and viewport and Spritebatch that's used.
     *
     * @param host is the Main Object used as a host for this screen.
     */

    public Tutorial (Main host) {

        batch = new SpriteBatch();
        tutCam = new OrthographicCamera();
        tutCam.setToOrtho(false, camWidth, camHeight);
        viewport = new FitViewport(TowerOfLife.WORLD_WIDTH * 1.1f, TowerOfLife.WORLD_HEIGHT * 1.1f, TowerOfLife.camera);
        viewport.apply();

        page = 0;
        this.host = host;

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean tap(float x, float y, int count, int button) {
                page++;

                return true;
            }

        }));
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void show() {

    }

    /**
     * Mandatory method for classes implementing the screen. Renders all the Textures used in the class.
     *
     * @param delta is the deltatime, or elapsed time.
     */

    @Override
    public void render(float delta) {

        if (langCheck) {
            host.resources.changeTutLanguage();
            langCheck = true;
        }
        batch.setProjectionMatrix(tutCam.combined);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (page) {
            case 0:
                batch.draw(host.resources.screenShot1, 0, 0, host.resources.screenShot1.getWidth() * 1.93f, host.resources.screenShot1.getHeight() * 1.87f);

                batch.draw(host.resources.tutorial1, 0, 60, host.resources.tutorial1.getWidth(), host.resources.tutorial1.getHeight() / 1.1f);
                break;
            case 1:
                batch.draw(host.resources.screenShot1, 0, 0, host.resources.screenShot1.getWidth() * 1.93f, host.resources.screenShot1.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial2, 0, 60, host.resources.tutorial2.getWidth(), host.resources.tutorial2.getHeight() / 1.1f);
                break;
            case 2:
                batch.draw(host.resources.screenShot1, 0, 0, host.resources.screenShot1.getWidth() * 1.93f, host.resources.screenShot1.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial3, 0, 60, host.resources.tutorial3.getWidth(), host.resources.tutorial3.getHeight() / 1.1f);
                break;
            case 3:
                batch.draw(host.resources.screenShot2, 0, 0, host.resources.screenShot2.getWidth() * 1.93f, host.resources.screenShot2.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial4, 0, 60, host.resources.tutorial4.getWidth(), host.resources.tutorial4.getHeight());
                break;
            case 4:
                batch.draw(host.resources.screenShot2, 0, 0, host.resources.screenShot2.getWidth() * 1.93f, host.resources.screenShot2.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial5, 0, 0, host.resources.tutorial5.getWidth(), host.resources.tutorial5.getHeight() / 1.05f);
                break;
            case 5:
                batch.draw(host.resources.screenShot2, 0, 0, host.resources.screenShot2.getWidth()* 1.93f, host.resources.screenShot2.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial6, 0, 0, host.resources.tutorial6.getWidth(), host.resources.tutorial6.getHeight() / 1.05f);
                break;
            case 6:
                batch.draw(host.resources.screenShot2, 0, 0, host.resources.screenShot2.getWidth()* 1.93f, host.resources.screenShot2.getHeight() * 1.87f);
                batch.draw(host.resources.tutorial7, 0, 0, host.resources.tutorial7.getWidth(), host.resources.tutorial7.getHeight());
                break;
            case 7:
                host.setScreen(new MainMenu(host));
                break;

        }

        batch.end();

    }

    /**
     * Mandatory method in classes that implement Screen. Updates the Viewport according to the Screen size.
     */

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
     * Mandatory method in classes that implement Screen. Disposes of the Textures, Sounds and Music used.
     */
    @Override
    public void dispose() {
        host.resources.screenShot1.dispose();
        host.resources.screenShot2.dispose();
        host.resources.tutorial1.dispose();
        host.resources.tutorial2.dispose();
        host.resources.tutorial3.dispose();
        host.resources.tutorial4.dispose();
        host.resources.tutorial5.dispose();
        host.resources.tutorial6.dispose();
        host.resources.tutorial7.dispose();

    }
}