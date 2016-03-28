package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.LoadingScreen;

public class Application extends Game {

	public static final float VERSION = 1.0f;
	public static final String TITLE = "Slidez";
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 420;

	public OrthographicCamera camera;
	public SpriteBatch batch;

	public BitmapFont font;
	public AssetManager assets;

	@Override
	public void create () {
		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.CORAL);

		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		assets.dispose();
		this.getScreen().dispose();
	}
}
