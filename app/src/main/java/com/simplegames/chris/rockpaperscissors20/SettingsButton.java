package com.simplegames.chris.rockpaperscissors20;

import com.google.android.material.card.MaterialCardView;

public class SettingsButton {
    private final int[] buttonColours;
    private final String buttonText;
    private MaterialCardView buttonLayout;

    public SettingsButton(int[] colours, String text){
        buttonColours = colours;
        buttonText = text;
    }

    public int[] getButtonColours() {return buttonColours;}
    public String getButtonText(){
        return buttonText;
    }
    public void setButtonLayout(MaterialCardView layout) {
        this.buttonLayout = layout;
    }
    public MaterialCardView getButtonLayout(){
        return buttonLayout;
    }
}
