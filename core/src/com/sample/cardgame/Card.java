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
	TextureRegion frontTexture, backTexture;
	public boolean isSelected, isFront = false;
	String memberNumber;
	String suit;
	Sprite mySprite;
	float width, height;
	
	
	public Card(String filePath, String memberNumber, String suit) {
		this.memberNumber = memberNumber;
		this.suit = suit;
		
		//get card image from texture pack
		frontTexture = Variables.textureAtlasCards.findRegion(filePath);
		backTexture = Variables.textureAtlasCards.findRegion("back");
		width = frontTexture.getRegionWidth() * Variables.getWorldRatio();
		height = frontTexture.getRegionHeight() * Variables.getWorldRatio();
		setBounds(getX(), getY(), width, height);
		
		setName(filePath);
		addClickListener();
	}

	//listener for userCard
	private void addClickListener() {
		final Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
	        protected MoveToAction newObject(){
	            return new MoveToAction();
	        }
	    };
		
		addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				final MoveToAction move = actionPool.obtain();
				if (isSelected) {
					move.setPosition(getX(), getY() - 20);
					isSelected = false;
				} else {
					move.setPosition(getX(), getY() + 20);
					isSelected = true;
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
	
	public void setIsFront(boolean isFront) {
		this.isFront = isFront;
	}
	
	 @Override
     public void draw(Batch batch, float alpha){
		 if (isFront) {
			batch.draw(frontTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), width, 
				 height, this.getScaleX(), this.getScaleY(), this.getRotation());

		 } else {
			 batch.draw(backTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), width, 
					 height, this.getScaleX(), this.getScaleY(), this.getRotation());
		 }
	 }
	 
}
