package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class TowerOfLife extends ApplicationAdapter {
	private static final float WORLD_WIDTH = 9f;
	private static final float WORLD_HEIGHT =16f ;
	SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	private Body body;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,WORLD_WIDTH,WORLD_HEIGHT);
		world = new World(new Vector2(0, -9.8f), true);
		body = createBody(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0.5f);

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
		CircleShape circleshape = new CircleShape();
		circleshape.setRadius(radius);

		// Add the shape to the fixture
		playerFixtureDef.shape = circleshape;

		return playerFixtureDef;
	}
}
