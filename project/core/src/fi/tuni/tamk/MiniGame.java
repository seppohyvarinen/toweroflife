package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class MiniGame {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    ArrayList<String> sorrowProblems;
    ArrayList<String> sorrowWrongAnswers;
    ArrayList<String> sorrowRightAnswers;
    ArrayList<String> hateProblems;
    ArrayList<String> hateProblemOne;
    ArrayList<String> hateProblemTwo;
    ArrayList<String> hateProblemThree;

    ArrayList<String> fearProblems;
    ArrayList<String> fearWrongAnswers;
    ArrayList<String> fearRightAnswers;
    Texture problemBox;
    Texture answerBox;
    Image answerImage;

    public static float problemWidth = 900f;
    public static float problemHeight = 200f;

    public static boolean youAreGoddamnRight = false;

    boolean hate = false;
    boolean sorrow = false;
    boolean fear = false;
    String problem;
    String ans1;
    String ans2;
    String ans3;
    int pIndex;
    int aIndex;


    public MiniGame(String e) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 1.5f;
        fontParameter.borderColor = Color.LIGHT_GRAY;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.5f, 0.5f);

        sorrowProblems = new ArrayList<>();
        sorrowWrongAnswers = new ArrayList<>();
        sorrowRightAnswers = new ArrayList<>();
        hateProblems = new ArrayList<>();
        hateProblemOne = new ArrayList<>();
        hateProblemTwo = new ArrayList<>();
        hateProblemThree = new ArrayList<>();
        fearProblems = new ArrayList<>();
        fearWrongAnswers = new ArrayList<>();
        fearRightAnswers = new ArrayList<>();

        sorrowProblems.add("Some sorrow related problem");
        sorrowWrongAnswers.add("wrong answer");
        sorrowRightAnswers.add("right answer");

        hateProblems.add("problem1");
        hateProblems.add("problem2");
        hateProblems.add("problem3");
        hateProblemOne.add("problem 1 wrong");
        hateProblemOne.add("problem 1 wrong2");
        hateProblemOne.add("correct!");
        hateProblemTwo.add("problem 2 wrong");
        hateProblemTwo.add("problem 2 wrong2");
        hateProblemTwo.add("correct!");
        hateProblemThree.add("problem 3 wrong");
        hateProblemThree.add("problem 3 wrong2");
        hateProblemThree.add("correct!");

        problemBox = new Texture(Gdx.files.internal("problem_box.png"));
        answerBox = new Texture(Gdx.files.internal("answer_box.png"));
        answerImage = new Image(answerBox);

        pIndex = MathUtils.random(0, 2);
        problem = hateProblems.get(pIndex);


        if (pIndex == 0) {
            aIndex = MathUtils.random(0, 2);
            int helper = aIndex;
            ans1 = hateProblemOne.get(aIndex);
            while (helper == aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            int helper2 = aIndex;
            ans2 = hateProblemOne.get(aIndex);
            while (helper == aIndex || helper2 ==aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            ans3 = hateProblemOne.get(aIndex);

        }  else if (pIndex == 1) {
            aIndex = MathUtils.random(0, 2);
            int helper = aIndex;
            ans1 = hateProblemTwo.get(aIndex);
            while (helper == aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            int helper2 = aIndex;
            ans2 = hateProblemTwo.get(aIndex);
            while (helper == aIndex || helper2 ==aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            ans3 = hateProblemTwo.get(aIndex);
        }  else {
            aIndex = MathUtils.random(0, 2);
            int helper = aIndex;
            ans1 = hateProblemThree.get(aIndex);
            while (helper == aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            int helper2 = aIndex;
            ans2 = hateProblemThree.get(aIndex);
            while (helper == aIndex || helper2 ==aIndex) {
                aIndex = MathUtils.random(0, 2);
            }
            ans3 = hateProblemThree.get(aIndex);
        }



    }

    public void draw(SpriteBatch b) {
        b.draw(problemBox, 0, TowerOfLife.WORLD_HEIGHT * 100 - 200, problemWidth, problemHeight);
        font.draw(b, problem, 250, TowerOfLife.WORLD_HEIGHT * 100 - 100);

        b.draw(answerBox, 0, TowerOfLife.WORLD_HEIGHT * 100 - 400, 400, 150);
        answerImage.setBounds(0, TowerOfLife.WORLD_HEIGHT * 100 - 400, 400, 150);
        font.draw(b, ans1 , 10, TowerOfLife.WORLD_HEIGHT * 100 - 300);

        b.draw(answerBox, 500, TowerOfLife.WORLD_HEIGHT * 100 - 400, 400, 150);
        font.draw(b, ans2 , 550, TowerOfLife.WORLD_HEIGHT * 100 - 300);

        b.draw(answerBox, 250, TowerOfLife.WORLD_HEIGHT * 100 - 600, 400, 150);
        font.draw(b, ans3 , 300, TowerOfLife.WORLD_HEIGHT * 100 - 500);

        isAnswerRight();
    }



    public void isAnswerRight() {
        answerImage.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                Gdx.app.log("minigame", "WEAREHERE");
                youAreGoddamnRight = true;
                return true;
            }
        });
    }
}

