package com.simplegames.chris.rockpaperscissors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.simplegames.chris.rockpaperscissors.activity.SettingsActivity;
import com.simplegames.chris.rockpaperscissors.utils.SharedPreferenceKeys;
import com.simplegames.chris.rockpaperscissors.utils.UIElements;
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew;

import java.util.ArrayList;

public class SettingsButtonAdapter extends RecyclerView.Adapter<SettingsButtonAdapter.SettingsButtonViewHolder> {
    private static ArrayList<SettingsButton> buttonArrayList;
    private OnItemClickListener buttonListener;
    private final Context buttonContext;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        buttonListener = listener;
    }

    public class SettingsButtonViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView buttonLayout;
        final ImageView buttonBackground;
        final TextView buttonText;

        public SettingsButtonViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.buttonLayout = itemView.findViewById(R.id.buttonColour);
            this.buttonBackground = itemView.findViewById(R.id.buttonColourBackground);
            this.buttonText = itemView.findViewById(R.id.buttonColourText);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int oldPos = ValuesNew.INSTANCE.getBackgroundGradient();
                    MaterialCardView oldLayout = buttonArrayList.get(oldPos).getButtonLayout();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ValuesNew.INSTANCE.setBackgroundGradient(position);
                        ((SettingsActivity) buttonContext).determineBackground();
                        setButtonStroke(oldLayout, oldPos);
                        setButtonStroke(buttonLayout, position);
                        ValuesNew.INSTANCE.saveValue(buttonContext,
                                SharedPreferenceKeys.KEY_SETTING_BACKGROUND_GRADIENT,
                                position);
                    }
                }
            });
        }
    }

    public SettingsButtonAdapter(ArrayList<SettingsButton> settingsButtonArrayList, Context context) {
        buttonArrayList = settingsButtonArrayList;
        buttonContext = context;
    }

    @NonNull
    @Override
    public SettingsButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_background_button_layout, parent, false);
        return new SettingsButtonViewHolder(v, buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsButtonViewHolder holder, int position) {
        SettingsButton currentItem = buttonArrayList.get(position);
        currentItem.setButtonLayout(holder.buttonLayout);
        holder.buttonText.setText(currentItem.getButtonText());

        UIElements.setBackground(holder.buttonBackground, currentItem.getButtonColours(), 0f);

        setButtonStroke(holder.buttonLayout, position);
    }

    @Override
    public int getItemCount() {
        return buttonArrayList.size();
    }

    private static void setButtonStroke(MaterialCardView layout, int position) {
        if (ValuesNew.INSTANCE.getBackgroundGradient() == position) {
            layout.setStrokeWidth(UIElements.dpToFloat(2.5f));
        } else {
            layout.setStrokeWidth(0);
        }
    }
}
