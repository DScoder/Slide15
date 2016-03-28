package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by DScoder on 28.03.2016.
 */
public class SplashScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Image splashImg;

    public SplashScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
        Gdx.input.setInputProcessor(stage);
        Texture splashTexture = app.assets.get("Cat2.png", Texture.class);
        splashImg = new Image(splashTexture);
        splashImg.setSize(164,164);
        splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);
        stage.addActor(splashImg);
    }

    @Override
    public void show() {
        splashImg.setPosition(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2  + splashImg.getHeight()/1.5f);
        splashImg.addAction(sequence(alpha(0f), scaleTo(.01f, .01f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f,1f,2.5f,Interpolation.pow5),
                        moveTo(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2, 2f, Interpolation.swing)),
                delay(1.5f, fadeOut(2f))));
    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
        app.batch.begin();
        app.font.draw(app.batch, "Screen: SPLASH",20, 20);
		app.batch.end();
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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

    }
}
