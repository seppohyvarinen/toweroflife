package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Box {

    Body body;
    Texture bodyTexture;
    public static float boxWidth = 4/3f;
    public static float boxHeight = 2/3f;
    public static String userData;
    boolean hasBody = false;
    Sound fearSound;
    Sound angerSound;
    Sound sorrowSound;
    Sound hateSound;
    Sound joySound;
    Sound aweSound;
    Sound loveSound;

    private boolean drop = false;

    public Box(Texture t, String d) {
        bodyTexture = t;
        userData = d;
        fearSound = Gdx.audio.newSound(Gdx.files.internal("fear.mp3"));
        angerSound = Gdx.audio.newSound(Gdx.files.internal("angerb.mp3"));
        sorrowSound = Gdx.audio.newSound(Gdx.files.internal("sorrow.mp3"));
        hateSound = Gdx.audio.newSound(Gdx.files.internal("anger.mp3"));
        joySound = Gdx.audio.newSound(Gdx.files.internal("joy.mp3"));
        aweSound = Gdx.audio.newSound(Gdx.files.internal("awe.mp3"));
        loveSound = Gdx.audio.newSound(Gdx.files.internal("love.mp3"));


    }

    public void draw(SpriteBatch b) {

        if (!drop) {
            b.draw(bodyTexture, TowerOfLife.realX, TowerOfLife.realY, boxWidth * 2, boxHeight * 2);
        }  else {
            b.draw(bodyTexture, body.getPosition().x - boxWidth, body.getPosition().y -boxHeight, boxWidth, boxHeight, boxWidth * 2, boxHeight * 2, 1f, 1f, body.getTransform().getRotation() * MathUtils.radiansToDegrees, 0, 0, bodyTexture.getWidth(), bodyTexture.getHeight(), false, false);
        }
    }

    public void dropIt() {
        drop = true;
        if (bodyTexture == TowerOfLife.joy) {
            body = Util.createJoyBox(TowerOfLife.realX+boxWidth/2 + 0.5f, TowerOfLife.realY + boxHeight/2+0.5f, boxWidth, boxHeight, TowerOfLife.bounceMultiplier * 0.07f + 0.25f);
        }  else if (bodyTexture == TowerOfLife.awe) {
            body = Util.createAweBox(TowerOfLife.realX+boxWidth/2 + 0.5f, TowerOfLife.realY + boxHeight/2+0.5f, boxWidth, boxHeight);

        }  else if (bodyTexture == TowerOfLife.hate) {
            body = Util.createAweBox(TowerOfLife.realX+boxWidth/2 + 0.5f, TowerOfLife.realY + boxHeight/2+0.5f, boxWidth, boxHeight);

        }  else if (bodyTexture == TowerOfLife.fear) {
            body = Util.createFearBox(TowerOfLife.realX+boxWidth/2 + 0.5f, TowerOfLife.realY + boxHeight/2+0.5f, boxWidth, boxHeight);

        }  else  {
            body = Util.createBox(TowerOfLife.realX+boxWidth/2 + 0.5f, TowerOfLife.realY + boxHeight/2+0.5f, boxWidth, boxHeight);
        }
        body.setUserData(userData);
        hasBody = true;
    }

    public Body getBody() {
        return body;
    }

    public boolean getDropState() {
        return this.drop;
    }

    public void makeSound() {
        if (bodyTexture == TowerOfLife.joy) {
            joySound.play();
        }  else if (bodyTexture == TowerOfLife.fear) {
            fearSound.play();
        }  else if (bodyTexture == TowerOfLife.awe) {
            aweSound.play();
        }  else if (bodyTexture == TowerOfLife.hate) {
            hateSound.play();
        }  else if (bodyTexture ==TowerOfLife.sorrow) {
            sorrowSound.play();
        }  else if (bodyTexture == TowerOfLife.anger) {
            angerSound.play();
        }  else if (bodyTexture == TowerOfLife.love) {
            loveSound.play();
        }
    }

    public void dispose() {
        bodyTexture.dispose();
    }


}
