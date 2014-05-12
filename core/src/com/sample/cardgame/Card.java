package com.sample.cardgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Card extends Actor {
	TextureRegion frontTexture, backTexture;
	public boolean isFront = false;
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
