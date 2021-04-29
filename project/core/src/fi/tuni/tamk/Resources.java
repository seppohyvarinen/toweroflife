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
    static Texture backdropGrass;
    static Texture backdrop1;
    static Texture backdrop2;
    static Texture backdrop3;
    static Texture backdrop4;
    static Texture backdrop5;
    static Texture backdrop6;
    static Texture backdrop7;
    static Texture backdrop8;
    static Texture backdrop9;
    static Texture backdrop10;
    static Texture backdrop11;
    static Texture backdrop12;
    static Texture backdrop13;
    static Texture backdrop14;
    static Texture backdrop15;
    static Texture backdrop16;
    static Texture backdrop17;
    static Texture backdrop18;
    static Texture backdrop19;
    static Texture backdrop20;



    public Resources() {
        menuBgm = Gdx.audio.newMusic(Gdx.files.internal("menuehka.mp3"));
        tap = Gdx.audio.newSound(Gdx.files.internal("menutap.mp3"));
        startGame = Gdx.audio.newSound(Gdx.files.internal("startgame.mp3"));
        menuBg = new Texture(Gdx.files.internal("mainMenuBackground.png"));
        backdropGrass = new Texture(Gdx.files.internal("grass.png"));
        backdrop1 = new Texture(Gdx.files.internal("backdrop1.png"));
        backdrop2 = new Texture(Gdx.files.internal("backdrop2.png"));
        backdrop3 = new Texture(Gdx.files.internal("backdrop3.png"));
        backdrop4 = new Texture(Gdx.files.internal("backdrop4.png"));
        backdrop5 = new Texture(Gdx.files.internal("backdrop5.png"));
        backdrop6 = new Texture(Gdx.files.internal("backdrop6.png"));
        backdrop7 = new Texture(Gdx.files.internal("backdrop7.png"));
        backdrop8 = new Texture(Gdx.files.internal("backdrop8.png"));
        backdrop9 = new Texture(Gdx.files.internal("backdrop9.png"));
        backdrop10 = new Texture(Gdx.files.internal("backdrop10.png"));
        backdrop11 = new Texture(Gdx.files.internal("backdrop11.png"));
        backdrop12 = new Texture(Gdx.files.internal("backdrop12.png"));
        backdrop13 = new Texture(Gdx.files.internal("backdrop13.png"));
        backdrop14 = new Texture(Gdx.files.internal("backdrop14.png"));
        backdrop15 = new Texture(Gdx.files.internal("backdrop15.png"));
        backdrop16 = new Texture(Gdx.files.internal("backdrop16.png"));
        backdrop17 = new Texture(Gdx.files.internal("backdrop17.png"));
        backdrop18 = new Texture(Gdx.files.internal("backdrop18.png"));
        backdrop19 = new Texture(Gdx.files.internal("backdrop19.png"));
        backdrop20 = new Texture(Gdx.files.internal("backdrop20.png"));

    }

}
