package com.simplegames.chris.rockpaperscissors20;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;

public class SettingsButton {
    private int buttonTL;
    private int buttonBR;
    private String buttonText;
    private MaterialCardView buttonLayout;

    public SettingsButton(int TL, int BR, String Text){
        buttonTL = TL;
        buttonBR = BR;
        buttonText = Text;
    }

    public int getButtonTL(){
        return buttonTL;
    }
    public int getButtonBR(){
        return buttonBR;
    }
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
