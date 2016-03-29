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
    private Image splashFont;

    public SplashScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        System.out.println("SPLASH");
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.mainMenuScreen);
            }
        };

        Texture splashTexture = app.assets.get("Cat2.png", Texture.class);
        splashImg = new Image(splashTexture);
        splashImg.setSize(164,164);
        splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);
        splashImg.setPosition(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2  + splashImg.getHeight()/1.5f);
        splashImg.addAction(sequence(alpha(0f), scaleTo(.01f, .01f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f,1f,2.5f,Interpolation.pow5),
                        moveTo(stage.getWidth()/2 - splashImg.getWidth()/2, stage.getHeight()/2, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(2f)));

        Texture splashTexture2 = app.assets.get("DScoder.png", Texture.class);
        splashFont = new Image(splashTexture2);
        splashFont.setOrigin(splashFont.getWidth()/2, splashFont.getHeight()/2);
        splashFont.setPosition(stage.getWidth()/2 - splashFont.getWidth()/2, stage.getHeight()/2  + splashFont.getHeight()/1.5f);
        splashFont.addAction(sequence(alpha(0f), scaleTo(.01f, .01f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f,1f,2.5f,Interpolation.pow5),
                        moveTo(stage.getWidth()/2 - splashFont.getWidth()/2, stage.getHeight()/2 - 120, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(2f), run(transitionRunnable)));

        stage.addActor(splashImg);
        stage.addActor(splashFont);
    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
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
