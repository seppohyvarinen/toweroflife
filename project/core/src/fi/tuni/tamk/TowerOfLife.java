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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * Tower Of Life class is the actual main game class. It contains several methods for handling physics, rendering Textures and handling collisions.
 * <p>
 * Tower Of Life is a tower stacking game that uses Box2dPhysics in achieving it's functionality. In the game the player stacks different types of
 * emotion boxes on top of each other to form a tower. Different boxes have different attributes. So-called negative boxes will trigger a minigame in
 * which the player has to answer a question in order to proceed. Depending on how the player answers, the Tower is either stabilized (by turning the Dynamic
 * box bodies into static) or not and the score increases or not.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

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
    public static int score;
    public int scoreMultiplier;
    public int lastScore;

    int lives;
    int gongratsTimer;
    int negTempGetThis;
    int posTempGetThis;

    boolean gameOver = false;
    static boolean scoreSoundPlayed = false;

    public static boolean soundOn = true;
    public static boolean musicOn = true;

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
    Texture scoreAdd_fi;
    Texture scoreMinus_fi;

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
    Texture cloud3;

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
    private Texture backdrop3;
    private Texture backdrop4;
    private Texture backdrop5;
    private Texture backdrop6;
    private Texture backdrop7;
    private Texture backdrop8;
    private Texture backdrop9;
    private Texture backdrop10;
    private Texture backdrop11;
    private Texture backdrop12;
    private Texture backdrop13;
    private Texture backdrop14;
    private Texture backdrop15;
    private Texture backdrop16;
    private Texture backdrop17;
    private Texture backdrop18;
    private Texture backdrop19;
    private Texture backdrop20;
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
    Sound destroySound;
    Sound multiplyScore;
    Sound minusScore;

    Box2DDebugRenderer debugRenderer;

    /**
     * Constructor for the Tower Of Life class. Sets the game, creates first Box to be dropped and initializes all the necessary variables needed at the start of the game.
     * <p>
     * The constructor also creates the inputprocessors necessary for handling all the collisions that happen in the game.
     *
     * @param host is the Main object that handles the switching of screens between the Minigame and main game.
     */

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
        cloud3 = new Texture(Gdx.files.internal("cloud3.png"));
        cloud2 = new Texture(Gdx.files.internal("cloud2.png"));
        cloud1 = new Texture(Gdx.files.internal("cloud1.png"));
        cloud0 = new Texture(Gdx.files.internal("cloud0.png"));
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);
        clouds.add(cloud3);
        hudcamera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        hudViewport = new FitViewport(WORLD_WIDTH * 200, WORLD_HEIGHT * 200, hudcamera);
        hudViewport.apply();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        hudcamera.setToOrtho(false, WORLD_WIDTH * 200f, WORLD_HEIGHT * 200f);
        world = new World(new Vector2(0, -9.8f), true);

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        destroySound = Gdx.audio.newSound(Gdx.files.internal("boxdestroy.mp3"));

        score = 0;
        lives = 3;
        posTempGetThis = 100;
        negTempGetThis = 100;
        lastScore = 0;
        scoreMultiplier = 1;
        gongratsTimer = 0;

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
        backdrop1 = new Texture(Gdx.files.internal("backdrop1.png"));
        backdrop2 = new Texture(Gdx.files.internal("backdrop2.png"));
        backdrop3 = new Texture(Gdx.files.internal("backdrop3.png"));
        backdrop4 = new Texture(Gdx.files.internal("backdrop4.png"));
        backdrop5 = new Texture(Gdx.files.internal("backdrop5.png"));
        backdrop6 = new Texture(Gdx.files.internal("backdrop6.png"));
        backdrop7 = new Texture(Gdx.files.internal("backdrop7.png"));
        backdrop8 = new Texture(Gdx.files.internal("backdrop8.png"));
        backdrop9 = new Texture(Gdx.files.internal("backdrop9.png"));
        backdrop10 = new Texture(Gdx.files.internal("backdrop10.png"));
        backdrop11 = new Texture(Gdx.files.internal("backdrop11.png"));
        backdrop12 = new Texture(Gdx.files.internal("backdrop12.png"));
        backdrop13 = new Texture(Gdx.files.internal("backdrop13.png"));
        backdrop14 = new Texture(Gdx.files.internal("backdrop14.png"));
        backdrop15 = new Texture(Gdx.files.internal("backdrop15.png"));
        backdrop16 = new Texture(Gdx.files.internal("backdrop16.png"));
        backdrop17 = new Texture(Gdx.files.internal("backdrop17.png"));
        backdrop18 = new Texture(Gdx.files.internal("backdrop18.png"));
        backdrop19 = new Texture(Gdx.files.internal("backdrop19.png"));
        backdrop20 = new Texture(Gdx.files.internal("backdrop20.png"));
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
        scoreAdd_fi = new Texture(Gdx.files.internal("scoreadd_fi.png"));
        scoreMinus_fi = new Texture(Gdx.files.internal("scoreminus_fi.png"));

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
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == firstStack && userData2 == itsABox) || (userData1 == itsABox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == firstStack && userData2 == aweBox) || (userData1 == aweBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == stacked && userData2 == itsABox) || (userData1 == itsABox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == stacked && userData2 == aweBox) || (userData1 == aweBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }

                if ((userData1 == stacked && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == firstStack && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                }
                if ((userData1 == firstStack && userData2 == hateBox) || (userData1 == hateBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                }
                if ((userData1 == firstStack && userData2 == fearBox) || (userData1 == fearBox && userData2 == firstStack)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(stacked);
                    } else
                        contact.getFixtureB().getBody().setUserData(stacked);
                }
                if ((userData1 == ground && userData2 == fearBox) || (userData1 == fearBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == fearBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                }
                if ((userData1 == ground && userData2 == hateBox) || (userData1 == hateBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == hateBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                }
                if ((userData1 == ground && userData2 == sorrowBox) || (userData1 == sorrowBox && userData2 == ground)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
                    }
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == sorrowBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                }
                if ((userData1 == stacked && userData2 == hateBox) || (userData1 == hateBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == stacked && userData2 == fearBox) || (userData1 == fearBox && userData2 == stacked)) {
                    if (soundOn) {
                        boxes.get(boxCounter - 1).makeSound();
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
                }
                if ((userData1 == stacked && userData2 == ground) || (userData1 == ground && userData2 == stacked)) {
                    if (soundOn)
                        hit.play();
                    if (userData1 == stacked) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                        if (soundOn)
                            destroySound.play();
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    if (soundOn)
                        destroySound.play();
                    score--;
                }
                if ((userData1 == ground && userData2 == itsABox) || (userData1 == itsABox && userData2 == ground)) {
                    if (soundOn)
                        hit.play();
                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    if (userData1 == itsABox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                        if (soundOn)
                            destroySound.play();
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    if (soundOn)
                        destroySound.play();
                }
                if ((userData1 == ground && userData2 == aweBox) || (userData1 == aweBox && userData2 == ground)) {
                    if (soundOn)
                        hit.play();

                    if (!canDrop) {
                        spawnCounter = 0;
                        canSpawn = true;
                    }
                    world.setGravity(new Vector2(0, -9.8f));
                    if (userData1 == aweBox) {
                        contact.getFixtureA().getBody().setUserData(destroy);
                        if (soundOn)
                            destroySound.play();
                    } else
                        contact.getFixtureB().getBody().setUserData(destroy);
                    if (soundOn)
                        destroySound.play();
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

    /**
     * Mandatory method for classes implementing the screen. Renders all the Textures used in the Tower Of Life to the players screen.
     * <p>
     * Here render() is also used in timing events such as transition to the minigame or how long certain Textures are displayed on the screen.
     *
     * @param delta is the deltatime, or elapsed time.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        hudbatch.setProjectionMatrix(hudcamera.combined);

        if (canSpawn) {
            spawnCounter++;
        }
        if (minigameStart) {
            miniGameCounter++;
        }

        moveCamera(boxCounter);

        batch.begin();

        if (mainGame) {
            batch.draw(backdrop1, 0f, 0f, 9f, 18f);
            batch.draw(backdrop2, 0f, 18f, 9f, 18f);
            batch.draw(backdrop3, 0f, 36f, 9f, 18f);
            batch.draw(backdrop4, 0f, 54f, 9f, 18f);
            batch.draw(backdrop5, 0f, 72f, 9f, 18f);
            batch.draw(backdrop6, 0f, 90f, 9f, 18f);
            batch.draw(backdrop7, 0f, 108f, 9f, 18f);
            batch.draw(backdrop8, 0f, 126f, 9f, 18f);
            batch.draw(backdrop9, 0f, 144f, 9f, 18f);
            batch.draw(backdrop10, 0f, 162f, 9f, 18f);
            batch.draw(backdrop11, 0f, 180f, 9f, 18f);
            batch.draw(backdrop12, 0f, 198f, 9f, 18f);
            batch.draw(backdrop13, 0f, 216f, 9f, 18f);
            batch.draw(backdrop14, 0f, 234f, 9f, 18f);
            batch.draw(backdrop15, 0f, 252f, 9f, 18f);
            batch.draw(backdrop16, 0f, 270f, 9f, 18f);
            batch.draw(backdrop17, 0f, 288f, 9f, 18f);
            batch.draw(backdrop18, 0f, 306f, 9f, 18f);
            batch.draw(backdrop19, 0f, 324f, 9f, 18f);
            batch.draw(backdrop20, 0f, 342f, 9f, 18f);
        }
        if (!gameOver) {
            Util.swing(realX, realY, toRight, toUp);
            if (okayToLoop) {
                for (int i = 0; i < boxes.size(); i++) {
                    if (boxes.get(i).hasBody) {
                        if ((boxes.get(i).body.getUserData().equals(destroy)) || boxes.get(i).body.getPosition().x < 0 || boxes.get(i).body.getPosition().x > 10) {
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
                if (host.locale.equals(new Locale("fi", "FI"))) {
                    hudbatch.draw(scoreAdd_fi, 20, WORLD_HEIGHT * 200 - 500, 592, 236);
                } else {
                    hudbatch.draw(scoreAdd, 20, WORLD_HEIGHT * 200 - 500, 592, 236);
                }
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
                if (host.locale.equals(new Locale("fi", "FI"))) {
                    hudbatch.draw(scoreMinus_fi, 20, WORLD_HEIGHT * 200 - 500, 750, 150);
                } else {
                    hudbatch.draw(scoreMinus, 20, WORLD_HEIGHT * 200 - 500, 750, 150);
                }
            } else {
                wasIncorrect = false;
                gongratsTimer = 0;
            }
        }
        if (gameOver) {
            lastScore = score;
            save(score, Main.highscore);
            host.setScreen(new GameOver(host));
        }
        hudbatch.end();

        if (mainGame) {
            doPhysicsStep(Gdx.graphics.getDeltaTime());
        }
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void show() {
    }

    /**
     * Mandatory method in classes that implement Screen. Handles the scaling for different devices.
     *
     * @param width  the width to be scaled
     * @param height the height to be scaled
     */

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hudViewport.update(width, height);
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
     * Mandatory method in classes that implement Screen. Disposes of Textures and Sounds used in the class.
     */

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
        for (Texture t : clouds) {
            t.dispose();
        }
        destroySound.dispose();

        backdrop1.dispose();
        backdrop2.dispose();
        backdrop3.dispose();
        backdrop4.dispose();
        backdrop5.dispose();
        backdrop6.dispose();
        backdrop7.dispose();
        backdrop8.dispose();
        backdrop9.dispose();
        backdrop10.dispose();
        backdrop11.dispose();
        backdrop12.dispose();
        backdrop13.dispose();
        backdrop14.dispose();
        backdrop15.dispose();
        backdrop16.dispose();
        backdrop17.dispose();
        backdrop18.dispose();
        backdrop19.dispose();
        backdrop20.dispose();
    }

    /**
     * Method for spawning Box objects to the top of the screen. It's programmed so, that same type of boxes cannot be spawned in a row.
     * <p>
     * Method also handles the randomization / frequency of negative type boxes in the game.
     */

    public void spawnBox() {
        int random = MathUtils.random(1, 3);
        if (((random == 1 && !lastWasNegative) || posiCounter > 5) && canBeNeg) {
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

    /**
     * Method that is called when the player taps the screen.
     * <p>
     * The method then calls the latest Boxes dropIt() method which creates a Body for the Box so that physics start to effect the Box.
     */

    public void drop() {
        if (!boxes.get(boxCounter).getDropState()) {
            boxes.get(boxCounter).dropIt();
        }
    }

    /**
     * Method for creating Bodydefinition for the ground used in the game. Creates a static body out of the ground.
     */

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

    /**
     * Method that shapes the ground as a box.
     */

    private PolygonShape getGroundShape() {
        // Create shape
        PolygonShape groundBox = new PolygonShape();
        // Real width and height is 2 X this!
        groundBox.setAsBox(WORLD_WIDTH / 2, 0.25f);

        return groundBox;
    }

    /**
     * Method that sums the different methods for creating the ground body (definition, shape and setting userdata)
     */

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

    /**
     * Method that handles the physics engines "steps" and times it with the rendering time so that drawing and physics engine are in sync.
     *
     * @param deltaTime is the elapsed time.
     */

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

    /**
     * Method that smoothly moves the camera up according to the amount of stacked boxes.
     *
     * @param boxCounter is the amount of stacked boxes.
     */

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

    /**
     * Method that saves the players score and writes it to txt.file if the score is high enough.
     *
     * @param score     is the int value of score to be saved
     * @param highscore is int[]array where the int values of the txt. file are temporarily stored so that we can compare whether the new score can be set to highscore.
     */

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

    /**
     * Method that draws the moving clouds in the game.
     * <p>
     * This method is called in the render() at 4 different occasions (before and after drawing the tower) creating a "layer effect".
     *
     * @param b      is the Spritebatch that handles the drawing.
     * @param scale  is the scale the cloud is drawn in depending whether the cloud is in front of or behind the tower.
     * @param t      is the Texture that the method draws.
     * @param height is the y coordinate in which the cloud is spawned/drawn
     * @param x      is the x coordinate of the cloud that is changed in the render to make it moving.
     */

    public void cloudsMove(SpriteBatch b, float scale, Texture t, float height, float x) {
        b.draw(t, x, height, clouds.get(1).getWidth() / scale, clouds.get(1).getHeight() / scale);
    }

    /**
     * Method that handles spawning the clouds located behind the tower.
     */

    public void cloudsBehind() {
        if (needNewY) {
            if (camera.position.y < 10) {
                cloudPosY = (float) MathUtils.random(camera.position.y + 5, camera.position.y + 8);
            } else if (camera.position.y > 120) {
                cloudPosY = camera.position.y - 10;
            } else {
                cloudPosY = (float) MathUtils.random(camera.position.y - 9, camera.position.y + 1);
            }
            needNewY = false;
        }
        if (needNewY2) {
            if (camera.position.y < 10) {
                cloudPosY2 = (float) MathUtils.random(camera.position.y + 1, camera.position.y + 3);
            } else if (camera.position.y > 120) {
                cloudPosY = camera.position.y - 10;
            } else {
                cloudPosY2 = (float) MathUtils.random(camera.position.y + 2, camera.position.y + 9);
            }
            needNewY2 = false;
        }
        if (cloudPosX < 12) {
            cloudPosX += 0.007f;
        } else {
            cloudPosX = -6.0f;
            needNewY = true;
        }
        if (cloudPosX2 < 12) {
            cloudPosX2 += 0.009f;
        } else {
            cloudPosX2 = -6.0f;
            needNewY2 = true;
        }
        if (cloudArea) {
            cloudsMove(batch, 220f, clouds.get(3), cloudPosY, cloudPosX);
            cloudsMove(batch, 210f, clouds.get(1), cloudPosY2, cloudPosX2);
        }
    }

    /**
     * Method that handles spawning the clouds located in front of the tower.
     */

    public void cloudsFront() {
        if (needNewY3) {
            if (camera.position.y > 120) {
                cloudPosY = camera.position.y - 10;
            } else {
                cloudPosY3 = (float) MathUtils.random(camera.position.y - 9, camera.position.y + 1);
            }
            needNewY3 = false;
        }
        if (needNewY4) {
            if (camera.position.y > 120) {
                cloudPosY = camera.position.y - 10;
            } else {
                cloudPosY4 = (float) MathUtils.random(camera.position.y + 2, camera.position.y + 9);
            }
            needNewY4 = false;
        }
        if (cloudPosX3 < 12) {
            cloudPosX3 += 0.01f;
        } else {
            cloudPosX3 = -6.0f;
            needNewY3 = true;
        }
        if (cloudPosX4 < 12) {
            cloudPosX4 += 0.014f;
        } else {
            cloudPosX4 = -6.0f;
            needNewY4 = true;
        }
        if (cloudArea) {
            cloudsMove(batch, 170f, clouds.get(0), cloudPosY3, cloudPosX3);
            cloudsMove(batch, 170f, clouds.get(2), cloudPosY4, cloudPosX4);
        }
    }

    /**
     * Method that adds the score to player. The multiplier is then added to the score.
     * <p>
     * The multiplier depends on how the player has answered in the Minigame
     *
     * @param score           is the old score to be added to.
     * @param scoreMultiplier is the multiplier added to the score.
     * @return returns the new score that is added to the player score.
     */

    public int addScore(int score, int scoreMultiplier) {
        int newScore = score + scoreMultiplier;
        return newScore;
    }
}