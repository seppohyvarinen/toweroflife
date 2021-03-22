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

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class MiniGame {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    ArrayList<String> sorrowProblems;
    ArrayList<String> sorrowProblemOne;
    ArrayList<String> sorrowProblemTwo;
    ArrayList<String> sorrowProblemThree;

    ArrayList<String> hateProblems;
    ArrayList<String> hateProblemOne;
    ArrayList<String> hateProblemTwo;
    ArrayList<String> hateProblemThree;

    ArrayList<String> fearProblems;
    ArrayList<String> fearProblemOne;
    ArrayList<String> fearProblemTwo;
    ArrayList<String> fearProblemThree;
    Texture problemBox;
    Texture answerBox;
    Image answerImage;

    public static float problemWidth = 900f;
    public static float problemHeight = 200f;

    public static boolean youAreGoddamnRight = false;

    boolean hate = false;
    boolean sorrow = false;
    boolean fear = false;
    ArrayList<String> problemList;
    ArrayList<String> answerList;
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
        sorrowProblemOne = new ArrayList<>();
        sorrowProblemTwo = new ArrayList<>();
        sorrowProblemThree = new ArrayList<>();

        hateProblems = new ArrayList<>();
        hateProblemOne = new ArrayList<>();
        hateProblemTwo = new ArrayList<>();
        hateProblemThree = new ArrayList<>();
        fearProblems = new ArrayList<>();
        fearProblemOne = new ArrayList<>();
        fearProblemTwo = new ArrayList<>();
        fearProblemThree = new ArrayList<>();

        problemList = new ArrayList<>();
        answerList = new ArrayList<>();

        fearProblems.add("fearproblem1");
        fearProblems.add("fearproblem2");
        fearProblems.add("fearproblem3");

        fearProblemOne.add("fearproblem1 wrong");
        fearProblemOne.add("fearproblem1 wrong2");
        fearProblemOne.add("correct!");

        fearProblemTwo.add("fearproblem2 wrong");
        fearProblemTwo.add("fearproblem2 wrong2");
        fearProblemTwo.add("correct!");

        fearProblemThree.add("fearproblem3 wrong");
        fearProblemThree.add("fearproblem3 wrong2");
        fearProblemThree.add("correct!");


        sorrowProblems.add("sorrowproblem1");
        sorrowProblems.add("sorrowproblem2");
        sorrowProblems.add("sorrowproblem3");

        sorrowProblemOne.add("sorr.problem1 wrong");
        sorrowProblemOne.add("sorr.problem1 wrong2");
        sorrowProblemOne.add("correct!");

        sorrowProblemTwo.add("sorr.problem2 wrong");
        sorrowProblemTwo.add("sorr.problem2 wrong2");
        sorrowProblemTwo.add("correct!");

        sorrowProblemThree.add("sorr.problem3 wrong");
        sorrowProblemThree.add("sorr.problem3 wrong2");
        sorrowProblemThree.add("correct!");



        hateProblems.add("hateproblem1");
        hateProblems.add("hateproblem2");
        hateProblems.add("hateproblem3");

        hateProblemOne.add("hateproblem 1 wrong");
        hateProblemOne.add("hateproblem 1 wrong2");
        hateProblemOne.add("correct!");

        hateProblemTwo.add("hateproblem 2 wrong");
        hateProblemTwo.add("hateproblem 2 wrong2");
        hateProblemTwo.add("correct!");

        hateProblemThree.add("hateproblem 3 wrong");
        hateProblemThree.add("hateproblem 3 wrong2");
        hateProblemThree.add("correct!");

        problemBox = new Texture(Gdx.files.internal("problem_box.png"));
        answerBox = new Texture(Gdx.files.internal("answer_box.png"));
        answerImage = new Image(answerBox);

        if (e.equals("sorrowBox")) {
            problemList.addAll(sorrowProblems);
        }  else if (e.equals("hateBox")) {
            problemList.addAll(hateProblems);
        }  else {
            problemList.addAll(fearProblems);
        }

        pIndex = MathUtils.random(0, 2);
        problem = problemList.get(pIndex);

        if (e.equals("sorrowBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemOne.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemOne.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemOne.get(aIndex);

            }  else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemTwo.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemTwo.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemTwo.get(aIndex);
            }  else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemThree.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemThree.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemThree.get(aIndex);
            }
        }  else if (e.equals("fearBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemOne.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemOne.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemOne.get(aIndex);

            }  else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemTwo.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemTwo.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemTwo.get(aIndex);
            }  else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemThree.get(aIndex);
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemThree.get(aIndex);
                while (helper == aIndex || helper2 ==aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemThree.get(aIndex);
            }
        }  else {
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

