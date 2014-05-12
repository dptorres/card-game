package com.sample.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	ArrayList<Card> deck, dumpedCards;
	Table rootTable;
	float cardWidth, cardHeight;
	
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
	    
	}
	
	private void setUpTableAndWidgets() {
		skin = new Skin();
		font = new BitmapFont(Gdx.files.internal("font/default.fnt"));
		addSkinObjects();
		
		//create root table for page layout
	    rootTable = new Table();
		rootTable.setFillParent(true);
		
		//create tables for different widgets
		final Table dumpedCardTable = createDumpedCardTable();
		final Table avatarTableOne = createAvatarTable(1);
		final Table avatarTableTwo = createAvatarTable(2);
		final Table avatarTableThree = createAvatarTable(3);
//		final Table playerCardTable = createPlayerCardTable();
		
		//create jackpot image
		Image jackpot = new Image(new Texture(Gdx.files.internal("data/jackpot.png")));
		
		//add tables to the root table
		rootTable.add(dumpedCardTable).pad(10).left();
		rootTable.add(avatarTableOne).pad(10).expandX().center();
		rootTable.add(jackpot).pad(10).right().row();
		rootTable.add(avatarTableTwo).padTop(50).left();
		rootTable.add().padTop(50).expandX().center();
		rootTable.add(avatarTableThree).padTop(50).padRight(30).right().row();
//		rootTable.add(playerCardTable).padTop(50).center().expandX();
		rootTable.top();
		
		stage.addActor(rootTable);
		
	}

//	private Table createPlayerCardTable() {
//		final Table playerTable = new Table();
//		
//		//create MoveToAction pool
//	    Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
//	        protected MoveToAction newObject(){
//	            return new MoveToAction();
//	        }
//	    };
//	    
//	    //add cards to stage
//	    for (int i = 12, j = (Variables.worldWidth / 5); i > -1; i--, j += cardWidth - 20) {
//	    	playerTable.addActor(playerCards.get(i));
//	    	final MoveToAction move = actionPool.obtain();
//    	    move.setPosition(j, Variables.worldHeight / 3);
//    	    move.setDuration(2f);
//    	    playerCards.get(i).addAction(move);
//	    }
//		
//		return playerTable;
//	}

	private Table createAvatarTable(int playerNumber) {
		final Table avatarTable = new Table();
		TextButtonStyle buttonStyle = createTextButtonStyle("userNameBG");
		
		final TextButton userName = new TextButton("User" + playerNumber + "\n P305", buttonStyle);
		final Image avatar = new Image(new Texture(Gdx.files.internal("data/avatar.png")));
		avatar.setScale(1.5f, 1.5f);
		userName.setDisabled(true);
		if (playerNumber == 1) {
			avatarTable.add(userName);
			avatarTable.add(avatar);
			
		} else {
			avatarTable.add(avatar).row();
			avatarTable.add(userName);
		}
		
		return avatarTable;
	}

	private Table createDumpedCardTable() {
		final Table dumpCardTable = new Table(); 
		TextButtonStyle buttonStyle = createTextButtonStyle("blackBG");
		
		final TextButton dumpCardBtn = new TextButton("Dumped Card", buttonStyle);
		dumpCardBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				//remove all components
				dumpCardTable.reset();
				
				//transfers one card from the deck to the dumpedCards array
				dumpedCards.add(deck.get(0));
				deck.remove(0);
				
				//add components back to table
				dumpedCards.get(dumpedCards.size() - 1).setIsFront(true);
				dumpCardTable.add(deck.get(0)).pad(0);
				dumpCardTable.add(dumpedCards.get(dumpedCards.size() - 1)).pad(0).row();
				dumpCardTable.add(dumpCardBtn).colspan(2);
			}
		});
		
		dumpCardTable.add(deck.get(0)).pad(0).row();
		dumpCardTable.add(dumpCardBtn).colspan(2);
		return dumpCardTable;
	}
	
	private TextButtonStyle createTextButtonStyle(String drawableName) {
		TextButtonStyle textStyle = new TextButtonStyle();
	    textStyle.up = skin.getDrawable(drawableName); 
	    textStyle.font = font;
	    textStyle.fontColor = Color.WHITE;
	    textStyle.downFontColor = Color.GRAY;
		return textStyle;
	}

	private void addSkinObjects() {
	    skin.add("blackBG", new Texture(Gdx.files.internal("data/imBack.png")));
	    skin.add("userNameBG", new Texture(Gdx.files.internal("data/glass.png")));
	    skin.add("avatar", new Texture(Gdx.files.internal("data/avatar.png")));
	    
	    Pixmap trans = new Pixmap((int)cardWidth, (int)cardHeight, Format.RGBA8888);
	    trans.setColor(1, 1, 1, 0);
	    trans.fill();
	    skin.add("transCard", new Texture(trans));
		
	}
	
	private void initCardsInDeck() {
		deck = new ArrayList<Card>();
		dumpedCards = new ArrayList<Card>();
	
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
		
		cardWidth = deck.get(0).getWidth();
		cardHeight = deck.get(0).getHeight();
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
