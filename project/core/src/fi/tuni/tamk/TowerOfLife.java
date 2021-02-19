package fi.tuni.tamk;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class TowerOfLife extends ApplicationAdapter {
	private static final float WORLD_WIDTH = 9f;
	private static final float WORLD_HEIGHT =16f ;
	SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,WORLD_WIDTH,WORLD_HEIGHT);
		world = new World(new Vector2(0, -9.8f), true);

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
}
