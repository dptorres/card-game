package com.sample.cardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Variables {
	
	public static TextureAtlas textureAtlasCards, textureAtlasOthers;
	
	public static String[] memberNumber = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"};
	public static String[] suits = {"c", "d", "h", "s"};
	
	public static int worldWidth = Gdx.graphics.getWidth();
	public static int worldHeight = Gdx.graphics.getHeight();
	
	public static float getWorldRatio() {
		float ratioSize = 1.0f;
		float worldTargetWidth = 1024;
		float worldTargetHeight = 600;

		if (worldWidth <= worldHeight) {		
			// Portrait
			ratioSize = worldWidth / worldTargetWidth;
		} else {
			// Landscape
			ratioSize = worldHeight / worldTargetHeight;
		}

		return ratioSize;
	}

}
