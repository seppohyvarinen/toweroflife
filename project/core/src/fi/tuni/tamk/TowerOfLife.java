package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class TowerOfLife implements Screen {
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

    Viewport viewport;
    Viewport hudViewport;

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
    String aweBox = "aweBox";

    String tempData = "";

    ArrayList<Box> boxes;
    Iterator<Box> itr;
    ArrayList<Integer> removeTheseIndexes;
    int boxCounter;
    public static int score = 0;
    public int scoreMultiplier = 1;
    public int lastScore = 0;

    int lives = 3;
    int gongratsTimer = 0;
    int negTempGetThis = 100;
    int posTempGetThis = 100;

    boolean gameOver = false;
    static boolean scoreSoundPlayed = false;

    public static boolean soundOn = true;
    public static boolean musicOn = true;

    //Score näytölle
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter smallFontParameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter smallRedFontParameter;

    BitmapFont font;
    BitmapFont smallFont;
    BitmapFont smallRedFont;

    private Sound hit;
    private Sound mode;
    private float radius = 1f;
    boolean canSpawn = false;
    boolean canDrop = true;
    boolean destroyIsOn = false;
    boolean canBeNeg = false;
    boolean okayToLoop = true;
    boolean positiveBoxes = true;
    boolean lastWasNegative = true;
    boolean miniGametime = false;
    boolean gongrats = false;
    boolean wasIncorrect = false;
    boolean gamePlayed = false;
    static boolean mainGame = true;
    static boolean minigameStart = false;
    int destroyIndex;
    int spawnCounter;
    static int miniGameCounter;
    static int answerCounter;
    static int posiCounter;

    static boolean answerIsGiven = false;
    int getThis;
    static int bounceMultiplier;
    float cameraY = WORLD_HEIGHT / 2f;

    Main host;

    Texture scoreAdd;
    Texture scoreMinus;

    private Texture bodyTexture;
    static Texture anger;
    static Texture awe;
    static Texture fear;
    static Texture hate;
    static Texture joy;
    static Texture love;
    static Texture sorrow;
    Texture cloud0;
    Texture cloud1;
    Texture cloud2;

    ArrayList<Texture> positive;
    ArrayList<Texture> negative;
    static ArrayList<Integer> usedAngerQuestions;
    static ArrayList<Integer> usedSorrowQuestions;
    static ArrayList<Integer> usedFearQuestions;
    static int latestAnger;
    static int latestSorrow;
    static int latestFear;
    private Texture backdropGrass;
    private Texture backdrop1;
    private Texture backdrop2;
    float cloudPosY;
    float cloudPosY2;
    float cloudPosY3;
    float cloudPosY4;
    boolean cloudArea = true;


    float cloudPosX;
    float cloudPosX2;
    float cloudPosX3;
    float cloudPosX4;
    boolean needNewY = true;
    boolean needNewY2 = true;
    boolean needNewY3 = true;
    boolean needNewY4 = true;
    ArrayList<Texture> clouds;


    Sound dropSound;
    Sound multiplyScore;
    Sound minusScore;

    Box2DDebugRenderer debugRenderer;


    public TowerOfLife(Main host) {
        this.host = host;
        batch = host.batch;
        hudbatch = new SpriteBatch();
        camera = new OrthographicCamera();
        cloudPosY = -1.0f;
        cloudPosY2 = -1.0f;
        cloudPosY3 = -1.0f;
        cloudPosY4 = -1.0f;
        cloudPosX = -8.0f;
        cloudPosX2 = -8.0f;
        cloudPosX3 = -8.0f;
        cloudPosX4 = -8.0f;
        clouds = new ArrayList<>();
        cloud2 = new Texture(Gdx.files.internal("cloud2.png"));
        cloud1 = new Texture(Gdx.files.internal("cloud1.png"));
        cloud0 = new Texture(Gdx.files.internal("cloud0.png"));
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);
        hudcamera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        hudViewport = new FitViewport(WORLD_WIDTH * 200, WORLD_HEIGHT * 200, hudcamera);
        hudViewport.apply();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        hudcamera.setToOrtho(false, WORLD_WIDTH * 200f, WORLD_HEIGHT * 200f);
        world = new World(new Vector2(0, -9.8f), true);

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 140;
        fontParameter.borderWidth = 4f;
        fontParameter.borderColor = Color.GRAY;
        fontParameter.color = Color.YELLOW;

        smallFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallFontParameter.size = 72;
        smallFontParameter.borderWidth = 4f;
        smallFontParameter.borderColor = Color.GRAY;
        smallFontParameter.color = Color.YELLOW;

        smallRedFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallRedFontParameter.size = 72;
        smallRedFontParameter.borderWidth = 4f;
        smallRedFontParameter.borderColor = Color.BLACK;
        smallRedFontParameter.color = Color.RED;

        font = fontGenerator.generateFont(fontParameter);
        smallFont = fontGenerator.generateFont(smallFontParameter);
        smallRedFont = fontGenerator.generateFont(smallRedFontParameter);
        posiCounter = 0;
        bounceMultiplier = 0;
        latestFear = 99;
        latestSorrow = 99;
        latestSorrow = 99;
        boxCounter = 0;
        destroyIndex = 0;
        spawnCounter = 0;
        miniGameCounter = 0;
        answerCounter = 0;


        bodyTexture = new Texture(Gdx.files.internal("box.png"));
        backdropGrass = new Texture(Gdx.files.internal("grass.png"));
        backdrop1 = new Texture(Gdx.files.internal("backdrop_1.png"));
        backdrop2 = new Texture(Gdx.files.internal("backdrop_2.png"));
        if (host.locale.equals(new Locale("fi", "FI"))) {
            awe = new Texture(Gdx.files.internal("em_awe_fi.png"));
            anger = new Texture(Gdx.files.internal("em_anger_fi.png"));
            fear = new Texture(Gdx.files.internal("em_fear_fi.png"));
            hate = new Texture(Gdx.files.internal("em_hate_fi.png"));
            joy = new Texture(Gdx.files.internal("em_joy_fi.png"));
            love = new Texture(Gdx.files.internal("em_love_fi.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow_fi.png"));
        } else {
            anger = new Texture(Gdx.files.internal("em_anger.png"));
            awe = new Texture(Gdx.files.internal("em_awe.png"));
            fear = new Texture(Gdx.files.internal("em_fear.png"));
            hate = new Texture(Gdx.files.internal("em_hate.png"));
            joy = new Texture(Gdx.files.internal("em_joy.png"));
            love = new Texture(Gdx.files.internal("em_love.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow.png"));
        }
        scoreAdd = new Texture(Gdx.files.internal("scoreadd.png"));
        scoreMinus = new Texture(Gdx.files.internal("scoreminus.png"));

        positive = new ArrayList<>();
        negative = new ArrayList<>();
        usedAngerQuestions = new ArrayList<>();
        usedFearQuestions = new ArrayList<>();
        usedSorrowQuestions = new ArrayList<>();

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
        while (getThis == 0) {
            getThis = MathUtils.random(0, positive.size() - 1);

        }
        firstBox = new Box(positive.get(getThis), itsFirst);
        posTempGetThis = getThis;
        boxes = new ArrayList<>();
        itr = boxes.iterator();

        multiplyScore = Gdx.audio.newSound(Gdx.files.internal("scoreplus.mp3"));
        minusScore = Gdx.audio.newSound(Gdx.files.internal("scoreminus.mp3"));


        boxes.add(firstBox);
        removeTheseIndexes = new ArrayList<>();

        // body = createBody(WORLD_WIDTH / 2, WORLD_HEIGHT, radius);

        createGround();
        hit = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        mode = Gdx.audio.newSound(Gdx.files.internal("mode.mp3"));


        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {


            @Override
            public boolean tap(float x, float y, int count, int button) {
                // canDropilla estetään se, ettei voi tiputtaa, ennen kuin uusi boxi on luotu (ettei drop() metodissa tule OutOfBoundsExceptionia)
                if (mainGame) {
                    if (!gameOver) {
                        if (canDrop && mainGame && !gongrats) {
                            if (soundOn)
                                dropSound.play();
                            drop();
                            boxCounter++;
                            canDrop = false;
                        }
                    }
                }
                Vector3 touchPoint = new Vector3();
                TowerOfLife.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                Gdx.app.log("", "" + touchPoint.x);

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
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    score = addScore(score, scoreMultiplier);
                    bounceMultiplier++;

                    if (userData1 == itsFirst) {
                        contact.getFixtureA().getBody().setUserData(firstStack);
                        contact.getFixtureA().setRestitution(0);


                    } else
                        contact.getFixtureB().getBody().setUserData(firstStack);
                    contact.getFixtureB().setRestitution(0);


                    Gdx.app.log("hello", "done");
                    //boxCounter++;


                }
                if ((userData1 == firstStack && userData2 == itsABox) || (userData1 == itsABox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    score = addScore(score, scoreMultiplier);
                    bounceMultiplier++;

                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                        contact.getFixtureA().setRestitution(0);


                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    contact.getFixtureB().setRestitution(0);


                    //boxCounter++;
                }
                if ((userData1 == firstStack && userData2 == aweBox) || (userData1 == aweBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    world.setGravity(new Vector2(0, -9.8f));
                    score = addScore(score, scoreMultiplier);
                    bounceMultiplier++;

                    if (userData1 == aweBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                        contact.getFixtureA().setRestitution(0);


                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    contact.getFixtureB().setRestitution(0);


                    //boxCounter++;
                }
                if ((userData1 == stacked && userData2 == itsABox) || (userData1 == itsABox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();


                    }
                    bounceMultiplier++;

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    score = addScore(score, scoreMultiplier);

                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                        contact.getFixtureA().setRestitution(0);


                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    contact.getFixtureB().setRestitution(0);


                    //boxCounter++;
                }
                if ((userData1 == stacked && userData2 == aweBox) || (userData1 == aweBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();


                    }
                    bounceMultiplier++;
                    world.setGravity(new Vector2(0, -9.8f));

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    score = addScore(score, scoreMultiplier);

                    if (userData1 == aweBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                        contact.getFixtureA().setRestitution(0);


                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    contact.getFixtureB().setRestitution(0);


                    //boxCounter++;
                }

                if ((userData1 == stacked && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }
                    bounceMultiplier++;
                    tempData = sorrowBox;
                    miniGametime = true;
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    //boxCounter++;
                }
                if ((userData1 == firstStack && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    //boxCounter++;
                }
                if ((userData1 == firstStack && userData2 == hateBox) || (userData1 == hateBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    //boxCounter++;
                }
                if ((userData1 == firstStack && userData2 == fearBox) || (userData1 == fearBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    //boxCounter++;
                }
                if ((userData1 == ground && userData2 == fearBox) || (userData1 == fearBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    //boxCounter++;
                }
                if ((userData1 == ground && userData2 == hateBox) || (userData1 == hateBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    //boxCounter++;
                }
                if ((userData1 == ground && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }


                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    //boxCounter++;
                }
                if ((userData1 == stacked && userData2 == hateBox) || (userData1 == hateBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();


                    }
                    tempData = hateBox;
                    bounceMultiplier++;

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    // boxCounter++;
                }
                if ((userData1 == stacked && userData2 == fearBox) || (userData1 == fearBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter-1).makeSound();

                    }
                    tempData = fearBox;
                    bounceMultiplier++;

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    minigameStart = true;

                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                    //boxCounter++;
                }
                if ((userData1 == stacked && userData2 == ground) || (userData1 == ground && userData2 == stacked)) {
                    if (soundOn)
                        hit.play();

                    if (userData1 == stacked) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    //boxCounter--;
                    //lives--;
                    score--;
                }
                if ((userData1 == ground && userData2 == itsABox) || (userData1 == itsABox && userData2 == ground)) {
                    if (soundOn)
                        hit.play();
                    //lives--;
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
                if ((userData1 == ground && userData2 == aweBox) || (userData1 == aweBox && userData2 == ground)) {
                    if (soundOn)
                        hit.play();
                    //lives--;
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    world.setGravity(new Vector2(0, -9.8f));
                    if (userData1 == aweBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                        Gdx.app.log("hello", "done");

                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);


                }

                if ((userData1 == stacked && userData2 == stacked) || (userData2 == stacked && userData1 == stacked)) {
                    if (soundOn) {
                        hit.play();

                    }

                }
                if ((userData1 == stacked && userData2 == firstStack) || (userData2 == stacked && userData1 == firstStack)) {
                    if (soundOn)
                        hit.play();
                }
                if ((userData1 == ground && userData2 == firstStack) || (userData2 == ground && userData1 == firstStack)) {
                    if (soundOn)
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        hudbatch.setProjectionMatrix(hudcamera.combined);


        //debugRenderer.render(world, camera.combined);
        Gdx.app.log("what", "" + bounceMultiplier);

        if (canSpawn) {
            spawnCounter++;
        }
        if (minigameStart) {
            miniGameCounter++;
        }




        moveCamera(boxCounter);

        batch.begin();
        Gdx.app.log("boxes", ""+camera.position.y);

        if (mainGame) {
            batch.draw(backdrop1, 0f, 0f, 9f, 18f);
            batch.draw(backdrop2, 0f, 18f, 9f, 18f);
        } /*else {
            m.draw(batch);
        }*/

        if (!gameOver) {

            Util.swing(realX, realY, toRight, toUp);

            if (okayToLoop) {
                for (int i = 0; i < boxes.size(); i++) {
                    if (boxes.get(i).hasBody) {
                        if ((boxes.get(i).body.getUserData().equals(destroy)) || boxes.get(i).body.getPosition().x < 0 || boxes.get(i).body.getPosition().x > 10) {
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
                boxCounter--;
                lives--;
                destroyIsOn = false;
                okayToLoop = true;
            }
        }


        if (camera.position.y < 150) {
            cloudsBehind();

        }


        if (mainGame) {
            for (Box box : boxes) {
                box.draw(batch);
            }
            batch.draw(backdropGrass, 0f, 0f, 9f, 3f);
        }

        if (camera.position.y > 12 && camera.position.y < 150) {
            cloudsFront();

        }


        //Palikoiden freezaus
     /*
        }*/

        batch.end();

        if (lives <= 0)
            gameOver = true;

        hudbatch.begin();
        if (mainGame) {
            font.draw(hudbatch, host.getLevelText("score") + " " + score, 20, WORLD_HEIGHT * 200 - 20);
            font.draw(hudbatch, host.getLevelText("lives") + " " + lives, WORLD_WIDTH * 200f - 820f, WORLD_HEIGHT * 200 - 20, 800f, 16, false);
            if (wasIncorrect == true) {
                smallRedFont.draw(hudbatch, host.getLevelText("multiplier") + " x" + scoreMultiplier, 20, WORLD_HEIGHT * 200 - 150);
            } else {
                smallFont.draw(hudbatch, host.getLevelText("multiplier") + " x" + scoreMultiplier, 20, WORLD_HEIGHT * 200 - 150);
            }
        }

        if (miniGameCounter > 50 && boxes.get(boxCounter - 1).body.getUserData().equals(stacked)) {
            MiniGame m = new MiniGame(tempData, host);
            host.setScreen(m);
        }
        if (spawnCounter > 60) {
            spawnBox();
        }

        if (gongrats) {

            if (!scoreSoundPlayed && soundOn) {
                multiplyScore.play();
                scoreSoundPlayed = true;
            }

            for (int i = 0; i < boxes.size() - 2; i++) {

                if (boxes.get(i).hasBody) {
                    boxes.get(i).body.setTransform(boxes.get(i).body.getPosition().x, boxes.get(i).body.getPosition().y, 0);
                    boxes.get(i).body.setType(BodyDef.BodyType.StaticBody);

                }
            }
            gongratsTimer++;
            if (gongratsTimer < 80) {
                if (gongratsTimer == 1 && scoreMultiplier < 10) {
                    scoreMultiplier++;
                }
                hudbatch.draw(scoreAdd, 20, WORLD_HEIGHT * 100 - 500, 350, 200);
            } else {
                gongrats = false;
                gongratsTimer = 0;
            }
        }
        if (wasIncorrect) {

            if (!scoreSoundPlayed && soundOn) {
                minusScore.play();
                scoreSoundPlayed = true;
            }
            gongratsTimer++;
            if (gongratsTimer < 80) {
                if (gongratsTimer == 1) {
                    scoreMultiplier = 1;
                }
                hudbatch.draw(scoreMinus, 20, WORLD_HEIGHT * 100 - 500, 350, 200);
            } else {
                wasIncorrect = false;
                gongratsTimer = 0;
            }
        }


        if (gameOver) {
            lastScore = score;
            save(score, Main.highscore);
            for (int i = 0; i < Main.highscore.length; i++) {
                System.out.println(Main.highscore[i]);

            }
            host.setScreen(new GameOver(host));
        }
        hudbatch.end();

        if (mainGame) {
            doPhysicsStep(Gdx.graphics.getDeltaTime());

        }
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hudViewport.update(width, height);
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
        batch.dispose();
        for (Texture t : positive) {
            t.dispose();
        }

        for (Texture t : negative) {
            t.dispose();
        }

        hudbatch.dispose();
        hit.dispose();
        mode.dispose();
        for (Box b : boxes) {
            b.dispose();
        }
        for (Texture t: clouds) {
            t.dispose();
        }

    }

    public void spawnBox() {
        int random = MathUtils.random(1, 3);
        if (((random == 1 && !lastWasNegative) || posiCounter > 7) && canBeNeg) {
            positiveBoxes = false;
            getThis = MathUtils.random(0, negative.size() - 1);
            while (getThis == negTempGetThis) {
                getThis = MathUtils.random(0, negative.size() - 1);

            }
            negTempGetThis = getThis;
            Box b = new Box(negative.get(getThis), negativeBox);
            if (b.bodyTexture == sorrow) {
                b.userData = sorrowBox;
            } else if (b.bodyTexture == fear) {
                b.userData = fearBox;
            } else {
                b.userData = hateBox;
            }
            boxes.add(b);
            lastWasNegative = true;
            posiCounter = 0;
        } else {
            positiveBoxes = true;
            getThis = MathUtils.random(0, positive.size() - 1);
            if (getThis == posTempGetThis) {
                while (getThis == posTempGetThis) {
                    getThis = MathUtils.random(0, positive.size() - 1);

                }
            }

            Box b = new Box(positive.get(getThis), itsABox);

            if (b.bodyTexture == awe) {
                b.userData = aweBox;
                world.setGravity(new Vector2(0, -6.0f));
            }
            posTempGetThis = getThis;
            boxes.add(b);
            lastWasNegative = false;
            posiCounter++;
            if (posiCounter > 3) {
                canBeNeg = true;
            }

        }

        canDrop = true;
        canSpawn = false;
        spawnCounter = 0;
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
        myBodyDef.position.set(WORLD_WIDTH / 2, 0.8f);
        //myBodyDef.position.set(WORLD_WIDTH / 2, 0.25f);

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

    private void save(int score, int[] highscore) {
        //Highscore from file to array
        String text = Main.file.readString();
        String stringHighscore[] = text.split("\\n");

        for (int i = 0; i < highscore.length; i++) {
            highscore[i] = Integer.parseInt(stringHighscore[i]);
        }

        int[] numbers = new int[11];
        for (int i = 0; i < 10; i++) {
            numbers[i] = highscore[i];
        }

        numbers[10] = score;

        for (int i = 0; i < numbers.length; i++) {
            int min = i;
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[min]) {
                    min = j;
                }
            }
            int tmp = numbers[i];
            numbers[i] = numbers[min];
            numbers[min] = tmp;
        }

        for (int i = 0; i < 10; i++) {
            highscore[i] = numbers[10 - i];
        }
        String myString = "";
        for (int i = 0; i < highscore.length; i++) {
            myString = myString + String.valueOf(highscore[i]) + "\n";
            Main.file.writeString(myString, false);
        }
    }

    public void cloudsMove(SpriteBatch b, float scale, Texture t, float height, float x) {

        b.draw(t, x, height, clouds.get(1).getWidth() / scale, clouds.get(1).getHeight() / scale);
    }
    public void cloudsBehind() {
        if (needNewY) {
            if (camera.position.y < 10) {
                cloudPosY = (float) MathUtils.random(camera.position.y + 5, camera.position.y + 8);
            }  else if (camera.position.y > 140) {
                cloudPosY = camera.position.y - 10;
            }  else  {
                cloudPosY = (float) MathUtils.random(camera.position.y - 9, camera.position.y + 1);
            }
            needNewY = false;
        }

        if (needNewY2) {
            if (camera.position.y < 10) {
                cloudPosY2 = (float) MathUtils.random(camera.position.y + 1, camera.position.y + 3);
            }  else if (camera.position.y > 140) {
                cloudPosY = camera.position.y - 10;
            }  else {
                cloudPosY2 = (float) MathUtils.random(camera.position.y + 2, camera.position.y + 9);
            }
            needNewY2 = false;
        }

        if (cloudPosX < 12) {
            cloudPosX += 0.007f;
        }  else {
            cloudPosX = -6.0f;
            needNewY = true;
        }
        if (cloudPosX2 < 12) {
            cloudPosX2 += 0.009f;
        }  else {
            cloudPosX2 = -6.0f;
            needNewY2 = true;
        }

        if (cloudArea) {
            cloudsMove(batch, 220f, clouds.get(0), cloudPosY, cloudPosX);
            cloudsMove(batch, 210f, clouds.get(1), cloudPosY2, cloudPosX2);
        }
    }
    public void cloudsFront() {
        if (needNewY3) {

            if (camera.position.y > 140) {
                cloudPosY = camera.position.y - 10;
            }  else {
                cloudPosY3 = (float) MathUtils.random(camera.position.y - 9, camera.position.y + 1);

            }
            needNewY3= false;
        }

        if (needNewY4) {

             if (camera.position.y > 140) {
                 cloudPosY = camera.position.y - 10;
             }  else {
                 cloudPosY4 = (float) MathUtils.random(camera.position.y + 2, camera.position.y + 9);
             }

            needNewY4 = false;
        }

        if (cloudPosX3 < 12) {
            cloudPosX3 += 0.01f;
        }  else {
            cloudPosX3 = -6.0f;
            needNewY3 = true;
        }
        if (cloudPosX4 < 12) {
            cloudPosX4 += 0.014f;
        }  else {
            cloudPosX4 = -6.0f;
            needNewY4 = true;
        }

        if (cloudArea) {
            cloudsMove(batch, 170f, clouds.get(0), cloudPosY3, cloudPosX3);
            cloudsMove(batch, 170f, clouds.get(2), cloudPosY4, cloudPosX4);
        }
    }

    public int addScore(int score, int scoreMultiplier) {
        int newScore = score + scoreMultiplier;
        return newScore;
    }

}