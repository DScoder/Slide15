package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Application;
import com.mygdx.game.actors.SlideButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by DScoder on 29.03.2016.
 */
public class PlayScreen implements Screen {
    private final Application app;
    private Stage stage;
    private Skin skin;
    private SlideButton[][] buttonGreed;

    private int boardSize = 4;
    private int holeX, holeY;

    private TextButton buttonBack;

    private Label labelInfo;

    public PlayScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
    }


    @Override
    public void show() {
        System.out.println("PLAY");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initNavigationButtons();
        initInfoLabel();
        initGrid();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initNavigationButtons() {
        buttonBack = new TextButton("Back", skin, "default");
        buttonBack.setPosition(20, app.camera.viewportHeight - 70);
        buttonBack.setSize(100, 50);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mainMenuScreen);
            }
        });
        stage.addActor(buttonBack);

    }

    private void initInfoLabel(){
        labelInfo = new Label("Hello! Click any number tile to begin!", skin, "default");
        labelInfo.setPosition(15, 310);
        labelInfo.setAlignment(Align.center);
        labelInfo.addAction(sequence(alpha(0f), delay(.5f), fadeIn(.5f)));
        stage.addActor(labelInfo);
    }

    private void initGrid() {
        Array<Integer> nums = new Array<Integer>();
        for (int i = 1; i < boardSize * boardSize; i++) {
            nums.add(i);
        }
        nums.shuffle();
        holeX = MathUtils.random(boardSize - 1);
        holeY = MathUtils.random(boardSize - 1);
        buttonGreed = new SlideButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i != holeY || j != holeX) {
                    int id = nums.removeIndex(0);
                    buttonGreed[i][j] = new SlideButton(id + "", skin, "default", id);
                    buttonGreed[i][j].setPosition(app.camera.viewportWidth / 7 * 2 + 51 * j, app.camera.viewportHeight / 5 * 3 - 51 * i);
                    buttonGreed[i][j].setSize(50, 50);
                    buttonGreed[i][j].addAction(sequence(alpha(0f), delay((j + 1 + i * boardSize)/60f),
                            parallel(fadeIn(.5f), moveBy(0, -10, .25f, Interpolation.pow5Out))));

                    buttonGreed[i][j].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            int buttonX = 0, buttonY = 0;
                            boolean buttonFound = false;
                            SlideButton selectSlideButton = (SlideButton) event.getListenerActor();

                            for (int i = 0; i < boardSize && !buttonFound; i++) {
                                for (int j = 0; j < boardSize && !buttonFound; j++) {
                                    if (buttonGreed != null && selectSlideButton == buttonGreed[i][j]) {
                                        buttonX = j;
                                        buttonY = i;
                                        buttonFound = true;
                                    }
                                }
                            }

                            if (holeX == buttonX || holeY == buttonY) {
//                                System.out.println("Button found! clicked:" + selectSlideButton.getId() + " " + buttonX + " " + buttonY);
                                moveButtons(buttonX, buttonY);

                                if(solutionFound()){
                                    labelInfo.clear();
                                    labelInfo.setText("Solution Found!");
                                    labelInfo.addAction(sequence(alpha(1f), delay(3f), fadeOut(3f, Interpolation.pow5Out)));
                                }
                            } else {
                                labelInfo.clear();
                                labelInfo.setText("Invalid Move...");
                                labelInfo.addAction(sequence(alpha(1f), delay(1f), fadeOut(1f, Interpolation.pow5Out)));
                            }
                        }
                    });
                    stage.addActor(buttonGreed[i][j]);
                }
            }
        }
    }

    private void moveButtons(int x, int y) {
        SlideButton button;
        if (x < holeX) {
            for (; holeX > x; holeX--) {
                button = buttonGreed[holeY][holeX - 1];
                button.addAction(moveBy(51, 0, .5f, Interpolation.pow5Out));
                buttonGreed[holeY][holeX] = button;
                buttonGreed[holeY][holeX - 1] = null;
            }
        } else {
            for (; holeX < x; holeX++) {
                button = buttonGreed[holeY][holeX + 1];
                button.addAction(moveBy(-51, 0, .5f, Interpolation.pow5Out));
                buttonGreed[holeY][holeX] = button;
                buttonGreed[holeY][holeX + 1] = null;
            }
        }
        if (y < holeY) {
            for (; holeY > y; holeY--) {
                button = buttonGreed[holeY - 1][holeX];
                button.addAction(moveBy(0, -51, .5f, Interpolation.pow5Out));
                buttonGreed[holeY][holeX] = button;
                buttonGreed[holeY - 1][holeX] = null;
            }
        } else
            for (; holeY < y; holeY++) {
                button = buttonGreed[holeY + 1][holeX];
                button.addAction(moveBy(0, +51, .5f, Interpolation.pow5Out));
                buttonGreed[holeY][holeX] = button;
                buttonGreed[holeY + 1][holeX] = null;
            }
    }

    private boolean solutionFound(){
        int idCheck = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(buttonGreed[i][j] != null) {
                    if(buttonGreed[i][j].getId() == idCheck++){
                        if(idCheck == 16){
                            return true;
                        }
                    } else {
                        return  false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
