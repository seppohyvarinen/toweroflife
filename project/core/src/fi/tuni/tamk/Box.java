package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Box {

    private Body body;
    private Texture bodyTexture;
    private float boxWidth = 1f;
    private float boxHeight = 2/3f;

    private boolean drop = false;

    public Box(Texture t) {
        bodyTexture = t;

    }

    public void draw(SpriteBatch b) {

        if (!drop) {
            b.draw(bodyTexture, TowerOfLife.WORLD_WIDTH / 2 - boxWidth, TowerOfLife.WORLD_HEIGHT - boxHeight, boxWidth * 2, boxHeight * 2);
        }  else {
            b.draw(bodyTexture, body.getPosition().x - boxWidth, body.getPosition().y -boxHeight, boxWidth, boxHeight, boxWidth * 2, boxHeight * 2, 1f, 1f, body.getTransform().getRotation() * MathUtils.radiansToDegrees, 0, 0, bodyTexture.getWidth(), bodyTexture.getHeight(), false, false);
        }
    }

    public void dropIt() {
        drop = true;
        body = Util.createBox(TowerOfLife.WORLD_WIDTH / 2, TowerOfLife.WORLD_HEIGHT, boxWidth, boxHeight);
        body.setUserData(bodyTexture);
    }


}
