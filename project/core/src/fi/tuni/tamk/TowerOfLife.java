package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Iterator;

public class TowerOfLife extends ApplicationAdapter {
    public static final float WORLD_WIDTH = 9f;
    public static final float WORLD_HEIGHT = 16f;
    public static float realX = WORLD_WIDTH / 2;
    public static float realY = WORLD_HEIGHT - 2 - Box.boxHeight;
    public static boolean toRight = true;
    public static boolean toUp = true;
    SpriteBatch batch;
    SpriteBatch hudbatch;
    public static OrthographicCamera camera;
    public static OrthographicCamera hudcamera;
    public static World world;

    private Box firstBox;
    String itsABox = "box";
    String itsFirst = "first";
    String firstStack = "firstStack";
    String ground = "ground";
    String stacked = "stacked";
    String destroy = "destroy";
    String negativeBox = "negativebox";
    String sorrowBox = "sorrowBox";
    String hateBox = "hateBox";
    String fearBox = "fearBox";

    ArrayList<Box> boxes;
    Iterator<Box> itr;
    ArrayList<Integer> removeTheseIndexes;
    int boxCounter = 0;

    int lives = 3;

    boolean gameOver = false;

    //Score näyttölle
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;


    private Sound hit;
    private Sound mode;
    private float radius = 1f;
    boolean canSpawn = false;
    boolean canDrop = true;
    boolean destroyIsOn = false;
    boolean okayToLoop = true;
    boolean positiveBoxes = true;
    boolean mainGame = true;
    boolean minigameStart = false;
    int destroyIndex = 0;
    int spawnCounter = 0;
    int miniGameCounter = 0;
    int getThis;
    float cameraY = WORLD_HEIGHT/2f;

    MiniGame m;


    private Texture bodyTexture;
    private Texture anger;
    private Texture awe;
    private Texture fear;
    private Texture hate;
    private Texture joy;
    private Texture love;
    private Texture sorrow;

    ArrayList<Texture> positive;
    ArrayList<Texture> negative;
    private Texture backdrop;

    Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        hudbatch = new SpriteBatch();
        camera = new OrthographicCamera();
        hudcamera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        hudcamera.setToOrtho(false, WORLD_WIDTH * 100f, WORLD_HEIGHT * 100f);
        world = new World(new Vector2(0, -9.8f), true);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 1.5f;
        fontParameter.borderColor = Color.LIGHT_GRAY;
        fontParameter.color = Color.WHITE;

        font = fontGenerator.generateFont(fontParameter);


        bodyTexture = new Texture(Gdx.files.internal("box.png"));
        backdrop = new Texture(Gdx.files.internal("backdrop_small.png"));
        anger = new Texture(Gdx.files.internal("em_anger.png"));
        awe = new Texture(Gdx.files.internal("em_awe.png"));
        fear = new Texture(Gdx.files.internal("em_fear.png"));
        hate = new Texture(Gdx.files.internal("em_hate.png"));
        joy = new Texture(Gdx.files.internal("em_joy.png"));
        love = new Texture(Gdx.files.internal("em_love.png"));
        sorrow = new Texture(Gdx.files.internal("em_sorrow.png"));

        positive = new ArrayList<>();
        negative = new ArrayList<>();

        negative.add(anger);
        negative.add(fear);
        negative.add(hate);
        negative.add(sorrow);
        positive.add(awe);
        positive.add(joy);
        positive.add(love);

        // boxit saa parametrina Texturen, josta luodaan uusi boxi, voidaan myöhemmin tehdä Array erilaisista
        // Textureista = erilaisia boxeja
        getThis = MathUtils.random(0, positive.size() - 1);
        firstBox = new Box(positive.get(getThis), itsFirst);
        boxes = new ArrayList<>();
        itr = boxes.iterator();

        boxes.add(firstBox);
        removeTheseIndexes = new ArrayList<>();

        // body = createBody(WORLD_WIDTH / 2, WORLD_HEIGHT, radius);

        createGround();
        hit = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        mode = Gdx.audio.newSound(Gdx.files.internal("mode.mp3"));

