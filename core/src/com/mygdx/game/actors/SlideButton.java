package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by DScoder on 29.03.2016.
 */
public class SlideButton extends TextButton {
    private int id;

    public SlideButton(String text, Skin skin, String style, int id){
        super(text, skin, style);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
