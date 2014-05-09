package com.sample.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
	
	final MyCardGame game;
	SpriteBatch gameBg;
	Stage stage;
	Skin skin;
	BitmapFont font;
	TextButtonStyle textStyle;
	ArrayList<Card> deck;
	Table rootTable;
	
	NinePatch bubble;
	
	public GameScreen(MyCardGame myCardGame) {
		game = myCardGame;

	}
	
	@Override
	public void render(float delta) {
		
		//draw background
		gameBg.begin();
		gameBg.draw(Variables.textureAtlasOthers.findRegion("background"), 0, 0, Variables.worldWidth, Variables.worldHeight);
		gameBg.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
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
	    
	    //set up table
	    setUpTableAndWidgets();
	    
//	    //create MoveToAction pool
//	    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
//	        protected MoveToAction newObject(){
//	            return new MoveToAction();
//	        }
//	    };
//	    
//	    //add cards to stage
//	    for (int i = 12, j = (Variables.worldWidth / 5); i > -1; i--, j += deck.get(0).getWidth() - 20) {
//	    	stage.addActor(deck.get(i));
//	    	final MoveToAction move = actionPool.obtain();
//    	    move.setPosition(j, Variables.worldHeight / 3);
//    	    move.setDuration(2f);
//    	    deck.get(i).addAction(move);
//	    }
	    
	}
	
	private void setUpTableAndWidgets() {
		skin = new Skin();
		font = new BitmapFont(Gdx.files.internal("font/default.fnt"));
		addSkinObjects();
		TextButtonStyle buttonStyle = createTextButtonStyle();
		
	    rootTable = new Table();
		rootTable.setFillParent(true);
		
		final Table dumpCardTable = new Table(); 
		
		final TextButton dumpCardBtn = new TextButton("Dumped Card", buttonStyle);
		dumpCardBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				dumpCardTable.removeActor(dumpCardBtn);
				dumpCardTable.removeActor(deck.get(0));
				dumpCardTable.removeActor(deck.get(1));
				dumpCardTable.add(deck.get(1)).pad(0);
				deck.get(0).setIsFront(true);
				dumpCardTable.add(deck.get(0)).pad(0).row();
				dumpCardTable.add(dumpCardBtn);
			}
		});
		
		dumpCardTable.add(deck.get(0)).pad(0).row();
		dumpCardTable.add(dumpCardBtn);
		
		rootTable.add(dumpCardTable).pad(10).left();
		rootTable.top();
		
		stage.addActor(rootTable);
		
	}
	
	private TextButtonStyle createTextButtonStyle() {
		TextButtonStyle textStyle = new TextButtonStyle();
	    textStyle.up = skin.getDrawable("blackBG"); 
	    textStyle.font = font;
	    textStyle.fontColor = Color.WHITE;
	    textStyle.downFontColor = Color.GRAY;
		return textStyle;
	}

	private void addSkinObjects() {
//		Pixmap balancePixmap = new Pixmap(64, 64, Format.RGBA8888);
//		balancePixmap.setColor(0, 0, 0, 1);
//		balancePixmap.fill();
	    skin.add("blackBG", new Texture(Gdx.files.internal("data/imBack.png")));
		
	}

	private void initCardsInDeck() {
		deck = new ArrayList<Card>();
	
		for (int i = 0; i < Variables.suits.length; i++) {
			for (int j = 0; j < Variables.memberNumber.length; j++) {
				String filename = Variables.suits[i] + "" + Variables.memberNumber[j];
				Card temp = new Card(filename, Variables.memberNumber[j], Variables.suits[i]);
				temp.setPosition(20, Variables.worldHeight / 3);
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
