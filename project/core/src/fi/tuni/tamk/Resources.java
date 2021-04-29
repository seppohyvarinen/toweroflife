package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Resources {
    static Music menuBgm;
    static Sound tap;
    static Sound startGame;
    static Texture menuBg;



    public Resources() {
        menuBgm = Gdx.audio.newMusic(Gdx.files.internal("menuehka.mp3"));
        tap = Gdx.audio.newSound(Gdx.files.internal("menutap.mp3"));
        startGame = Gdx.audio.newSound(Gdx.files.internal("startgame.mp3"));
        menuBg = new Texture(Gdx.files.internal("mainMenuBackground.png"));

    }

}
