package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by DScoder on 28.03.2016.
 */
public class MainMenuScreen implements Screen {

    private final Application app;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Skin skin;

    private TextButton playButton, exitButton;

    public MainMenuScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("MENU");
        Gdx.input.setInputProcessor(stage);
        stage.clear();


        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
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
        shapeRenderer.dispose();
    }

    private  void initButtons(){
        playButton = new TextButton("Play", skin, "default");
        playButton.setPosition(110, 260);
        playButton.setSize(280, 60);
        playButton.addAction(sequence(alpha(0f), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        playButton.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                app.setScreen(app.playScreen);

            }
        });

        exitButton = new TextButton("Exit", skin, "default");
        exitButton.setPosition(110, 190);
        exitButton.setSize(280, 60);
        exitButton.addAction(sequence(alpha(0f), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        exitButton.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(playButton);
        stage.addActor(exitButton);
    }
}
