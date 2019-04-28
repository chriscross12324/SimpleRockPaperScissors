package com.simplegames.chris.rockpaperscissors20;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsButtonAdapter extends RecyclerView.Adapter<SettingsButtonAdapter.SettingsButtonViewHolder> {
    private ArrayList<SettingsButton> buttonArrayList;
    private OnItemClickListener buttonListener;
    private static Context buttonContext;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        buttonListener = listener;
    }

    public static class SettingsButtonViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout buttonLayout;
        public TextView buttonText;

        public SettingsButtonViewHolder(@NonNull View itemView, final OnItemClickListener listener, Context context) {
            super(itemView);
            this.buttonLayout = itemView.findViewById(R.id.constraintLayout);
            buttonText = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            Values.SPBackgroundNumber = position;
                            ((Settings)buttonContext).determineBackground();
                        }
                    }
                }
            });
        }
    }

    public SettingsButtonAdapter(ArrayList<SettingsButton> settingsButtonArrayList, Context context){
        buttonArrayList = settingsButtonArrayList;
        buttonContext = context;
    }

    @NonNull
    @Override
    public SettingsButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_background_button_layout, parent, false);
        SettingsButtonViewHolder sbvh = new SettingsButtonViewHolder(v, buttonListener, buttonContext);
        return sbvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsButtonViewHolder holder, int position) {
        SettingsButton currentItem = buttonArrayList.get(position);
        holder.buttonText.setText(currentItem.getButtonText());

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[] {currentItem.getButtonTL(),currentItem.getButtonBR()});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(50);
        holder.buttonLayout.setBackground(gradientDrawable);
    }

    public static interface AdapterCallback{
        void onMethodCallback();
    }

    @Override
    public int getItemCount() {
        return buttonArrayList.size();
    }
}
