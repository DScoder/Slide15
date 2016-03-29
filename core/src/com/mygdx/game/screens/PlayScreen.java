package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Application;
import com.mygdx.game.actors.SlideButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

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
        initGrid();
    }

    private void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        app.batch.begin();
        app.font.draw(app.batch, "Screen: PLAY",20, 20);
        app.batch.end();

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

    private void initNavigationButtons(){
        buttonBack = new TextButton("Back", skin, "default");
        buttonBack.setPosition(20, app.camera.viewportHeight - 70);
        buttonBack.setSize(100, 50);
        buttonBack.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                app.setScreen(app.mainMenuScreen);
            }
        });
        stage.addActor(buttonBack);

    }
    private void initGrid(){
        holeX = 3;
        holeY = 3;
        buttonGreed = new SlideButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int id = j + 1 + i*boardSize;
                buttonGreed[i][j] = new SlideButton(id + "", skin, "default", id);
                buttonGreed[i][j].setPosition(app.camera.viewportWidth/7*2 + 51*j, app.camera.viewportHeight/5*3 - 51*i);
                buttonGreed[i][j].setSize(50, 50);

                buttonGreed[i][j].addAction(sequence(alpha(0f), delay(id / 15f),
                        parallel(fadeIn(.5f), moveBy(0, -10, .25f, Interpolation.pow5Out))));

                stage.addActor(buttonGreed[i][j]);
            }
        }
    }
}
