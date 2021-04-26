package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Util class contains methods for building Body's for the Boxes. The class also contains the swing method for swinging the Box.
 * <p>
 * There are different types of Boxes used in the game. This class creates different Body's for different Boxes.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class Util {
    private static float speedX = 2f;
    private static float speedY = 1f;

    /**
     * Method for creating a standard Box Body for the game.
     *
     * @param x         is the x coordinate the Body is created to.
     * @param y         is the y coordinate the Body is created to.
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static Body createBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }

    /**
     * Method for creating a Joy-type Box Body for the game.
     *
     * @param x         is the x coordinate the Body is created to.
     * @param y         is the y coordinate the Body is created to.
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     * @param bounce    is the desired bounce value that is passed on to a method that defines the fixture of the body.
     */
    public static Body createJoyBox(float x, float y, float boxWidth, float boxHeight, float bounce) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getJoyFixtureDefinition(boxWidth, boxHeight, bounce));
        return playerBody;
    }

    /**
     * Method for creating Awe-type Box Body for the game.
     *
     * @param x         is the x coordinate the Body is created to.
     * @param y         is the y coordinate the Body is created to.
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static Body createAweBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getAweFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }

    /**
     * Method for creating a Fear-type Box Body for the game.
     *
     * @param x         is the x coordinate the Body is created to.
     * @param y         is the y coordinate the Body is created to.
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static Body createFearBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFearFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }

    public static Body createHateBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFearFixtureDefinition(boxWidth, boxHeight));
        return playerBody;
    }

    /**
     * Method that sets the created Body as dynamic.
     *
     * @param x is the x coordinate the Body is created to.
     * @param y is the y coordinate the Body is created to.
     */

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

    /**
     * Method for creating a standard Box Body for the game.
     * <p>
     * Method creates a swing animation of the texture.
     *
     * @param x       is the x coordinate of the swinging Texture.
     * @param y       is the y coordinate of the swinging Texture.
     * @param toRight is a boolean value that tells if the Texture is moving to right
     * @param toUp    is a boolean value that tells if the Texture is moving up.
     */

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

    /**
     * Method for defining standard fixture for the Box Bodies.
     *
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

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

    /**
     * Method for defining Joy fixture for the Box Bodies.
     *
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     * @param bounce    determines the restitution of the Body.
     */

    public static FixtureDef getJoyFixtureDefinition(float boxWidth, float boxHeight, float bounce) {
        FixtureDef playerFixtureDef = new FixtureDef();

        playerFixtureDef.density = 1000;
        playerFixtureDef.restitution = bounce;
        playerFixtureDef.friction = 0.1f;

        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);

        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }

    /**
     * Method for defining Awe fixture for the Box Bodies.
     *
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static FixtureDef getAweFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        playerFixtureDef.density = 1000;
        playerFixtureDef.restitution = 0.1f;
        playerFixtureDef.friction = 0.04f;

        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);

        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }

    /**
     * Method for defining Fear fixture for the Box Bodies.
     *
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static FixtureDef getFearFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 3000;
        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.2f;
        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.01f;

        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);

        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }

    /**
     * Method for defining Hate fixture for the Box Bodies.
     *
     * @param boxWidth  is the width of the Body
     * @param boxHeight is the height of the Body.
     */

    public static FixtureDef getHateFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 4000;
        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.25f;
        // How slipper object? [0,1]
        playerFixtureDef.friction = 1f;

        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);

        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }
}
