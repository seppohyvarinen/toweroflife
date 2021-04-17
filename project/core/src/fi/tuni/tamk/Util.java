package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Util {
    private static float speedX = 2f;
    private static float speedY = 1f;

    public static Body createBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }
    public static Body createJoyBox(float x, float y, float boxWidth, float boxHeight, float bounce) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getJoyFixtureDefinition(boxWidth, boxHeight, bounce));
        return playerBody;
    }
    public static Body createAweBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getAweFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }

    public static BodyDef getDefinitionOfBody(float x, float y) {
        // Body Definition
        BodyDef myBodyDef = new BodyDef();

        // It's a body that moves
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        // Initial position is centered up
        // This position is the CENTER of the shape!
        myBodyDef.position.set(x, y);

        return myBodyDef;
    }


    public static void swing(float x, float y, boolean toRight, boolean toUp) {

        float maxY = TowerOfLife.camera.position.y + TowerOfLife.WORLD_HEIGHT / 2 - 1 - Box.boxHeight;
        float minY = TowerOfLife.camera.position.y + TowerOfLife.WORLD_HEIGHT / 2 - 2 - Box.boxHeight;

        float maxX = TowerOfLife.WORLD_WIDTH - (3 + Box.boxWidth / 2);
        float minX = 2 - Box.boxWidth / 2;

        if (toRight)
            x += speedX * Gdx.graphics.getDeltaTime();
        else
            x -= speedX * Gdx.graphics.getDeltaTime();

        if (x > maxX) {
            x = maxX;
            toRight = false;
            toUp = false;
        }
        if (x < minX) {
            x = minX;
            toRight = true;
            toUp = false;
        }

        if (toUp)
            y += speedY * Gdx.graphics.getDeltaTime();
        else
            y -= speedY * Gdx.graphics.getDeltaTime();

        if (toRight && (x > TowerOfLife.WORLD_WIDTH / 2 - Box.boxWidth))
            toUp = true;
        if (!toRight && (x < TowerOfLife.WORLD_WIDTH / 2 - Box.boxWidth))
            toUp = true;

        if (y > maxY) {
            y = maxY;
        }

        if (y < minY) {
            y = minY;
        }

        TowerOfLife.realX = x;
        TowerOfLife.realY = y;
        TowerOfLife.toRight = toRight;
        TowerOfLife.toUp = toUp;
    }

    public static FixtureDef getFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 1000;

        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.0f;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.5f;


        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);


        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }
    public static FixtureDef getJoyFixtureDefinition(float boxWidth, float boxHeight, float bounce) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 1000;

        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = bounce;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.1f;


        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);


        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }
    public static FixtureDef getAweFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 1000;

        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.1f;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.03f;


        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);


        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }

}
