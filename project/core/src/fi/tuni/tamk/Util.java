package fi.tuni.tamk;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Util {

    public static Body createBox(float x, float y, float boxWidth, float boxHeight) {
        Body playerBody = TowerOfLife.world.createBody(getDefinitionOfBody(x, y));
        playerBody.createFixture(getFixtureDefinition(boxWidth, boxHeight));
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

    public static FixtureDef getFixtureDefinition(float boxWidth, float boxHeight) {
        FixtureDef playerFixtureDef = new FixtureDef();

        // Mass per square meter (kg^m2)
        playerFixtureDef.density = 1;

        // How bouncy object? Very bouncy [0,1]
        playerFixtureDef.restitution = 0.1f;

        // How slipper object? [0,1]
        playerFixtureDef.friction = 0.5f;

        // Create circle shape.
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxWidth, boxHeight);

        // Add the shape to the fixture
        playerFixtureDef.shape = pShape;

        return playerFixtureDef;
    }
}
