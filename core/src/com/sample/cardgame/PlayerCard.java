package com.sample.cardgame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;

public class PlayerCard extends Card {
	public boolean isSelected;
	public Card card;
	
	public PlayerCard(String filePath, String memberNumber, String suit) {
		super(filePath, memberNumber, suit);
		addClickListener();
		
	}
	
	public PlayerCard(Card card) {
		this(card.getName(), card.getMemberNumber(), card.getSuitNumber());

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
	
}
