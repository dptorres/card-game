package com.sample.cardgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sample.cardgame.MyCardGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Card Game";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new MyCardGame(), config);
	}
}
