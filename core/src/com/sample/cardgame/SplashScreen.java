package com.sample.cardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SplashScreen implements Screen {
	
	MyCardGame game;
	SpriteBatch splashBg;
	Label labelLoading;
	Stage splashStage;

	String cardsPath = "data/cardspack.pack";
	String othersPath = "data/others.pack";
	
	float percent = 0f;
	
	public SplashScreen(MyCardGame game) {
		this.game = game;
		
	}

	@Override
	public void render(float delta) {
		try {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
			
			//draw background
			splashBg.begin();
			splashBg.draw(Variables.textureAtlasOthers.findRegion("background"), 0, 0, Variables.worldWidth, Variables.worldHeight);
			splashBg.end();
			
			splashStage.draw();
			
			//change percentage of loading label
			percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);
			labelLoading.setText("Loading " + (int) (percent * 101) + "%");
			labelLoading.setScale(10f);
			
			//checks if the assets manager has finished loading the assets
			if (game.manager.update()) {
				if ((int) (percent * 101) == 100f) {
					game.setScreen(new GameScreen(game));
				}
			}
			
		} catch(Exception e) {
			Gdx.app.error("SplashScreen ERROR render()", e.toString());
		}

	}

	@Override
	public void show() {
		game.camera.update();
		
		splashBg = new SpriteBatch();
		
		//create stage
		splashStage = new Stage();
		
		//create table for screen layout
		Table table = new Table();
		table.setFillParent(true);
		
		//load textures for all images in game
		game.manager.load(cardsPath, TextureAtlas.class);
		game.manager.load(othersPath, TextureAtlas.class);
		game.manager.finishLoading();
		
		Variables.textureAtlasCards = game.manager.get(cardsPath, TextureAtlas.class);
		Variables.textureAtlasOthers = game.manager.get(othersPath, TextureAtlas.class);
		
		//create loading label while waiting for the assets to load
		Texture loadingLabelTexture = new Texture(Gdx.files.internal("font/mrchuckles_blue.png"));
		loadingLabelTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		BitmapFont loadingFont = new BitmapFont(Gdx.files.internal("font/mrchuckles_blue.fnt"), new TextureRegion(loadingLabelTexture), false);
		LabelStyle loadingLabelStyle = new LabelStyle(loadingFont, Color.WHITE);
		
		labelLoading = new Label("Loading...", loadingLabelStyle);
		labelLoading.setFontScale(0.75f);
		
		//add loading label to table
		table.add(labelLoading);
		
		//add table to stage
		splashStage.addActor(table);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		splashStage.dispose();
	}

}
