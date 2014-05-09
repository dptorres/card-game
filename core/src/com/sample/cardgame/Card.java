package com.sample.cardgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;

public class Card extends Actor {
	TextureRegion texture;
	public boolean selected;
	String memberNumber;
	String suit;
	Sprite mySprite;
	
	
	public Card(String filePath, String memberNumber, String suit) {
		this.memberNumber = memberNumber;
		this.suit = suit;
		
		texture = Variables.textureAtlasCards.findRegion(filePath);
		setBounds(getX(), getY(), texture.getRegionWidth(), texture.getRegionHeight());
		
		setName(filePath);
		
		final Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
	        protected MoveToAction newObject(){
	            return new MoveToAction();
	        }
	    };
		
		addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				final MoveToAction move = actionPool.obtain();
				if (selected) {
					move.setPosition(getX(), getY() - 20);
					selected = false;
				} else {
					move.setPosition(getX(), getY() + 20);
					selected = true;
				}
	    	    
	    	    move.setDuration(0.2f);
	    	    addAction(move);
	    	    actionPool.free(move);
			}
		});
	}
	
	public String getMemberNumber() {
		return memberNumber;
	}
	
	public String getSuitNumber() {
		return suit;
	}
	
	 @Override
     public void draw(Batch batch, float alpha){
		 //put conditional for front or back display of card
		 
		 batch.draw(texture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), texture.getRegionWidth(), 
				 texture.getRegionHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());

	 }
	 
}
