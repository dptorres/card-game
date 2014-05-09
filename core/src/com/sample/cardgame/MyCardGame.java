package com.sample.cardgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyCardGame extends Game {
	
	SpriteBatch batch;
	AssetManager manager;
	OrthographicCamera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		manager = new AssetManager();
		
		//sets up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
		this.setScreen(new SplashScreen(this));
		
	}
	
	public void render() {
		super.render();
	
	}

	public void dispose() {
		batch.dispose();

	}
	
}
