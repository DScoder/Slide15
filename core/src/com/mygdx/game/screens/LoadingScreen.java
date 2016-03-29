package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Application;

/**
 * Created by DScoder on 28.03.2016.
 */
public class LoadingScreen implements Screen {

    private final Application app;
    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final Application app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets(){
        app.assets.load("Cat2.png", Texture.class);
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
        System.out.println("LOADING");
        shapeRenderer.setProjectionMatrix(app.camera.combined);
        this.progress = 0f;
        queueAssets();
    }

    private void update(float delta){
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .1f);
        if(app.assets.update() && progress >= app.assets.getProgress() - 0.001f){
            app.setScreen(app.splashScreen);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, app.camera.viewportHeight/2 - 8, app.camera.viewportWidth - 64, 16);
        shapeRenderer.setColor(Color.FIREBRICK);
        shapeRenderer.rect(32, app.camera.viewportHeight/2 - 8, progress * (app.camera.viewportWidth - 64), 16);
        shapeRenderer.end();


        app.batch.begin();
        app.font.draw(app.batch, "Screen: LOADING",20, 20);
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
        shapeRenderer.dispose();
    }

}
