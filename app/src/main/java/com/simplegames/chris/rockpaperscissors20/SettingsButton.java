package com.simplegames.chris.rockpaperscissors20;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsButton {
    private int buttonTL;
    private int buttonBR;
    private String buttonText;
    private ConstraintLayout buttonLayout;

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
    public ConstraintLayout getButtonLayout(){
        return buttonLayout;
    }
}
