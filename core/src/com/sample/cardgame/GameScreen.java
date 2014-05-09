package com.sample.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Pool;

public class GameScreen implements Screen {
	
	final MyCardGame game;
	Texture background;
	SpriteBatch gameBg;
	Stage stage;
	Skin skin;
	BitmapFont font;
	TextButtonStyle textStyle;
	ArrayList<Card> deck;
	
	NinePatch bubble;
	
	public GameScreen(MyCardGame myCardGame) {
		game = myCardGame;

	}
	
	public static NinePatch processNinePatchFile(String filename) {
	    final Texture t = new Texture(Gdx.files.internal(filename));
	    final int width = t.getWidth() - 2;
	    final int height = t.getHeight() - 2;
	    return new NinePatch(new TextureRegion(t, 1, 1, width, height), 15, 15, 15, 15);
	}
	
	@Override
	public void render(float delta) {
		
		//draw background
		gameBg.begin();
		gameBg.draw(Variables.textureAtlasOthers.findRegion("background"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameBg.end();
		
		stage.act(Gdx.graphics.getDeltaTime());		//not sure if still needed
	    stage.draw();
	}

	@Override
	public void show() {
		game.camera.update();
		
		gameBg = new SpriteBatch();
		
		//sets the stage up
		stage = new Stage();
	    Gdx.input.setInputProcessor(stage);
	    
	    //initialize deck
	    initCardsInDeck();
	    
	    //create MoveToAction pool
	    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
	        protected MoveToAction newObject(){
	            return new MoveToAction();
	        }
	    };
	    
	    //add cards to stage
	    for (int i = 4, j = (Gdx.graphics.getWidth() / 5); i > -1; i--, j += deck.get(0).getWidth() + 10) {
	    	stage.addActor(deck.get(i));
	    	final MoveToAction move = actionPool.obtain();
    	    move.setPosition(j, Gdx.graphics.getHeight() / 3);
    	    move.setDuration(2f);
    	    deck.get(i).addAction(move);
	    }
	    
	    //create chat bubbles
//	    bubble = processNinePatchFile("blue_bubble.9.png");
//	    NinePatchDrawable btnNormal9Drawable = new NinePatchDrawable(bubble);
//	    
//	    TextButtonStyle style = new TextButtonStyle(btnNormal9Drawable, btnNormal9Drawable, btnNormal9Drawable, new BitmapFont());
//	    style.fontColor = new Color(1, 1, 1, 1);
//
//	    Button button = new TextButton("The quick brown fox jumped over the lazy dog", style);
//	    button.setPosition(0, 0);
//	    button.setDisabled(true);
//	    
//	    stage.addActor(button);
		
	}
	
	private void initCardsInDeck() {
		deck = new ArrayList<Card>();
	
		for (int i = 0; i < Variables.suits.length; i++) {
			for (int j = 0; j < Variables.memberNumber.length; j++) {
				String filename = Variables.suits[i] + "" + Variables.memberNumber[j];
				Card temp = new Card(filename, Variables.memberNumber[j], Variables.suits[i]);
				temp.setPosition(20, Gdx.graphics.getHeight() / 3);
				deck.add(temp);
				
			}
		}
		//randomize deck
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
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
		stage.dispose();
	}

}