        m = new MiniGame("e");

        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {


            @Override
            public boolean tap(float x, float y, int count, int button) {
                // canDropilla estetään se, ettei voi tiputtaa, ennen kuin uusi boxi on luotu (ettei drop() metodissa tule OutOfBoundsExceptionia)
                if (mainGame) {
                    if (!gameOver) {
                        if (canDrop && mainGame) {
                            drop();
                            canDrop = false;
                        }
                    }
                } else {
                    miniGameCounter = 0;
                    minigameStart = false;
                    mainGame = true;
                }


                return true;
            }

        }));

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Game objects collide with each other

                // Let's get user data from both of the objects
                // We do not know the order:
                Object userData1 = contact.getFixtureA().getBody().getUserData();
                Object userData2 = contact.getFixtureB().getBody().getUserData();

                // If we did get user data (ground does not have user data)
                if ((userData1 == itsFirst && userData2 == ground) || (userData1 == ground && userData2 == itsFirst)) {
                    hit.play();
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }

                    if (userData1 == itsFirst) {
                        contact.getFixtureA().getBody().setUserData(firstStack);
                    } else
                        contact.getFixtureB().getBody().setUserData(firstStack);
                    Gdx.app.log("hello", "done");
                    boxCounter++;


                }
                if ((userData1 == firstStack && userData2 == itsABox) || (userData1 == itsABox && userData2 == firstStack)) {
                    hit.play();
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }

                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);

                    boxCounter++;
                }
                if ((userData1 == stacked && userData2 == itsABox) || (userData1 == itsABox && userData2 == stacked)) {
                    hit.play();

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }

                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    boxCounter++;
                }

                if ((userData1 == stacked && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == stacked)) {
                    mode.play();
                    m = new MiniGame(sorrowBox);

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    boxCounter++;
                }
                if ((userData1 == stacked && userData2 == hateBox) || (userData1 == hateBox && userData2 == stacked)) {
                    mode.play();
                    m = new MiniGame(hateBox);

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    boxCounter++;
                }
                if ((userData1 == stacked && userData2 == fearBox) || (userData1 == fearBox && userData2 == stacked)) {
                    mode.play();
                    m = new MiniGame(fearBox);

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    boxCounter++;
                }
                if ((userData1 == stacked && userData2 == ground) || (userData1 == ground && userData2 == stacked)) {
                    hit.play();

                    if (userData1 == stacked) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    boxCounter--;
                    lives--;
                }
                if ((userData1 == ground && userData2 == itsABox) || (userData1 == itsABox && userData2 == ground)) {
                    hit.play();
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                        Gdx.app.log("hello", "done");

                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);


                }

                if ((userData1 == stacked && userData2 == stacked) || (userData2 == stacked && userData1 == stacked)) {
                    hit.play();
                }
                if ((userData1 == stacked && userData2 == firstStack) || (userData2 == stacked && userData1 == firstStack)) {
                    hit.play();
                }
                if ((userData1 == ground && userData2 == firstStack) || (userData2 == ground && userData1 == firstStack)) {
                    hit.play();
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        hudbatch.setProjectionMatrix(hudcamera.combined);
     //   Gdx.app.log("hello", "boxes:" + boxCounter);


        //debugRenderer.render(world, camera.combined);

        if (canSpawn) {
            spawnCounter++;
        }
        if (minigameStart) {
            miniGameCounter++;
        }
        if (miniGameCounter > 50 && boxes.get(boxCounter - 1).body.getUserData().equals(stacked)) {
            mainGame = false;
        }
        if (spawnCounter > 60) {
            if (boxCounter % 5 == 0) {
                positiveBoxes = false;
                getThis = MathUtils.random(0, negative.size() - 1);
                Box b = new Box(negative.get(getThis), negativeBox);
                if (b.bodyTexture == sorrow) {
                    b.userData = sorrowBox;
                }  else if (b.bodyTexture == fear) {
                    b.userData = fearBox;
                }  else {
                    b.userData = hateBox;
                }
                boxes.add(b);
            } else {
                positiveBoxes = true;
                getThis = MathUtils.random(0, positive.size() - 1);
                Box b = new Box(positive.get(getThis), itsABox);
                boxes.add(b);

            }

            canDrop = true;
            canSpawn = false;
            spawnCounter = 0;
        }


        moveCamera(boxCounter);

        batch.begin();

        if (mainGame) {
            batch.draw(backdrop, 0f, 0f, backdrop.getWidth() / 80f, backdrop.getHeight() / 120f);
        } /*else {
            m.draw(batch);
        }*/

        if (!gameOver) {

            Util.swing(realX, realY, toRight, toUp);


            if (okayToLoop) {
                for (int i = 0; i < boxes.size(); i++) {
                    if (boxes.get(i).hasBody) {
                        if ((boxes.get(i).body.getUserData().equals(destroy)) || boxes.get(i).body.getPosition().x < 0) {
                            Gdx.app.log("hello", "yes here we are");
                            destroyIsOn = true;

                            if (!canDrop) {
                                spawnCounter = 0;
                                canSpawn = true;
                            }
                            destroyIndex = i;
                            okayToLoop = false;
                        }
                    }
                }
            }


            if (destroyIsOn) {
                world.destroyBody(boxes.get(destroyIndex).body);

                boxes.remove(destroyIndex);
                destroyIsOn = false;
                okayToLoop = true;
            }
        }
        if (mainGame) {
            for (Box box : boxes) {
                box.draw(batch);
            }
        }


        //Palikoiden freezaus
     /*   for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).hasBody) {
                if ((boxes.get(i).body.getPosition().y < camera.position.y - WORLD_HEIGHT / 2) && (i != boxes.size() - 1 && i != boxes.size() - 2)) {
                    boxes.get(i).body.setType(BodyDef.BodyType.StaticBody);
                }

                if (boxes.get(i).body.getPosition().y >= camera.position.y - WORLD_HEIGHT / 2)
                    boxes.get(i).body.setType(BodyDef.BodyType.DynamicBody);
            }
        }*/

        batch.end();

        if (lives <= 0)
            gameOver = true;

        hudbatch.begin();
        if (mainGame) {
            font.draw(hudbatch, "Score: " + boxCounter, 10, WORLD_HEIGHT * 100 - 10);
            font.draw(hudbatch, "Lives: " + lives, WORLD_WIDTH * 100f - 250, WORLD_HEIGHT * 100 - 10);
        } else {
            m.draw(hudbatch);
        }
        if (MiniGame.youAreGoddamnRight)
            Gdx.app.log("minigame", "YOU ARE GODDAMN RIGHT!");


        if (gameOver) {
            font.draw(hudbatch, "GAME OVER!", 200, WORLD_HEIGHT * 100 - 100);
        }
        hudbatch.end();

        if (mainGame) {
            doPhysicsStep(Gdx.graphics.getDeltaTime());

        }
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    public void drop() {


        if (!boxes.get(boxCounter).getDropState()) {
            boxes.get(boxCounter).dropIt();

        }

    }

    private BodyDef getGroundBodyDef() {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();

        // This body won't move
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set(WORLD_WIDTH / 2, 0.25f);

        return myBodyDef;
    }

    private PolygonShape getGroundShape() {
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(WORLD_WIDTH / 2, 0.25f);

        return groundBox;
    }

    public void createGround() {
        Body groundBody = world.createBody(getGroundBodyDef());
        groundBody.setUserData(ground);

        // Add shape to fixture, 0.0f is density.
        // Using method createFixture(Shape, density) no need
        // to create FixtureDef object as on createPlayer!
        groundBody.createFixture(getGroundShape(), 0.0f);
    }

    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;

    private void doPhysicsStep(float deltaTime) {

        float frameTime = deltaTime;

        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if (deltaTime > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            // It's fixed time step!
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    //siirtää kameraa ylös laatikon korkeuden verran, kun laatikoiden määrä on yli 4.
    private void moveCamera(int boxCounter) {
        float camSpeed = 0.05f;
        if (boxCounter > 4) {
            cameraY = boxCounter * 4 / 3f + 2;
        }
        if (camera.position.y > cameraY + 0.1f) {
            camera.position.y -= camSpeed;
        } else if (camera.position.y <= cameraY) {
            camera.position.y += camSpeed;
        }
        camera.update();
    }

}
