package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class TowerOfLife extends ApplicationAdapter {
	private static final float WORLD_WIDTH = 9f;
	private static final float WORLD_HEIGHT =16f ;
	SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	private Body body;
	private Sound hit;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,WORLD_WIDTH,WORLD_HEIGHT);
		world = new World(new Vector2(0, -9.8f), true);
		body = createBody(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0.5f);
		createGround();
		hit = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				// Game objects collide with each other

				// Let's get user data from both of the objects
				// We do not know the order:
				Object userData1 = contact.getFixtureA().getBody().getUserData();
				Object userData2 = contact.getFixtureB().getBody().getUserData();

				// If we did get user data (ground does not have user data)
				if((userData1 == null && userData2 != null) || (userData2 == null && userData1 != null)) {

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
	public void render () {
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	private Body createBody(float x, float y, float radius) {
		Body playerBody = world.createBody(getDefinitionOfBody(x, y));
		playerBody.createFixture(getFixtureDefinition(radius));
		return playerBody;
	}

	private BodyDef getDefinitionOfBody(float x, float y) {
		// Body Definition
		BodyDef myBodyDef = new BodyDef();

		// It's a body that moves
		myBodyDef.type = BodyDef.BodyType.DynamicBody;

		// Initial position is centered up
		// This position is the CENTER of the shape!
		myBodyDef.position.set(x, y);

		return myBodyDef;
	}

	private FixtureDef getFixtureDefinition(float radius) {
		FixtureDef playerFixtureDef = new FixtureDef();

		// Mass per square meter (kg^m2)
		playerFixtureDef.density = 1;

		// How bouncy object? Very bouncy [0,1]
		playerFixtureDef.restitution = 0.6f;

		// How slipper object? [0,1]
		playerFixtureDef.friction = 0.5f;

		// Create circle shape.
		PolygonShape pShape = new PolygonShape();
		pShape.setRadius(radius);

		// Add the shape to the fixture
		playerFixtureDef.shape = pShape;

		return playerFixtureDef;
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
		groundBox.setAsBox( WORLD_WIDTH/2 , 0.25f);

		return groundBox;
	}

	public void createGround() {
		Body groundBody = world.createBody(getGroundBodyDef());

		// Add shape to fixture, 0.0f is density.
		// Using method createFixture(Shape, density) no need
		// to create FixtureDef object as on createPlayer!
		groundBody.createFixture(getGroundShape(), 0.0f);
	}

}
