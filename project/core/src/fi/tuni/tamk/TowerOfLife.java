package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
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

import java.util.ArrayList;

public class TowerOfLife extends ApplicationAdapter {
    public static final float WORLD_WIDTH = 9f;
    public static final float WORLD_HEIGHT = 16f;
    public static float realX = WORLD_WIDTH / 2;
    public static float realY = WORLD_HEIGHT - 2;
    public static boolean toRight = true;
    public static boolean toUp = true;
    SpriteBatch batch;
    private OrthographicCamera camera;
    public static World world;

    private Box firstBox;

    ArrayList<Box> boxes;
    int boxCounter = 0;

    private Sound hit;
    private float radius = 1f;
    boolean canSpawn = false;
    boolean canDrop = true;
    int spawnCounter = 0;


    private Texture bodyTexture;

    Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        world = new World(new Vector2(0, -9.8f), true);
        bodyTexture = new Texture(Gdx.files.internal("box.png"));

        // boxit saa parametrina Texturen, josta luodaan uusi boxi, voidaan myöhemmin tehdä Array erilaisista
        // Textureista = erilaisia boxeja
        firstBox = new Box(bodyTexture);
        boxes = new ArrayList<>();
        boxes.add(firstBox);
        // body = createBody(WORLD_WIDTH / 2, WORLD_HEIGHT, radius);

        createGround();
        hit = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {


            @Override
            public boolean tap(float x, float y, int count, int button) {
                // canDropilla estetään se, ettei voi tiputtaa, ennen kuin uusi boxi on luotu (ettei drop() metodissa tule OutOfBoundsExceptionia)
                if (canDrop) {
                    drop();
                    canDrop = false;
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
                if ((userData1 == null && userData2 != null) || (userData2 == null && userData1 != null)) {
                    hit.play();
                    spawnCounter = 0;
                    canSpawn = true;
                }
                if ((userData1 != null && userData2 != null) || (userData2 != null && userData1 != null)) {
                    hit.play();
                    spawnCounter = 0;
                    canSpawn = true;
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

        debugRenderer.render(world, camera.combined);
        if (canSpawn) {
            spawnCounter++;
        }
        if (spawnCounter > 15) {
            Box b = new Box(bodyTexture);
            boxes.add(b);
            canDrop = true;
            canSpawn = false;
            spawnCounter = 0;
        }

        if (!canSpawn) {
            Gdx.app.log("yes", "itsfalse");
        }


        batch.begin();
        Util.swing(realX,realY,toRight,toUp);
        for (Box box : boxes) {
            box.draw(batch);
        }
        batch.end();
        doPhysicsStep(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    public void drop() {


        if (!boxes.get(boxCounter).getDropState()) {
            boxes.get(boxCounter).dropIt();
            boxCounter++;
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

}
